# URL Shortener

**Status:** Reference case
**Scope level:** Production-minded interview design
**Before score:** 5/10 — had structure but lacked schema, algorithm depth, and failure specifics
**After score:** 9/10

---

## 1. Problem statement

Provide short, stable URLs that redirect users to long destinations. Creators can create a link, optionally set an expiry, and inspect coarse-grained click counts. A redirect must remain fast and reliable even under heavy read load.

---

## 2. Requirements

### Functional requirements

- Create a unique short URL for a valid destination URL.
- Redirect a short code to its destination with HTTP 301/302.
- Support optional expiration (TTL) and manual disabling by the creator.
- Provide eventual, aggregate click analytics (total clicks, referrer, country).

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Redirect latency | p95 < 20 ms at edge, < 100 ms origin | Redirect is the product's primary user path |
| Availability | 99.99% for redirects | Existing links are user promises |
| Durability | No acknowledged mapping is ever lost | A published link must survive any single failure |
| Consistency | Strong for create/read-after-create; eventual for analytics | New link must work immediately; counts can lag minutes |
| Write throughput | Sustain 1,200 writes/s peak | 10x average of 116/s |

### Explicitly out of scope

- Custom vanity domains and aliases.
- Malware/phishing classification beyond a basic blocklist hook.
- Per-click, real-time analytics dashboards.
- QR code generation.

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| New links | 10 million/day | 10M / 86,400 ≈ 116 writes/s avg; 10x peak = 1,160 writes/s | Single primary write store is fine; plan sharding at 10x growth |
| Redirects | 1 billion/day | 1B / 86,400 ≈ 11,600 reads/s avg; 10x peak = 116,000 reads/s | Read path needs CDN + cache; DB alone cannot serve this |
| Mapping size | ~500 bytes/row (code, URL, metadata, indexes) | 10M × 500 B = 5 GB/day | ~1.8 TB/year before replication; expiry/retention controls cost |
| Code space | Base62, 7 chars | 62^7 ≈ 3.5 trillion combinations | Enough for decades; collision probability negligible at this scale |
| Cache working set | Top 20% of links get 80% of traffic | 20% of 10M active = 2M entries × 500 B ≈ 1 GB | Fits in a single Redis node; replicate for HA |
| Analytics events | 1 billion/day | ~11,600 events/s | Kafka topic with partitioned consumers; never on the redirect path |

Peak multiplier: 10x. Hot retention: 90 days. Cold/archive: 7 years.

---

## 4. API and domain model

### REST API

```
POST /v1/links
  Body:  { destinationUrl, expiresAt?, creatorId }
  Headers: Idempotency-Key: <uuid>
  Response 201: { code, shortUrl, expiresAt, createdAt }
  Response 409: idempotency conflict (returns original)
  Response 422: invalid/blocked destination URL

GET /{code}
  Response 301/302: Location: <destinationUrl>   (301 for permanent, 302 for expirable)
  Response 404: code not found
  Response 410: link expired or disabled

GET /v1/links/{code}
  Response 200: full link record including analytics summary

PATCH /v1/links/{code}
  Body: { status: "DISABLED" }
  Response 200: updated record

GET /v1/links/{code}/analytics?from=&to=&granularity=hour
  Response 200: { clicks: [...timeseries], topReferrers, topCountries }
```

### Domain model

```
Link {
  code:           VARCHAR(10) PK          -- Base62 short code
  destinationUrl: TEXT NOT NULL           -- normalized, validated
  creatorId:      UUID NOT NULL
  status:         ENUM(ACTIVE, DISABLED, EXPIRED)
  expiresAt:      TIMESTAMP NULL
  createdAt:      TIMESTAMP NOT NULL
  version:        INT NOT NULL DEFAULT 0  -- optimistic lock
  idempotencyKey: VARCHAR(64) UNIQUE      -- per creator
}

ClickEvent {                              -- append-only, written to Kafka
  code:       VARCHAR(10)
  occurredAt: TIMESTAMP
  referrer:   TEXT
  country:    CHAR(2)
  userAgent:  TEXT
}

ClickAggregate {                          -- materialized by analytics worker
  code:        VARCHAR(10)
  windowStart: TIMESTAMP
  granularity: ENUM(HOUR, DAY)
  clicks:      BIGINT
}
```

State transitions: `ACTIVE → DISABLED` (creator action), `ACTIVE → EXPIRED` (TTL worker or read-time check). Both are terminal.

---

## 5. High-level design

```
                        ┌─────────────────────────────────────────────────────┐
                        │  Write path                                          │
Creator ──POST /links──▶│  Link API  ──▶  ID Generator  ──▶  Link DB (primary)│
                        │                                    + Outbox table    │
                        └──────────────────────┬──────────────────────────────┘
                                               │ async
                                               ▼
                                         Kafka topic: link-events
                                               │
                                    ┌──────────┴──────────┐
                                    ▼                      ▼
                              Cache warmer           Analytics worker
                              (Redis write)          (ClickAggregate DB)

                        ┌─────────────────────────────────────────────────────┐
                        │  Read path (redirect)                                │
User ──GET /{code}─────▶│  CDN (edge cache, TTL 60s)                          │
                        │    │ miss                                            │
                        │    ▼                                                 │
                        │  Redirect API  ──▶  Redis cache  ──▶  Link DB (RO)  │
                        │                         │ miss                       │
                        │                         └──────────────────────────▶│
                        │                                                      │
                        │  Emit ClickEvent to Kafka (fire-and-forget)          │
                        └─────────────────────────────────────────────────────┘
```

**Key design decisions:**
- CDN caches 301 redirects for permanent links (long TTL). Use 302 for expirable links (short TTL, forces origin check).
- Redis is cache-aside: populated on first miss, TTL matches link expiry or 1 hour for permanent links.
- Redirect API never writes to DB on the hot path — only reads.
- Outbox pattern ensures `LinkCreated` event is published exactly once even if the app crashes after DB write.

---

## 6. Critical flows and failure behavior

### Create flow

1. Validate and normalize destination URL (reject private IPs, known malware domains).
2. Check idempotency key in `(creatorId, idempotencyKey)` index — return existing if found.
3. Generate a 7-char Base62 code (random). Check uniqueness in DB. Retry up to 3 times on collision (collision probability at 10M links is ~0.003%, negligible).
4. `BEGIN TRANSACTION`: insert `Link` row + insert `Outbox` row. `COMMIT`.
5. Outbox relay publishes `LinkCreated` to Kafka asynchronously.
6. Return `201` with the short URL.

**Failure cases:**
- Client timeout after step 4: retry with same idempotency key → returns original link (step 2 catches it).
- DB write fails: return 500, nothing was committed, client retries safely.
- Outbox relay down: link is usable immediately; event publishes when relay recovers (at-least-once).

### Redirect flow

1. CDN lookup → cache hit → 301/302 immediately (no origin call).
2. CDN miss → Redirect API → Redis lookup.
3. Redis miss → DB read replica lookup → populate Redis with TTL.
4. Evaluate: if `status != ACTIVE` or `expiresAt < now` → 404/410.
5. Emit `ClickEvent` to Kafka (async, non-blocking — if Kafka is down, redirect still succeeds).
6. Return 301 (permanent) or 302 (expirable).

**Failure cases:**
- Redis down: fall through to DB read replica. Latency increases but redirect succeeds.
- DB replica lagging: a link created < replication lag ago may 404 briefly. Acceptable per requirements (strong consistency only guaranteed for creator's own session via primary read).
- Link disabled after CDN cached it: CDN TTL is 60s max; use `Cache-Control: max-age=60`. For urgent disables, issue a CDN purge API call.
- Analytics Kafka down: redirect succeeds; click is lost. Acceptable (eventual analytics).

### Expiry cleanup flow

- A background worker queries `WHERE status = 'ACTIVE' AND expiresAt < now` in batches, updates status to `EXPIRED`, and publishes `LinkExpired` events.
- The read path also checks expiry inline — so even if the worker is delayed, expired links return 410.

---

## 7. Low-level design

### Code generation

```
Option A (chosen): Random Base62
  - Generate 7 random bytes, Base62-encode to 7 chars.
  - INSERT with unique constraint on `code`; retry on conflict.
  - Pro: no central bottleneck, no sequential enumeration risk.
  - Con: rare collision retries (acceptable at this scale).

Option B: Snowflake ID → Base62
  - Central ID generator (or DB sequence) → encode.
  - Pro: zero collisions.
  - Con: sequential codes are enumerable; requires a generator service.
```

### Cache-aside pattern (Redis)

```
resolve(code):
  val = redis.get(code)
  if val == "NOT_FOUND": return 404          // negative cache, TTL 5 min
  if val != null: return val
  row = db.queryReplica("SELECT * FROM links WHERE code = ?", code)
  if row == null:
    redis.setex(code, 300, "NOT_FOUND")
    return 404
  redis.setex(code, min(row.expiresAt - now, 3600), serialize(row))
  return row
```

### Schema indexes

```sql
CREATE TABLE links (
  code           VARCHAR(10)  PRIMARY KEY,
  destination_url TEXT        NOT NULL,
  creator_id     UUID         NOT NULL,
  status         VARCHAR(10)  NOT NULL DEFAULT 'ACTIVE',
  expires_at     TIMESTAMPTZ,
  created_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),
  version        INT          NOT NULL DEFAULT 0,
  idempotency_key VARCHAR(64)
);

CREATE UNIQUE INDEX idx_links_idempotency ON links(creator_id, idempotency_key)
  WHERE idempotency_key IS NOT NULL;
CREATE INDEX idx_links_expiry ON links(expires_at)
  WHERE status = 'ACTIVE' AND expires_at IS NOT NULL;
CREATE INDEX idx_links_creator ON links(creator_id, created_at DESC);
```

### Outbox table

```sql
CREATE TABLE outbox (
  id         UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
  event_type VARCHAR(50) NOT NULL,
  payload    JSONB       NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  published  BOOLEAN     NOT NULL DEFAULT false
);
-- Relay: SELECT ... WHERE published = false ORDER BY created_at LIMIT 100
-- After Kafka ack: UPDATE outbox SET published = true WHERE id = ?
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Code generation | Random Base62 + uniqueness check | Central counter + Base62 | Avoids generator SPOF; collision rate is negligible at this scale |
| Redirect caching | CDN (edge) + Redis (origin) | DB-only reads | 116k reads/s peak cannot be served by DB alone; cache hit rate >99% expected |
| Analytics | Async Kafka → aggregate worker | Synchronous counter increment | Keeps redirect latency and availability independent of analytics correctness |
| Durability | Replicated DB + transactional outbox | Cache as source of truth | Links survive cache loss; outbox prevents silent event loss |
| 301 vs 302 | 301 for permanent, 302 for expirable | Always 302 | 301 lets browsers cache permanently, reducing origin load; 302 forces recheck for expirable links |
| Expiry enforcement | Read-time check + async cleanup worker | Only async cleanup | Async-only risks serving expired links if worker is delayed |

### Scaling triggers

- **Write throughput**: shard `links` table by `code` hash when single-primary write IOPS becomes a bottleneck (expect this around 50M links/day).
- **Read throughput**: add Redis cluster sharding; add more CDN PoPs.
- **Analytics**: migrate from batch aggregation to a streaming pipeline (Flink/Spark Streaming) when near-real-time analytics is required.
- **Storage**: implement tiered storage — hot links in fast SSD-backed DB, cold/expired links archived to object storage after 90 days.

### Observability

- Redirect p95/p99 latency (CDN vs origin breakdown).
- Cache hit rate (CDN and Redis separately).
- Code collision rate (should be < 0.01%).
- Outbox relay lag (alert if > 30s).
- Expired/disabled redirect attempt rate.
- Analytics event lag (Kafka consumer group lag).
- DB replica lag.

### Security

- Validate destination URL: reject `localhost`, RFC-1918 ranges, `file://`, `javascript:` schemes (SSRF prevention).
- Rate-limit `POST /v1/links` per creator (e.g., 1,000/hour).
- Blocklist known phishing/malware domains; integrate with a threat-intel feed asynchronously.
- Short codes are not secret — do not use them for access control. Use signed URLs or auth tokens for private content.
