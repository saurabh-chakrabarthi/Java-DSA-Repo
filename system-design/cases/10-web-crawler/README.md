# Web Crawler

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 4/10 — mentioned Bloom filter and politeness but skipped frontier architecture, dedup strategy depth, and robots.txt caching
**After score:** 9/10

---

## 1. Problem statement

Crawl public web pages to collect documents and links for a search index while respecting robots rules, per-host politeness, deduplication, and changing content. The system must handle 10 million pages/day, recover from worker failures without re-crawling, and avoid hammering any single host.

---

## 2. Requirements

### Functional requirements

- Accept seed URLs; fetch allowed pages; extract canonical links; publish fetched documents.
- Respect `robots.txt` (Disallow rules, Crawl-delay directive).
- Enforce per-host concurrency limits (max 1 concurrent request per host by default).
- Deduplicate URLs (don't re-crawl the same URL unnecessarily).
- Detect changed content (re-crawl pages that have changed; skip unchanged).
- Recover work after worker failure (no lost URLs).

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Throughput | 10M pages/day (~116 pages/s) | Index freshness depends on crawl speed |
| Politeness | Max 1 req/s per host (configurable) | Avoid being blocked or causing harm |
| Durability | No URL task is permanently lost on worker failure | Frontier must survive worker crashes |
| Deduplication | < 0.1% duplicate fetches | Wasted bandwidth and compute |
| Storage | 5 TB/day raw content | Object storage; not DB |

### Explicitly out of scope

- Page ranking (PageRank, etc.) — separate indexing pipeline.
- JavaScript rendering — separate rendering tier for approved domains.
- Public search UI.

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Pages/day | 10 million | 116 pages/s avg; 10x peak = 1,160/s | ~100 fetch workers at 10 pages/s each |
| Avg page size | 500 KB | 10M × 500 KB = 5 TB/day | Object storage (S3); not DB |
| URL metadata | ~200 bytes/URL | 10M × 200 B = 2 GB/day | Postgres or Cassandra for frontier |
| Unique URLs | 1 billion (total corpus) | 1B × 200 B = 200 GB | Bloom filter for fast dedup; durable store for confirmation |
| Bloom filter | 1B entries, 1% FPR | ~1.2 GB (10 bits/entry) | Fits in memory; false positives cause rare missed crawls (acceptable) |
| robots.txt | 1 per host | Cache per host, TTL 24h | ~1M unique hosts; 1M × 2 KB = 2 GB cache |
| Re-crawl frequency | Popular pages: daily; others: weekly | — | Priority queue in frontier |

Peak multiplier: 10x. Content retention: 30 days hot, indefinite cold (object storage).

---

## 4. API and domain model

### API

```
// Submit seed URLs or a crawl job
POST /v1/crawl-jobs
  Body: {
    seeds: ["https://example.com", ...],
    policy: {
      maxDepth: 3,
      allowedDomains: ["example.com"],   // null = unrestricted
      maxPagesPerDomain: 10000,
      respectRobots: true,
      crawlDelaySeconds: 1
    }
  }
  Response 201: { jobId, status: "QUEUED" }

GET /v1/crawl-jobs/{jobId}
  Response 200: { jobId, status, pagesCrawled, pagesQueued, errors }

// Internal: fetch workers call this to get next task
POST /internal/frontier/lease
  Body: { workerId, count: 10 }
  Response 200: { tasks: [{ taskId, url, host, priority }] }

POST /internal/frontier/complete
  Body: { taskId, status, contentHash, extractedLinks: [...] }
```

### Domain model

```
UrlTask {
  taskId:          UUID PK
  jobId:           UUID FK → CrawlJob
  url:             TEXT NOT NULL
  normalizedUrl:   TEXT NOT NULL UNIQUE   -- canonical form for dedup
  host:            VARCHAR NOT NULL
  priority:        INT NOT NULL DEFAULT 50  -- 0=highest, 100=lowest
  status:          ENUM(QUEUED, LEASED, COMPLETED, FAILED, SKIPPED)
  attemptCount:    INT NOT NULL DEFAULT 0
  nextEligibleAt:  TIMESTAMPTZ NOT NULL DEFAULT now()
  leasedBy:        VARCHAR NULL           -- workerId
  leasedUntil:     TIMESTAMPTZ NULL       -- lease expiry
  lastFetchedAt:   TIMESTAMPTZ NULL
  lastContentHash: VARCHAR NULL           -- SHA-256 of last fetched content
  createdAt:       TIMESTAMPTZ NOT NULL
}

FetchResult {
  resultId:      UUID PK
  taskId:        UUID FK → UrlTask
  url:           TEXT NOT NULL
  httpStatus:    INT
  contentHash:   VARCHAR                  -- SHA-256 of response body
  contentType:   VARCHAR
  fetchedAt:     TIMESTAMPTZ NOT NULL
  responseTimeMs: INT
  objectKey:     VARCHAR                  -- S3 key for raw content
  extractedLinks: INT                     -- count of links found
  robotsAllowed: BOOLEAN
}

RobotsCache {
  host:        VARCHAR PK
  content:     TEXT                       -- raw robots.txt content
  fetchedAt:   TIMESTAMPTZ NOT NULL
  expiresAt:   TIMESTAMPTZ NOT NULL       -- TTL 24h
  disallowRules: JSONB                    -- parsed rules
  crawlDelay:  INT NULL                   -- seconds
}

HostState {
  host:              VARCHAR PK
  activeLeaseCount:  INT NOT NULL DEFAULT 0
  nextAllowedAt:     TIMESTAMPTZ NOT NULL DEFAULT now()
  consecutiveErrors: INT NOT NULL DEFAULT 0
  backoffUntil:      TIMESTAMPTZ NULL
}
```

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Seed Ingestion                                       │
Seeds ─────────────▶│  - Normalize URLs                                    │
                    │  - Dedup check (Bloom filter + DB)                   │
                    │  - Insert into Frontier DB                           │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Frontier (URL Scheduler)                            │
                    │  - Host-partitioned priority queue                   │
                    │  - Enforces per-host politeness (nextAllowedAt)      │
                    │  - Leases tasks to workers (with expiry)             │
                    │  - Handles lease expiry (worker failure recovery)    │
                    └──────────────────────┬───────────────────────────────┘
                                           │ leased tasks
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Fetch Workers (100 instances)                       │
                    │  - Check robots.txt cache                            │
                    │  - Fetch page (HTTP GET with timeout)                │
                    │  - Compute content hash                              │
                    │  - Store raw content to S3                           │
                    │  - Extract links                                     │
                    │  - Report completion to Frontier                     │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Link Extractor / URL Normalizer                     │
                    │  - Parse HTML, extract <a href> links                │
                    │  - Normalize URLs (lowercase, remove fragments, etc.)│
                    │  - Dedup check (Bloom filter)                        │
                    │  - Enqueue new URLs to Frontier                      │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Document Store (S3)                                 │
                    │  - Raw HTML content                                  │
                    │  - Indexed by contentHash (dedup at storage level)  │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Index Pipeline (downstream consumer)                │
                    │  - Reads from S3 via Kafka notifications             │
                    │  - Parses, indexes, ranks pages                      │
                    └──────────────────────────────────────────────────────┘
```

---

## 6. Critical flows and failure behavior

### URL deduplication (two-layer approach)

```
isDuplicate(normalizedUrl):

// Layer 1: Bloom filter (fast, in-memory, ~1% false positive rate)
if bloomFilter.mightContain(normalizedUrl):
  // Layer 2: DB lookup (slow, but only for Bloom filter hits)
  return db.exists("SELECT 1 FROM url_tasks WHERE normalized_url = ?", normalizedUrl)
else:
  return false  // definitely not seen before

// On new URL:
bloomFilter.add(normalizedUrl)
db.insert(urlTask)
```

**Why two layers?**
- Bloom filter alone: 1% false positives = 1% of new URLs incorrectly skipped. Acceptable for most use cases.
- DB alone: 1B URLs × DB lookup = too slow for link extraction throughput.
- Two layers: Bloom filter handles 99% of cases in memory; DB confirms the 1% that hit the filter.

**Bloom filter persistence:** Serialize to disk every 10 minutes. On restart, reload from disk. If disk is lost, rebuild from DB (expensive but rare).

### Politeness enforcement

```
leaseTask(workerId):

// Find eligible tasks respecting per-host limits
SELECT t.* FROM url_tasks t
JOIN host_states h ON t.host = h.host
WHERE t.status = 'QUEUED'
  AND t.next_eligible_at <= now()
  AND h.next_allowed_at <= now()
  AND h.active_lease_count < h.max_concurrent   -- default 1
  AND h.backoff_until IS NULL OR h.backoff_until <= now()
ORDER BY t.priority ASC, t.next_eligible_at ASC
LIMIT 1
FOR UPDATE SKIP LOCKED  -- skip rows locked by other workers

UPDATE url_tasks SET status='LEASED', leased_by=?, leased_until=now()+5min
UPDATE host_states SET active_lease_count = active_lease_count + 1,
                       next_allowed_at = now() + crawl_delay_seconds
```

**`FOR UPDATE SKIP LOCKED`** is the key: multiple workers can call `leaseTask` concurrently without blocking each other. Each worker atomically claims a different task.

### Worker failure recovery

```
// Lease expiry worker (runs every 60s):
expiredTasks = SELECT * FROM url_tasks
  WHERE status = 'LEASED' AND leased_until < now()

for each task:
  UPDATE url_tasks
    SET status = 'QUEUED',
        leased_by = NULL,
        leased_until = NULL,
        attempt_count = attempt_count + 1,
        next_eligible_at = now() + exponential_backoff(attempt_count)
    WHERE task_id = ? AND status = 'LEASED'

  UPDATE host_states
    SET active_lease_count = GREATEST(0, active_lease_count - 1)
    WHERE host = ?
```

Tasks are never deleted from the frontier until completed. Worker failure = lease expiry = automatic retry.

### robots.txt handling

```
isAllowed(url):
  host = extractHost(url)
  robots = robotsCache.get(host)

  if robots == null or robots.isExpired():
    robots = fetchRobotsTxt(host)  // HTTP GET https://{host}/robots.txt
    if fetch fails (404, timeout): assume all allowed, cache for 1h
    robotsCache.put(host, robots, ttl=24h)

  return robots.isAllowed(url, userAgent="MyCrawler")

// robots.txt parsing:
// Respect: User-agent: *, User-agent: MyCrawler
// Honor: Disallow, Allow, Crawl-delay
// Ignore: Sitemap (use separately for seed discovery)
```

### Content change detection

```
onFetchComplete(task, response):
  newHash = sha256(response.body)

  if task.lastContentHash == newHash:
    // Content unchanged
    task.status = 'COMPLETED'
    task.nextEligibleAt = now() + recrawlInterval(task.priority)
    // Do NOT store to S3 again; do NOT re-index
    return

  // Content changed (or first fetch)
  objectKey = storeToS3(response.body, newHash)
  task.lastContentHash = newHash
  task.status = 'COMPLETED'
  task.nextEligibleAt = now() + recrawlInterval(task.priority)
  publishToKafka(FetchedDocument { url, objectKey, contentHash, fetchedAt })
```

**Recrawl intervals:**
- Priority 0-10 (news, high-traffic): recrawl every 1 hour.
- Priority 11-50 (popular pages): recrawl every 24 hours.
- Priority 51-100 (low-traffic): recrawl every 7 days.

### Host error handling

```
onFetchError(task, error):
  if error is DNS_FAILURE or HTTP_4xx (permanent):
    task.status = 'FAILED'  // terminal; don't retry
    return

  if error is HTTP_5xx or TIMEOUT or CONNECTION_REFUSED:
    task.attemptCount++
    if task.attemptCount >= 5:
      task.status = 'FAILED'  // give up after 5 attempts
    else:
      task.status = 'QUEUED'
      task.nextEligibleAt = now() + exponentialBackoff(task.attemptCount)

    // Back off the entire host
    hostState.consecutiveErrors++
    if hostState.consecutiveErrors >= 3:
      hostState.backoffUntil = now() + hostBackoff(hostState.consecutiveErrors)
      // Backoff: 1min, 5min, 30min, 2h, 24h
```

---

## 7. Low-level design

### URL normalization

```java
String normalize(String rawUrl) {
    URL url = new URL(rawUrl);
    // 1. Lowercase scheme and host
    // 2. Remove default port (80 for http, 443 for https)
    // 3. Remove fragment (#section)
    // 4. Sort query parameters alphabetically
    // 5. Remove tracking parameters (utm_*, fbclid, etc.)
    // 6. Resolve relative URLs against base
    // 7. Decode percent-encoded characters (except reserved chars)
    // 8. Remove trailing slash from path (except root)
    return normalized;
}
```

### Schema

```sql
CREATE TABLE url_tasks (
  task_id           UUID        PRIMARY KEY,
  job_id            UUID        NOT NULL,
  url               TEXT        NOT NULL,
  normalized_url    TEXT        NOT NULL,
  host              VARCHAR(255) NOT NULL,
  priority          INT         NOT NULL DEFAULT 50,
  status            VARCHAR(20) NOT NULL DEFAULT 'QUEUED',
  attempt_count     INT         NOT NULL DEFAULT 0,
  next_eligible_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
  leased_by         VARCHAR(100),
  leased_until      TIMESTAMPTZ,
  last_fetched_at   TIMESTAMPTZ,
  last_content_hash VARCHAR(64),
  created_at        TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX idx_url_tasks_normalized ON url_tasks(normalized_url);
CREATE INDEX idx_url_tasks_frontier ON url_tasks(host, priority, next_eligible_at)
  WHERE status = 'QUEUED';
CREATE INDEX idx_url_tasks_lease_expiry ON url_tasks(leased_until)
  WHERE status = 'LEASED';

CREATE TABLE host_states (
  host                VARCHAR(255) PRIMARY KEY,
  active_lease_count  INT         NOT NULL DEFAULT 0,
  max_concurrent      INT         NOT NULL DEFAULT 1,
  crawl_delay_seconds INT         NOT NULL DEFAULT 1,
  next_allowed_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
  consecutive_errors  INT         NOT NULL DEFAULT 0,
  backoff_until       TIMESTAMPTZ
);
```

### S3 object key strategy

```
// Key: content/{sha256[0:2]}/{sha256[2:4]}/{sha256}.html.gz
// Example: content/a3/f7/a3f7b2...html.gz
//
// Why content-addressed (by hash)?
// - Automatic deduplication: same content = same key = one S3 object
// - Two URLs with identical content share one S3 object
// - Immutable: content never changes for a given hash
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Deduplication | Bloom filter + DB | DB only | Bloom filter handles 99% of cases in memory; DB is the durable confirmation |
| Frontier | Postgres with `FOR UPDATE SKIP LOCKED` | Kafka + Redis | Postgres provides durability + lease semantics natively; Kafka doesn't support lease/expiry |
| Politeness | Per-host `nextAllowedAt` in DB | Distributed rate limiter | DB-based is simpler and durable; rate limiter adds a network hop |
| Content storage | S3 (content-addressed) | DB blobs | S3 is cheaper and more scalable; content-addressed deduplicates identical pages |
| Worker failure | Lease expiry + retry | Checkpoint-based | Lease expiry is simpler; checkpoint requires workers to write progress |
| robots.txt | Cache per host (24h TTL) | Fetch on every crawl | Fetching robots.txt on every request would double the crawl load |

### Scaling triggers

- **Fetch throughput**: add more fetch worker instances (stateless). Each handles ~10 pages/s. 100 workers = 1,000 pages/s = 86M pages/day.
- **Frontier DB**: partition `url_tasks` by `host` hash when single instance exceeds 1B rows. Or migrate to Cassandra for write-heavy frontier.
- **Bloom filter**: at 10B URLs, Bloom filter grows to 12 GB. Shard by URL hash prefix across multiple instances.
- **Content storage**: S3 scales automatically. Add lifecycle policies to move old content to Glacier.
- **Link extraction**: parallelize with a separate worker pool. Link extraction is CPU-bound (HTML parsing).

### Observability

- Fetch success rate (% of fetches that return 200 — alert if < 80%).
- robots.txt denial rate (% of URLs blocked by robots.txt).
- Frontier queue depth (total QUEUED tasks — alert if growing unboundedly).
- Frontier age (oldest QUEUED task age — alert if > 24h for priority 0-10).
- Lease expiry rate (% of leases that expire = worker failure rate — alert if > 1%).
- Duplicate fetch rate (% of fetches where content hash matches previous — indicates re-crawl tuning needed).
- Host backoff count (number of hosts currently in backoff — alert if > 1,000).
- Bytes fetched per day (for cost monitoring).
- Content change rate (% of re-crawled pages with changed content — quality metric).
