# Rate Limiter

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 4/10 — named the algorithm but skipped schema, Lua script logic, and failure mode depth
**After score:** 9/10

---

## 1. Problem statement

Protect APIs from accidental or abusive traffic while giving clients predictable, documented limits. Enforce quotas by user, API key, IP, and endpoint without making the application fleet a shared-state bottleneck. The limiter must be fast enough to sit on every request path and resilient enough that its own failure does not take down the API.

---

## 2. Requirements

### Functional requirements

- Enforce rules such as `100 requests/minute per API key` and endpoint-specific burst limits.
- Support multiple scopes: per-user, per-API-key, per-IP, per-endpoint, and composite (e.g., user + endpoint).
- Return remaining quota and reset time in response headers; reject excess traffic with HTTP 429.
- Allow dynamic policy updates without restarting the service.

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Decision latency | p99 < 5 ms added overhead | Limiter is on every request; latency tax must be negligible |
| Availability | Degrade gracefully on limiter failure | Limiter outage must not take down the API |
| Accuracy | Global accuracy within ~1% | Approximate is acceptable; exact global counting is too expensive |
| Throughput | 100k decisions/s peak | Must not be a bottleneck at scale |

### Explicitly out of scope

- Billing-grade usage metering (requires exact counts, different system).
- Bot detection and CAPTCHA challenges.
- DDoS mitigation at the network layer.

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Peak request rate | 100k req/s | — | One atomic counter operation per request; must be sub-millisecond |
| Active keys | 10 million | 10M × ~100 bytes/key = ~1 GB | Fits in Redis cluster; evict inactive keys with TTL |
| Policy count | ~1,000 rules | Small; cache all policies in-process | Policy resolution is a local in-memory lookup |
| Counter window | 60 seconds (token bucket refill) | — | Keys expire naturally; no explicit cleanup needed |
| Hot keys | Top 1% of keys get 10% of traffic | 100k keys at high rate | Dedicated shard routing for hot keys; local token bucket as fallback |

Peak multiplier: 10x burst. Policy cache TTL: 30 seconds.

---

## 4. API and domain model

### Internal decision API (called by gateway/middleware)

```
POST /internal/ratelimit/check
  Body:  { identityKey, routeKey, cost? }
  Response 200: {
    allowed:      boolean,
    remaining:    int,
    limit:        int,
    resetAfterMs: int,
    retryAfterMs: int   // only when allowed=false
  }

// Headers the gateway adds to every response:
X-RateLimit-Limit:     100
X-RateLimit-Remaining: 42
X-RateLimit-Reset:     1718000060   // Unix epoch of window reset
Retry-After:           30           // only on 429
```

### Policy management API

```
PUT /admin/policies/{policyId}
  Body: { scope, limit, windowSeconds, burstMultiplier }

GET /admin/policies/{policyId}
DELETE /admin/policies/{policyId}
```

### Domain model

```
RateLimitPolicy {
  policyId:        UUID PK
  scope:           ENUM(USER, API_KEY, IP, ENDPOINT, COMPOSITE)
  scopeKey:        VARCHAR   -- e.g., "user:*", "endpoint:/v1/payments"
  limit:           INT       -- max tokens per window
  windowSeconds:   INT       -- refill period
  burstMultiplier: FLOAT     -- e.g., 1.5 = allow 150% burst briefly
  priority:        INT       -- lower = more specific, wins over broader rules
  version:         INT       -- for cache invalidation
}

// Ephemeral state in Redis (not persisted to DB):
TokenBucketState {
  key:       STRING   -- e.g., "rl:user:abc123:endpoint:/v1/pay"
  tokens:    FLOAT    -- current token count
  lastRefill: INT     -- Unix timestamp of last refill
  // TTL = windowSeconds * 2
}
```

**Policy resolution:** `PolicyResolver` selects the narrowest matching rule. A rule for `user:abc123` on `/v1/payments` beats a rule for `user:*` on `*`. Ties broken by `priority` field.

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Gateway / API middleware (per-instance)              │
Request ───────────▶│                                                       │
                    │  1. PolicyResolver (in-process cache, 30s TTL)        │
                    │       │                                               │
                    │  2. LocalTokenBucket (in-process, approximate)        │
                    │       │ if local bucket allows:                       │
                    │  3. RemoteTokenBucket.tryConsume(key, cost)           │
                    │       │                                               │
                    │  4. Allow / Reject                                    │
                    └──────────────────┬───────────────────────────────────┘
                                       │
                    ┌──────────────────▼───────────────────────────────────┐
                    │  Redis Cluster (sharded by key hash)                  │
                    │  Atomic Lua script: refill + consume                  │
                    └──────────────────────────────────────────────────────┘
                                       │
                    ┌──────────────────▼───────────────────────────────────┐
                    │  Policy Service                                        │
                    │  PostgreSQL (source of truth for policies)            │
                    │  Publishes policy change events → gateways invalidate │
                    └──────────────────────────────────────────────────────┘
```

**Two-layer architecture:**
- Layer 1 (local): Each gateway instance maintains a local token bucket per key. This absorbs the majority of traffic without a network call. It is approximate — it does not account for other instances.
- Layer 2 (remote): Redis atomic Lua script is the global source of truth. Called when the local bucket allows the request. If Redis is unavailable, fall back to local-only mode.

---

## 6. Algorithm: Token Bucket

**Why token bucket over alternatives:**

| Algorithm | Burst handling | Memory | Boundary spike | Chosen? |
|---|---|---|---|---|
| Fixed window counter | Poor (2x burst at boundary) | Low | Yes | No |
| Sliding window log | Exact | High (stores all timestamps) | No | No |
| Sliding window counter | Good approximation | Low | Minimal | Viable |
| Token bucket | Excellent (explicit burst) | Low | No | **Yes** |
| Leaky bucket | Smooths output | Low | No | Good for egress shaping |

Token bucket chosen because: it naturally handles burst (burstMultiplier), is O(1) per operation, and maps cleanly to an atomic Redis script.

### Lua script (atomic refill + consume)

```lua
-- KEYS[1] = bucket key (e.g., "rl:user:abc:ep:/v1/pay")
-- ARGV[1] = limit (max tokens)
-- ARGV[2] = windowSeconds (refill period)
-- ARGV[3] = cost (tokens to consume, usually 1)
-- ARGV[4] = now (Unix timestamp, milliseconds)
-- ARGV[5] = burstLimit (limit * burstMultiplier)

local key         = KEYS[1]
local limit       = tonumber(ARGV[1])
local window      = tonumber(ARGV[2])
local cost        = tonumber(ARGV[3])
local now         = tonumber(ARGV[4])
local burstLimit  = tonumber(ARGV[5])

local data = redis.call("HMGET", key, "tokens", "lastRefill")
local tokens     = tonumber(data[1]) or burstLimit
local lastRefill = tonumber(data[2]) or now

-- Refill tokens proportional to elapsed time
local elapsed    = math.max(0, now - lastRefill)
local refillRate = limit / (window * 1000)   -- tokens per millisecond
tokens = math.min(burstLimit, tokens + elapsed * refillRate)

local allowed = 0
local remaining = 0
if tokens >= cost then
  tokens  = tokens - cost
  allowed = 1
end
remaining = math.floor(tokens)

-- Persist updated state; TTL = 2 * window
redis.call("HMSET", key, "tokens", tokens, "lastRefill", now)
redis.call("PEXPIRE", key, window * 2000)

-- resetAfterMs: time until bucket is full again
local resetAfterMs = 0
if tokens < limit then
  resetAfterMs = math.ceil((limit - tokens) / refillRate)
end

return {allowed, remaining, resetAfterMs}
```

**Why Lua?** Redis executes Lua scripts atomically — no race condition between read-modify-write. This is the standard production approach (used by Stripe, GitHub, etc.).

---

## 7. Failure behavior

### Redis unavailable

```
if redis.ping() fails:
  // Fail-open with strict local cap
  if route.isSensitiveWrite():
    return REJECT  // fail closed for payments, auth, etc.
  else:
    return localBucket.tryConsume(key, cost)  // approximate, per-instance
```

- Sensitive write APIs (payments, auth) fail closed — better to reject than allow unlimited writes.
- Read APIs fail open with a local cap — better to serve than block all reads.
- Alert immediately; this is a P1 incident.

### Hot keys

- A single key receiving >10k req/s can saturate one Redis shard.
- Detection: monitor per-key request rate; alert when a key exceeds 5k req/s.
- Mitigation: route hot keys to a dedicated Redis shard; use local token bucket as primary for known hot keys (accept ~5% over-counting).

### Duplicate client retries

- A client retry after a 429 consumes another token unless the gateway provides an idempotency identity.
- For idempotent retries: include `Idempotency-Key` header; gateway deduplicates within a 5-second window using a separate Redis key.

### Policy update race

- Policy version is included in the Redis key: `rl:v3:user:abc:ep:/v1/pay`.
- When policy version changes, old keys expire naturally (TTL). No migration needed.
- Gateway caches policies for 30s; stale policy means slightly wrong limits for up to 30s — acceptable.

---

## 8. Low-level design

### PolicyResolver

```java
// Selects the narrowest applicable policy for a given identity + route
RateLimitPolicy resolve(String identityKey, String routeKey) {
    List<RateLimitPolicy> candidates = policyCache.getAll()
        .stream()
        .filter(p -> p.matches(identityKey, routeKey))
        .sorted(Comparator.comparingInt(RateLimitPolicy::getPriority))
        .collect(toList());
    return candidates.isEmpty() ? defaultPolicy : candidates.get(0);
}
```

### TokenBucketStore interface

```java
interface TokenBucketStore {
    // Returns: allowed=true/false, remaining tokens, resetAfterMs
    ConsumeResult tryConsume(String key, RateLimitPolicy policy, int cost, long nowMs);
}

// RedisTokenBucketStore: executes Lua script above
// LocalTokenBucketStore: in-process ConcurrentHashMap<String, AtomicTokenBucket>
//   used as fallback and as Layer 1 pre-filter
```

### Composite key construction

```
// Ensures policy version is embedded so old counters expire naturally
String buildKey(String policyId, int policyVersion, String identityValue, String routeKey) {
    return String.format("rl:p%s:v%d:%s:%s", policyId, policyVersion, identityValue, routeKey);
}
```

### Schema (policy store)

```sql
CREATE TABLE rate_limit_policies (
  policy_id        UUID        PRIMARY KEY,
  scope            VARCHAR(20) NOT NULL,
  scope_key        VARCHAR(255) NOT NULL,
  limit_count      INT         NOT NULL,
  window_seconds   INT         NOT NULL,
  burst_multiplier NUMERIC(4,2) NOT NULL DEFAULT 1.0,
  priority         INT         NOT NULL DEFAULT 100,
  version          INT         NOT NULL DEFAULT 1,
  created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at       TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_policies_scope ON rate_limit_policies(scope, scope_key);
```

---

## 9. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Algorithm | Token bucket | Fixed window | Token bucket avoids boundary bursts; explicit burst control |
| Counter store | Redis with Lua | Database counters | Redis is orders of magnitude faster; Lua ensures atomicity |
| Global vs local | Two-layer (local pre-filter + global Redis) | Global only | Local layer reduces Redis calls by ~80%; accepts ~5% over-counting |
| Failure mode | Fail closed for writes, fail open for reads | Always fail open | Protects sensitive APIs; reads are lower risk |
| Policy versioning | Version in Redis key | Explicit migration | Zero-downtime policy changes; old keys expire naturally |

### Scaling triggers

- **Redis throughput**: shard Redis cluster when any single shard exceeds 100k ops/s. Consistent hashing ensures minimal key remapping.
- **Policy complexity**: if policy count exceeds 10k, add a policy index (trie or interval tree) for O(log n) resolution instead of linear scan.
- **Multi-region**: use a local Redis per region for low latency; accept that global limits are per-region (common in practice). For true global limits, use a gossip protocol or accept eventual consistency with a small over-count budget.

### Observability

- Decision latency p50/p95/p99 (local vs remote breakdown).
- Reject rate per policy, per endpoint, per identity.
- Redis fallback activation rate (alert if > 0.1%).
- Hot key concentration (top 10 keys by request rate).
- Policy cache staleness (time since last refresh).
- Local vs remote decision ratio (should be ~80% local).
