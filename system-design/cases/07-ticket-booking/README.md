# Ticket Booking

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 4/10 — mentioned optimistic locking and virtual queue but skipped the actual contention mechanics, hold expiry race, and onsale spike architecture
**After score:** 9/10

---

## 1. Problem statement

Let users find an event, view seats, temporarily hold selected inventory, pay, and receive a confirmed ticket without overselling high-demand events. The hardest problem is handling a popular onsale where 50,000 users simultaneously try to buy from a 20,000-seat inventory — the system must prevent overselling while remaining responsive under extreme contention.

---

## 2. Requirements

### Functional requirements

- Browse events and view real-time seat availability (slightly stale is acceptable).
- Place a 5-minute hold on selected seats (exclusive, prevents others from buying).
- Confirm a hold into a confirmed order after successful payment.
- Release holds automatically on expiry or payment failure.
- Prevent two confirmed orders for the same seat (hard invariant).

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Correctness | Zero oversells | Overselling is a P0 incident; tickets must be honored |
| Hold latency | p95 < 500 ms | Users expect immediate feedback on seat selection |
| Availability | 99.99% for browsing; 99.9% for hold/confirm | Browsing is read-only; hold/confirm are write-critical |
| Onsale spike | Handle 50k concurrent users for a 20k-seat event | Popular onsales are the defining load scenario |
| Hold expiry | Accurate within 30 seconds | Expired holds must release promptly to allow others to buy |

### Explicitly out of scope

- Venue layout authoring and seat map rendering.
- Ticket resale marketplace.
- Dynamic pricing / surge pricing.

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Normal load | 1k hold attempts/s | — | Standard DB with optimistic locking handles this |
| Onsale spike | 50k requests/s for one event | 50k × 1 seat = 50k concurrent hold attempts | Virtual waiting room required; DB cannot handle 50k concurrent writes on one event |
| Seat inventory | 20k seats per event | 20k rows per event | Tiny data; fits in memory; hot-key contention is the problem, not storage |
| Hold duration | 5 minutes | — | Expiry worker runs every 30s; read path checks expiry inline |
| Events | 10k active events | 10k × 20k seats = 200M seat rows | Manageable in Postgres with partitioning by eventId |
| Ticket size | ~500 bytes | 200M × 500 B = 100 GB | Fits in a single large Postgres instance |

Peak multiplier: 50x for onsale events (not 10x — onsales are the design-defining scenario).

---

## 4. API and domain model

### REST API

```
// Browse events
GET /v1/events?date=&venue=&page=
  Response 200: [{ eventId, name, date, venue, availableSeats, status }]

// Get seat map (slightly stale OK — served from cache)
GET /v1/events/{eventId}/seats
  Response 200: { seats: [{ seatId, section, row, number, status, price }] }

// Place a hold (strong consistency required)
POST /v1/holds
  Headers: Idempotency-Key: <uuid>
  Body: { eventId, seatIds: ["A1", "A2"], userId }
  Response 201: { holdId, seatIds, expiresAt, totalPrice }
  Response 409: one or more seats already held/sold
  Response 429: virtual queue — position and estimated wait time

// Confirm hold (after payment)
POST /v1/orders
  Headers: Idempotency-Key: <uuid>
  Body: { holdId, paymentToken }
  Response 201: { orderId, tickets: [{ ticketId, seatId, qrCode }] }
  Response 410: hold expired
  Response 402: payment failed

// Release hold manually
DELETE /v1/holds/{holdId}
  Response 204

// Get order
GET /v1/orders/{orderId}
  Response 200: { orderId, status, tickets, totalPaid }
```

### Domain model

```
SeatInventory {
  eventId:    UUID (partition key)
  seatId:     VARCHAR (sort key)
  section:    VARCHAR
  row:        VARCHAR
  number:     VARCHAR
  price:      BIGINT          -- in cents
  status:     ENUM(AVAILABLE, HELD, SOLD)
  holdId:     UUID NULL       -- FK to Hold if status=HELD
  heldUntil:  TIMESTAMPTZ NULL
  version:    INT NOT NULL DEFAULT 0   -- optimistic lock
}

Hold {
  holdId:    UUID PK
  userId:    UUID NOT NULL
  eventId:   UUID NOT NULL
  seatIds:   UUID[]           -- array of held seat IDs
  status:    ENUM(ACTIVE, EXPIRED, CONFIRMED, RELEASED)
  expiresAt: TIMESTAMPTZ NOT NULL
  createdAt: TIMESTAMPTZ NOT NULL
  idempotencyKey: VARCHAR(64) UNIQUE
}

Order {
  orderId:        UUID PK
  userId:         UUID NOT NULL
  holdId:         UUID NOT NULL UNIQUE   -- one order per hold
  eventId:        UUID NOT NULL
  status:         ENUM(PENDING, CONFIRMED, CANCELED, REFUNDED)
  totalPaid:      BIGINT NOT NULL
  paymentId:      UUID NOT NULL
  idempotencyKey: VARCHAR(64) UNIQUE
  createdAt:      TIMESTAMPTZ NOT NULL
}

Ticket {
  ticketId:  UUID PK
  orderId:   UUID FK → Order
  eventId:   UUID NOT NULL
  seatId:    VARCHAR NOT NULL
  qrCode:    VARCHAR NOT NULL   -- signed token for entry
  issuedAt:  TIMESTAMPTZ NOT NULL
}
```

State transitions:
- `SeatInventory.status`: `AVAILABLE → HELD → SOLD` (or `HELD → AVAILABLE` on expiry/release)
- `Hold.status`: `ACTIVE → CONFIRMED | EXPIRED | RELEASED`
- `Order.status`: `PENDING → CONFIRMED | CANCELED`

**Hard invariant:** A seat with `status=SOLD` can never transition back to `AVAILABLE` or `HELD`. A seat can have at most one active hold at any time.

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Virtual Waiting Room (for onsale events only)        │
Users ─────────────▶│  - Issues a queue token with position + wait time    │
                    │  - Admits N users/second to the booking flow         │
                    │  - Prevents 50k concurrent DB writes                 │
                    └──────────────────────┬───────────────────────────────┘
                                           │ admitted users only
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Booking API                                          │
                    │  - Idempotency check                                 │
                    │  - Seat availability check (cache)                   │
                    │  - Hold creation (strong consistency)                │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Inventory Service (sharded by eventId)              │
                    │  - Atomic hold creation with optimistic locking      │
                    │  - Conflict detection and retry                      │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Inventory DB (Postgres, partitioned by eventId)     │
                    │  - SeatInventory rows with version column            │
                    │  - Unique constraint: one active hold per seat       │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Seat Map Cache (Redis)                               │
                    │  - Cached seat availability per event (TTL 5s)       │
                    │  - Used for browsing only; not for hold decisions    │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Hold Expiry Worker                                   │
                    │  - Runs every 30s                                    │
                    │  - Finds HELD seats with heldUntil < now             │
                    │  - Atomically releases: HELD → AVAILABLE             │
                    └──────────────────────────────────────────────────────┘
```

---

## 6. Critical flows and failure behavior

### Hold creation (the critical path)

The core challenge: multiple users trying to hold the same seat simultaneously.

```
holdSeats(userId, eventId, seatIds, idempotencyKey):

1. Check idempotency key → return existing hold if found

2. For each seatId, verify status=AVAILABLE in cache (fast, slightly stale)
   If any seat shows HELD/SOLD in cache → return 409 immediately (fast rejection)

3. BEGIN TRANSACTION
   For each seatId:
     SELECT * FROM seat_inventory
       WHERE event_id = ? AND seat_id = ? AND status = 'AVAILABLE'
       FOR UPDATE  -- row-level lock

   If any seat is not AVAILABLE: ROLLBACK → return 409

   INSERT INTO holds (holdId, userId, eventId, seatIds, expiresAt=now+5min)

   UPDATE seat_inventory
     SET status = 'HELD', hold_id = ?, held_until = now+5min, version = version+1
     WHERE event_id = ? AND seat_id = ? AND status = 'AVAILABLE' AND version = ?

   If UPDATE affected 0 rows (concurrent hold won): ROLLBACK → return 409

   COMMIT

4. Invalidate seat map cache for this event
5. Return holdId and expiresAt
```

**Why `FOR UPDATE` + version check?** `FOR UPDATE` prevents concurrent transactions from reading the same row until the lock is released. The version check is a belt-and-suspenders — if somehow two transactions both see `AVAILABLE`, only one UPDATE will succeed (the other sees version mismatch).

**Why not Redis for holds?** Redis is faster but less durable. A Redis crash could lose hold state, causing oversells. Postgres with row-level locks is the right tool for inventory correctness.

### Onsale spike: virtual waiting room

Without a waiting room, 50k concurrent users would:
1. All hit the DB simultaneously.
2. Most would fail with lock contention.
3. DB would be overwhelmed with retries.

**Virtual waiting room design:**
```
1. When event enters "onsale" mode, enable waiting room
2. User requests a queue token: { position: 4521, estimatedWaitSeconds: 90 }
3. Waiting room admits N users/second (N = inventory service capacity, e.g., 500/s)
4. Admitted users get a short-lived "admission token" (JWT, 10-minute TTL)
5. Booking API requires valid admission token for hold creation
6. Users poll for their position every 5 seconds (or use SSE/WebSocket)
```

**Queue implementation:** Redis sorted set, scored by join timestamp.
```
ZADD queue:{eventId} {timestamp} {userId}
ZRANK queue:{eventId} {userId}  → position
// Admission worker: ZPOPMIN queue:{eventId} N every second
```

### Hold expiry and release

```
// Hold expiry worker (runs every 30s)
expiredHolds = SELECT * FROM holds
  WHERE status = 'ACTIVE' AND expires_at < now()
  LIMIT 1000;

for each hold:
  BEGIN TRANSACTION
    UPDATE seat_inventory
      SET status = 'AVAILABLE', hold_id = NULL, held_until = NULL, version = version+1
      WHERE hold_id = ? AND status = 'HELD'
    UPDATE holds SET status = 'EXPIRED' WHERE hold_id = ?
  COMMIT
  Invalidate seat map cache for event
```

**Read path also checks expiry:** Even if the expiry worker is delayed, the hold creation flow checks `heldUntil < now()` inline. A seat with an expired hold is treated as AVAILABLE for new holds.

### Confirm hold → order

```
confirmOrder(holdId, paymentToken, idempotencyKey):

1. Check idempotency key → return existing order if found
2. Load hold: must be ACTIVE and not expired
3. Call payment service (synchronous, with timeout)
4. On payment success:
   BEGIN TRANSACTION
     UPDATE holds SET status = 'CONFIRMED'
     UPDATE seat_inventory SET status = 'SOLD' WHERE hold_id = ?
     INSERT INTO orders (orderId, holdId, paymentId, ...)
     INSERT INTO tickets (one per seat)
     INSERT INTO outbox (OrderConfirmed event)
   COMMIT
5. Return orderId and tickets

On payment failure:
  UPDATE holds SET status = 'RELEASED'
  UPDATE seat_inventory SET status = 'AVAILABLE' WHERE hold_id = ?
  Return 402

On API crash after payment success but before DB commit:
  Recovery: query payment service for status by idempotencyKey
  If payment succeeded: retry the DB transaction (idempotent by holdId)
  If payment failed: release hold
```

---

## 7. Low-level design

### Schema

```sql
CREATE TABLE seat_inventory (
  event_id    UUID        NOT NULL,
  seat_id     VARCHAR(20) NOT NULL,
  section     VARCHAR(50),
  row_label   VARCHAR(10),
  seat_number VARCHAR(10),
  price       BIGINT      NOT NULL,
  status      VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
  hold_id     UUID,
  held_until  TIMESTAMPTZ,
  version     INT         NOT NULL DEFAULT 0,
  PRIMARY KEY (event_id, seat_id)
) PARTITION BY HASH (event_id);  -- partition by event for isolation

CREATE INDEX idx_seat_hold ON seat_inventory(hold_id)
  WHERE hold_id IS NOT NULL;
CREATE INDEX idx_seat_expiry ON seat_inventory(held_until)
  WHERE status = 'HELD';

CREATE TABLE holds (
  hold_id         UUID        PRIMARY KEY,
  user_id         UUID        NOT NULL,
  event_id        UUID        NOT NULL,
  seat_ids        TEXT[]      NOT NULL,
  status          VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
  expires_at      TIMESTAMPTZ NOT NULL,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
  idempotency_key VARCHAR(64) UNIQUE
);

CREATE INDEX idx_holds_expiry ON holds(expires_at)
  WHERE status = 'ACTIVE';
CREATE INDEX idx_holds_user ON holds(user_id, created_at DESC);
```

### Seat map cache (Redis)

```
// Cache key: seatmap:{eventId}
// Value: JSON array of { seatId, status, price }
// TTL: 5 seconds (stale is OK for browsing; hold creation always hits DB)

// Invalidation: on every hold creation, expiry, or confirmation:
DEL seatmap:{eventId}
// Next read will repopulate from DB
```

### QR code / ticket token

```
// Signed JWT containing: ticketId, eventId, seatId, issuedAt
// Signed with event-specific private key
// Venue scanner verifies signature + checks ticketId not in revocation list
// Short-lived (24h before event) to limit forgery window
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Hold mechanism | Postgres row-level locks + version | Redis SETNX | Postgres is durable; Redis crash could cause oversells |
| Onsale spike | Virtual waiting room | Rate limiting only | Rate limiting still allows 50k concurrent DB writes; waiting room serializes admission |
| Seat map reads | Cache (5s TTL) | Always read from DB | Seat map reads are 100x more frequent than writes; cache reduces DB load dramatically |
| Hold expiry | Worker + inline check | Worker only | Worker delay could allow expired holds to block new purchases; inline check is the safety net |
| Inventory sharding | Partition by eventId | Shard by seatId | Event-level isolation means one hot event doesn't affect others; natural boundary |
| Ticket QR code | Signed JWT | Database lookup at gate | Signed JWT works offline; no DB call needed at venue entry |

### Scaling triggers

- **Onsale events**: increase waiting room admission rate by adding inventory service instances. Each instance handles ~500 hold attempts/s.
- **Event catalog**: cache event listings in Redis (TTL 60s). Add read replicas for event catalog DB.
- **Seat inventory DB**: partition by `eventId` hash. Each partition handles one or more events. Hot events get dedicated partitions.
- **Ticket issuance**: if ticket generation (QR codes) becomes slow, pre-generate tickets for confirmed orders asynchronously.

### Observability

- Hold success rate (% of hold attempts that succeed — alert if < 50% during onsale, indicates contention).
- Hold conflict rate (% of hold attempts that fail due to seat already held).
- Hold expiry rate (% of holds that expire without confirmation — indicates payment funnel issues).
- Waiting room queue depth and admission rate.
- Seat map cache hit rate (should be > 95%).
- Hold expiry worker lag (time between hold expiry and seat release — alert if > 2 minutes).
- Oversell count (should always be 0 — alert immediately if > 0).
- Payment conversion rate (% of holds that become confirmed orders).
