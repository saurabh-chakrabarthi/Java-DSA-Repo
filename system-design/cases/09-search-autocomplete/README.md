# Search Autocomplete

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 4/10 — mentioned trie/FST but skipped the actual data structure tradeoffs, index build pipeline, and ranking algorithm
**After score:** 9/10

---

## 1. Problem statement

Return relevant query suggestions as a user types, with sub-100 ms latency and ranking informed by popularity, recency, locale, and optional user context. The system must serve 10k keystrokes/second with p95 < 50 ms, handle index updates without downtime, and filter unsafe or blocklisted suggestions.

---

## 2. Requirements

### Functional requirements

- Return the top 10 suggestions for a given prefix (2+ characters).
- Support locale/language filtering (suggestions are locale-specific).
- Rank suggestions by popularity, recency, and optionally user context.
- Filter blocklisted and unsafe terms before serving.
- Update suggestion rankings based on query volume (eventual, not real-time).

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Suggestion latency | p95 < 50 ms | Autocomplete must feel instant; > 100 ms breaks the UX |
| Throughput | 10k requests/s peak | Every keystroke is a request |
| Index freshness | Rankings updated within 1 hour | Trending queries should appear quickly |
| Availability | 99.99% | Serve last known-good index on failure |
| Safety | Zero unsafe suggestions served | Blocklist must be applied before serving |

### Explicitly out of scope

- Full-document search (Elasticsearch use case).
- Personalized ML model training (use offline ML pipeline; this service consumes its output).
- Spell correction (separate NLP service).

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Unique queries | 100 million normalized queries | — | Index size depends on prefix fanout |
| Avg prefix length | 5 chars | 100M × 5 prefixes = 500M prefix entries | Trie or prefix map; compression is essential |
| Top-K per prefix | 10 suggestions | 500M × 10 × ~50 bytes = 250 GB uncompressed | Must compress; FST or prefix-to-topK map with delta encoding |
| Locales | 50 | 250 GB × 50 = 12.5 TB total | Shard by locale; most locales are much smaller than English |
| Read throughput | 10k req/s | — | In-memory index per shard; no DB on hot path |
| Query events | 1 billion/day | ~11,600/s | Kafka → aggregation job → index rebuild |
| Index rebuild | Every 30 minutes | — | Atomic swap; no downtime |

Peak multiplier: 5x. Index retention: current version + 2 previous (for rollback).

---

## 4. API and domain model

### REST API

```
GET /v1/suggestions?prefix=sea&locale=en-US&limit=10&userId=<optional>
  Response 200: {
    suggestions: [
      { text: "seattle weather", score: 0.95, type: "QUERY" },
      { text: "search engine", score: 0.87, type: "QUERY" },
      ...
    ],
    indexVersion: "v2024-06-15-14:30"
  }
  Response 400: prefix too short (< 2 chars)

// Admin: blocklist management
POST /admin/blocklist
  Body: { term, locale?, reason }
DELETE /admin/blocklist/{termId}

// Admin: trigger index rebuild (for emergency updates)
POST /admin/index/rebuild
```

### Domain model

```
// Offline (index build pipeline):
QueryEvent {
  normalizedQuery: VARCHAR   -- lowercased, trimmed, deduped
  locale:          VARCHAR
  occurredAt:      TIMESTAMP
  userId:          UUID NULL  -- for personalization features
}

SuggestionCandidate {
  text:        VARCHAR
  locale:      VARCHAR
  queryCount:  BIGINT         -- rolling 7-day count
  recentCount: BIGINT         -- rolling 24-hour count (for trending)
  safetyState: ENUM(SAFE, BLOCKED, PENDING_REVIEW)
  updatedAt:   TIMESTAMP
}

// Serving (in-memory index):
PrefixIndex {
  // Map: prefix → TopKList
  // TopKList: sorted list of (text, score) pairs, max 10 entries
  // Stored as: compressed trie or prefix-to-topK hash map
}

IndexVersion {
  versionId:   VARCHAR   -- e.g., "v2024-06-15-14:30"
  locale:      VARCHAR
  builtAt:     TIMESTAMP
  status:      ENUM(BUILDING, VALIDATING, ACTIVE, RETIRED)
  entriesCount: BIGINT
}

BlocklistEntry {
  termId:    UUID PK
  term:      VARCHAR NOT NULL
  locale:    VARCHAR NULL   -- null = all locales
  reason:    TEXT
  addedBy:   UUID
  addedAt:   TIMESTAMPTZ
}
```

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Read path (hot path — must be < 50ms)               │
Client ────────────▶│  Edge Cache (CDN, TTL 60s for common prefixes)       │
                    │    │ miss                                             │
                    │    ▼                                                  │
                    │  Suggestion API (stateless, multiple instances)       │
                    │    │                                                  │
                    │    ▼                                                  │
                    │  In-Memory Prefix Index (per locale, per instance)   │
                    │  - Loaded on startup, atomically swapped on update   │
                    │  - No network call on hot path                       │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Index build pipeline (offline, runs every 30 min)   │
Query events ──────▶│  Kafka topic: query-events                           │
                    │    │                                                  │
                    │    ▼                                                  │
                    │  Aggregation Job (Spark / Flink)                     │
                    │  - Count queries per (text, locale) over 7-day window│
                    │  - Apply safety filter (blocklist)                   │
                    │  - Compute top-K per prefix per locale               │
                    │    │                                                  │
                    │    ▼                                                  │
                    │  Index Store (S3 / object storage)                   │
                    │  - Versioned index files per locale                  │
                    │    │                                                  │
                    │    ▼                                                  │
                    │  Index Publisher                                      │
                    │  - Validates new index (smoke test: known queries)   │
                    │  - Publishes version to config service               │
                    │  - API instances detect new version, reload index    │
                    └──────────────────────────────────────────────────────┘
```

**Key insight:** The hot path has zero network calls. The in-memory index is a hash map lookup — O(1) for exact prefix, O(prefix length) for trie traversal. This is how Google/Bing achieve sub-10ms autocomplete.

---

## 6. Data structure: Prefix-to-TopK Map vs Trie

### Option A: Prefix-to-TopK Hash Map (chosen)

```
Map<String, List<Suggestion>> index = {
  "se"  → [("seattle weather", 0.95), ("search engine", 0.87), ...],
  "sea" → [("seattle weather", 0.98), ("sea turtle", 0.72), ...],
  "sear" → [("search engine", 0.99), ("search autocomplete", 0.81), ...],
  ...
}

Lookup: index.get(prefix) → O(1)
Build: for each query, for each prefix of query, insert into top-K list
Storage: 500M entries × ~100 bytes = 50 GB (compressed with delta encoding: ~5 GB)
```

**Pros:** O(1) lookup, simple to implement, easy to shard by prefix hash.
**Cons:** Large storage; every prefix must be pre-computed.

### Option B: Compressed Trie / FST (Finite State Transducer)

```
Trie node: { char, children[], topK: List<Suggestion> }
// Each node stores the top-K suggestions for all queries with this prefix

Lookup: traverse trie character by character → O(prefix length)
Storage: much smaller than hash map (shared prefixes compressed)
// FST (used by Lucene) compresses further: ~1-2 GB for 100M queries
```

**Pros:** Memory-efficient; supports fuzzy matching and edit distance.
**Cons:** More complex to build and update; harder to shard.

**Decision:** Prefix-to-TopK map for simplicity and O(1) lookup. Use FST if memory becomes a constraint (> 10 GB per locale).

---

## 7. Critical flows and failure behavior

### Serving flow

```
getSuggestions(prefix, locale, userId):

1. Normalize prefix: lowercase, trim, remove special chars
2. If prefix.length < 2: return empty (too short)
3. Look up in-memory index: index[locale].get(prefix)
4. If not found: return empty (no suggestions for this prefix)
5. If userId provided: re-rank top-10 using personalization model
   (lightweight: just re-order, don't add new suggestions)
6. Apply real-time blocklist check (in-memory set, updated every 60s)
7. Return top-10

Total time: < 5ms (in-memory operations only)
```

### Index build pipeline

```
Every 30 minutes:
1. Aggregation job reads Kafka: query-events from last 7 days
2. For each (normalizedQuery, locale):
   a. Count occurrences (7-day total, 24-hour recent)
   b. Compute score: 0.7 * log(totalCount) + 0.3 * log(recentCount + 1)
   c. Apply safety filter: skip if in blocklist
3. For each prefix of each query:
   a. Insert (query, score) into prefix's top-K list (max 10)
   b. Use min-heap to maintain top-K efficiently
4. Serialize index to compressed format (gzip + delta encoding)
5. Upload to S3: s3://autocomplete-index/{locale}/v{timestamp}.bin
6. Validate: run smoke tests (known queries must return expected results)
7. Publish new version to config service
8. API instances poll config service every 60s; detect new version; load new index
9. Atomic swap: replace old index reference with new (no downtime)
10. Retire old index after 2 versions
```

### Index update failure

- If build job fails: API continues serving previous index. Alert on-call.
- If new index fails validation: do not publish. Alert on-call. Previous index remains active.
- If API instance fails to load new index: continue with old index. Alert on-call.
- **Invariant:** API always serves a valid index. It may be stale (up to 1 hour) but never empty.

### Blocklist update (urgent)

```
// Blocklist updates must take effect within 60 seconds (not 30 minutes)
// Separate from index rebuild:

1. Admin adds term to blocklist DB
2. Blocklist publisher pushes update to Redis (TTL 24h)
3. API instances poll Redis blocklist every 60s
4. Apply blocklist filter at serve time (step 6 in serving flow)
// This means a blocked term can appear in the index but is filtered at serve time
// Index rebuild will remove it from the index in the next cycle
```

---

## 8. Low-level design

### Ranking formula

```
// Score for a suggestion given a prefix:
score = w1 * normalizedPopularity + w2 * normalizedRecency + w3 * personalScore

where:
  normalizedPopularity = log(queryCount7d + 1) / log(maxQueryCount + 1)
  normalizedRecency    = log(queryCount24h + 1) / log(maxQueryCount24h + 1)
  personalScore        = 0 if no userId, else personalization model output [0,1]
  w1 = 0.6, w2 = 0.3, w3 = 0.1

// Deterministic tie-breaking: alphabetical order
// This ensures cache behavior is stable (same prefix always returns same order)
```

### Index sharding

```
// Shard by locale (primary) and leading 2 characters of prefix (secondary)
// Shard key: {locale}:{prefix[0:2]}
// Example: "en-US:se" → shard 7

// Each API instance loads shards for its assigned locales
// Locales are assigned to instances via consistent hashing
// Rebalancing: add instance → transfer shard ownership → load new shards → serve

// Memory estimate per instance:
// English locale: ~5 GB compressed index
// 10 locales per instance: ~20 GB (smaller locales are much smaller than English)
// Fits in a 32 GB instance
```

### Schema (offline pipeline)

```sql
-- Suggestion candidates (updated by aggregation job)
CREATE TABLE suggestion_candidates (
  text           VARCHAR(500) NOT NULL,
  locale         VARCHAR(10)  NOT NULL,
  query_count_7d BIGINT       NOT NULL DEFAULT 0,
  query_count_24h BIGINT      NOT NULL DEFAULT 0,
  safety_state   VARCHAR(20)  NOT NULL DEFAULT 'SAFE',
  updated_at     TIMESTAMPTZ  NOT NULL,
  PRIMARY KEY (text, locale)
);

CREATE INDEX idx_candidates_locale_count
  ON suggestion_candidates(locale, query_count_7d DESC)
  WHERE safety_state = 'SAFE';

-- Blocklist
CREATE TABLE blocklist_entries (
  term_id    UUID        PRIMARY KEY,
  term       VARCHAR(500) NOT NULL,
  locale     VARCHAR(10),   -- NULL = all locales
  reason     TEXT,
  added_by   UUID,
  added_at   TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_blocklist_term ON blocklist_entries(term, locale);
```

---

## 9. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Index structure | Prefix-to-TopK hash map | Compressed trie / FST | O(1) lookup; simpler to build and shard; FST if memory > 10 GB/locale |
| Index location | In-memory per API instance | Shared Redis | In-memory has zero network latency; Redis adds 1-2ms per lookup |
| Index updates | Atomic swap (30-min rebuild) | Incremental updates | Atomic swap is simpler and safer; incremental updates risk partial state |
| Blocklist | Serve-time filter (60s lag) | Index-time filter only | Serve-time filter allows urgent blocking without waiting for index rebuild |
| Ranking | Offline pre-computed scores | Query-time scoring | Pre-computed scores are faster and more cache-stable; query-time scoring is fresher |
| Personalization | Re-rank top-10 at serve time | Personalized index per user | Per-user index is impractical at 10M users; re-ranking is a good approximation |

### Scaling triggers

- **Read throughput**: add API instances (stateless, just load the index). CDN absorbs common prefixes.
- **Index size**: if English index exceeds 10 GB, switch to FST. Or shard by leading 3 characters instead of 2.
- **Build time**: if 30-minute rebuild takes > 20 minutes, increase Spark cluster size or switch to streaming aggregation (Flink).
- **Locales**: each locale is independent. Add locales by adding new index shards.
- **Personalization**: when personalization model improves, increase w3 weight. A/B test ranking changes by serving different weights to different user segments.

### Observability

- Suggestion latency p50/p95/p99 (should be < 5ms in-memory, < 50ms including CDN miss).
- Cache hit rate (CDN — should be > 70% for common prefixes).
- Zero-result rate (% of prefixes with no suggestions — alert if > 5%).
- Index freshness (age of active index — alert if > 2 hours).
- Blocklist application rate (% of requests where a suggestion was filtered).
- Index build success rate (alert if build fails 2 consecutive times).
- Click-through rate (% of suggestions that are clicked — primary quality metric).
- Unsafe suggestion served count (should always be 0 — alert immediately if > 0).
