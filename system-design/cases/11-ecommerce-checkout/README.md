# E-Commerce Checkout

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 5/10 — had saga concept but skipped the saga step log schema, compensation logic, and inventory reservation mechanics
**After score:** 9/10

---

## 1. Problem statement

Convert a shopper's cart into a durable order by pricing items, reserving inventory, authorizing payment, and initiating fulfillment without duplicate charges or overselling. The hardest problems are: coordinating across three independent services (inventory, payment, fulfillment) without a distributed transaction, and recovering correctly from partial failures at any step.

---

## 2. Requirements

### Functional requirements

- Maintain shopping carts (add/remove items, apply coupons).
- Calculate a quoted checkout total (prices snapshotted at checkout time).
- Reserve inventory during checkout; release on failure.
- Authorize and capture payment.
- Create a confirmed order and emit fulfillment work.
- A customer retry must create at most one logical order and payment.

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Correctness | Zero duplicate charges; zero oversells | Both are P0 incidents |
| Checkout latency | p95 < 5 seconds | Checkout is the highest-value user action |
| Availability | 99.99% for order status reads | Customer must always see their order |
| Throughput | 5k checkout attempts/s peak | Promotion spikes |
| Consistency | Eventual across services; strong within each service | Saga pattern; each service is strongly consistent internally |

### Explicitly out of scope

- Product catalog search and recommendations.
- Warehouse routing optimization.
- Tax calculation (external service).
- Fraud scoring (external service; checkout consumes its output).

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Checkout attempts/s | 5k peak | — | Saga orchestrator must handle 5k concurrent sagas |
| Conversion rate | 60% | 5k × 0.6 = 3k confirmed orders/s | Inventory and payment services sized for 3k writes/s |
| Cart size | avg 3 items | 3k × 3 = 9k inventory reservation ops/s | Inventory service must handle 9k atomic decrements/s |
| Order record size | ~5 KB | 3k × 5 KB = 15 MB/s | ~1.3 TB/year; Postgres with partitioning |
| Reservation TTL | 10 minutes | — | Expiry worker runs every 60s |
| Saga step log | ~1 KB/step × 5 steps | 5k × 5 KB = 25 MB/s | Append-only; partition by date |

Peak multiplier: 10x (flash sales). Order retention: 7 years. Cart retention: 30 days.

---

## 4. API and domain model

### REST API

```
// Cart management
POST /v1/carts
  Response 201: { cartId }

PUT /v1/carts/{cartId}/items
  Body: { skuId, quantity }
  Response 200: { cartId, items, subtotal }

DELETE /v1/carts/{cartId}/items/{skuId}

// Checkout
POST /v1/carts/{cartId}/checkout
  Headers: Idempotency-Key: <uuid>
  Body: {
    shippingAddress: { ... },
    paymentMethod:   { type: "CARD_TOKEN", token: "..." },
    couponCode?:     "SAVE10"
  }
  Response 202: { orderId, status: "PENDING" }
  // Async: checkout saga runs in background
  // Client polls GET /v1/orders/{orderId} for final status

// Order status
GET /v1/orders/{orderId}
  Response 200: {
    orderId, status, items, pricingSnapshot,
    paymentId, estimatedDelivery, trackingNumber?
  }

// Order cancellation (before fulfillment starts)
POST /v1/orders/{orderId}/cancel
  Response 200 | 409 (already shipped)
```

### Domain model

```
Cart {
  cartId:    UUID PK
  userId:    UUID NOT NULL
  items:     JSONB   -- [{ skuId, quantity, priceAtAdd }]
  couponCode: VARCHAR NULL
  updatedAt: TIMESTAMPTZ
  expiresAt: TIMESTAMPTZ   -- 30 days
}

Order {
  orderId:          UUID PK
  userId:           UUID NOT NULL
  cartId:           UUID NOT NULL
  status:           ENUM(PENDING, CONFIRMED, CANCELED, FAILED, REFUNDED)
  pricingSnapshot:  JSONB NOT NULL   -- prices locked at checkout time
  shippingAddress:  JSONB NOT NULL
  subtotal:         BIGINT NOT NULL
  discountAmount:   BIGINT NOT NULL DEFAULT 0
  taxAmount:        BIGINT NOT NULL
  totalAmount:      BIGINT NOT NULL
  paymentId:        UUID NULL
  idempotencyKey:   VARCHAR(64) NOT NULL UNIQUE
  createdAt:        TIMESTAMPTZ NOT NULL
  confirmedAt:      TIMESTAMPTZ NULL
}

InventoryReservation {
  reservationId: UUID PK
  orderId:       UUID NOT NULL
  skuId:         VARCHAR NOT NULL
  quantity:      INT NOT NULL
  status:        ENUM(ACTIVE, CONFIRMED, RELEASED, EXPIRED)
  expiresAt:     TIMESTAMPTZ NOT NULL
  createdAt:     TIMESTAMPTZ NOT NULL
}

SagaLog {                              -- append-only, durable saga state
  sagaId:      UUID PK
  orderId:     UUID NOT NULL
  currentStep: ENUM(STARTED, PRICING, RESERVING, PAYING, CONFIRMING, COMPLETED,
                    COMPENSATING, FAILED)
  stepHistory: JSONB   -- array of { step, status, timestamp, payload }
  createdAt:   TIMESTAMPTZ NOT NULL
  updatedAt:   TIMESTAMPTZ NOT NULL
}
```

State transitions:
- `Order.status`: `PENDING → CONFIRMED | FAILED | CANCELED`
- `InventoryReservation.status`: `ACTIVE → CONFIRMED | RELEASED | EXPIRED`
- `SagaLog.currentStep`: forward path or compensation path (see below)

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Checkout API                                         │
Client ────────────▶│  1. Idempotency check                                │
                    │  2. Create Order(PENDING) + SagaLog(STARTED)         │
                    │  3. Return 202 { orderId }                           │
                    │  4. Enqueue saga to Saga Executor                    │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Saga Executor (orchestrator pattern)                │
                    │                                                       │
                    │  Step 1: PRICING                                     │
                    │    - Fetch current prices for all SKUs               │
                    │    - Apply coupon discount                           │
                    │    - Snapshot prices onto Order                      │
                    │                                                       │
                    │  Step 2: RESERVING                                   │
                    │    - Call Inventory Service: reserve(orderId, items) │
                    │    - On failure: → FAILED (no compensation needed)   │
                    │                                                       │
                    │  Step 3: PAYING                                      │
                    │    - Call Payment Service: authorize(orderId, amount)│
                    │    - On failure: → COMPENSATING (release reservation)│
                    │                                                       │
                    │  Step 4: CONFIRMING                                  │
                    │    - Update Order.status = CONFIRMED                 │
                    │    - Confirm inventory reservation                   │
                    │    - Publish OrderConfirmed event                    │
                    │    - Emit fulfillment work                           │
                    │                                                       │
                    │  Compensation path (on payment failure):             │
                    │    - Release inventory reservation                   │
                    │    - Update Order.status = FAILED                    │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Inventory Service                                    │
                    │  - Atomic reservation (conditional decrement)        │
                    │  - Reservation TTL: 10 minutes                       │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Payment Service                                      │
                    │  - Authorize + capture                               │
                    │  - Idempotent by orderId                             │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Fulfillment Service (downstream)                    │
                    │  - Consumes OrderConfirmed event from Kafka          │
                    │  - Assigns warehouse, generates shipping label       │
                    └──────────────────────────────────────────────────────┘
```

---

## 6. Critical flows and failure behavior

### The saga pattern: why and how

A distributed transaction (2PC) across inventory + payment + order DB would require all three services to be available simultaneously and would create tight coupling. Instead, we use a saga: a sequence of local transactions, each with a compensating transaction if a later step fails.

```
Forward path:
  STARTED → PRICING → RESERVING → PAYING → CONFIRMING → COMPLETED

Compensation path (triggered by payment failure):
  PAYING_FAILED → COMPENSATING → release reservation → FAILED
```

**Key property:** Each step is idempotent. If the saga executor crashes and restarts, it re-reads the `SagaLog` and continues from the last completed step. No step is executed twice.

### Saga step: RESERVING (inventory)

```
reserveInventory(orderId, items):

BEGIN TRANSACTION (in Inventory Service DB)
  for each item in items:
    // Conditional decrement: only if available >= requested
    updated = UPDATE inventory
      SET available = available - item.quantity,
          reserved  = reserved  + item.quantity
      WHERE sku_id = item.skuId
        AND available >= item.quantity  -- prevents oversell

    if updated == 0:
      ROLLBACK
      return INSUFFICIENT_STOCK { skuId: item.skuId }

  INSERT INTO inventory_reservations
    (reservationId, orderId, skuId, quantity, status='ACTIVE', expiresAt=now+10min)
    for each item
COMMIT

return SUCCESS { reservationId }
```

**Why a single transaction for all items?** Partial reservation (some items reserved, others not) is worse than no reservation — it holds inventory for items the customer can't actually buy. All-or-nothing is the correct semantic.

### Saga step: PAYING (payment)

```
authorizePayment(orderId, amount, paymentToken):
  // Payment service is called with orderId as idempotency key
  // If this call is retried (saga executor restart), payment service returns
  // the original result instead of charging again

  result = paymentService.authorize(
    orderId=orderId,
    amount=amount,
    token=paymentToken,
    idempotencyKey=orderId  // deterministic
  )

  if result.status == AUTHORIZED:
    sagaLog.recordStep(PAYING, SUCCESS, { paymentId: result.paymentId })
    return SUCCESS

  if result.status == DECLINED:
    sagaLog.recordStep(PAYING, FAILED, { declineCode: result.declineCode })
    return PAYMENT_DECLINED  // triggers compensation

  if result.status == TIMEOUT:
    // Don't know if payment succeeded
    // Recovery: query payment service by orderId
    // Saga executor will retry this step
    throw RetryableException
```

### Compensation: releasing inventory

```
releaseReservation(orderId):
  BEGIN TRANSACTION
    reservations = SELECT * FROM inventory_reservations
      WHERE order_id = orderId AND status = 'ACTIVE'

    for each reservation:
      UPDATE inventory
        SET available = available + reservation.quantity,
            reserved  = reserved  - reservation.quantity
        WHERE sku_id = reservation.skuId

      UPDATE inventory_reservations
        SET status = 'RELEASED'
        WHERE reservation_id = reservation.reservationId
  COMMIT
```

**Compensation is idempotent:** If called twice (saga executor restart during compensation), the second call finds `status = 'RELEASED'` and does nothing.

### Failure scenarios

| Failure point | What happens | Recovery |
|---|---|---|
| Crash after Order(PENDING) created, before RESERVING | Saga log shows STARTED | Saga executor restarts, begins from PRICING |
| Crash after RESERVING, before PAYING | Saga log shows RESERVING complete | Saga executor restarts, begins PAYING (reservation still active) |
| Crash after PAYING succeeds, before CONFIRMING | Saga log shows PAYING complete | Saga executor restarts, begins CONFIRMING (payment already authorized) |
| Payment timeout (don't know if charged) | Saga executor retries PAYING | Payment service returns original result (idempotent by orderId) |
| Compensation fails (inventory service down) | Saga stuck in COMPENSATING | Retry with exponential backoff; alert on-call if stuck > 5 minutes |
| Reservation expires before payment completes | Expiry worker releases reservation | Saga detects RELEASED reservation during CONFIRMING → FAILED |

### Reservation expiry

```
// Expiry worker (runs every 60s):
expiredReservations = SELECT * FROM inventory_reservations
  WHERE status = 'ACTIVE' AND expires_at < now()

for each reservation:
  BEGIN TRANSACTION
    UPDATE inventory
      SET available = available + reservation.quantity,
          reserved  = reserved  - reservation.quantity
    UPDATE inventory_reservations SET status = 'EXPIRED'
  COMMIT

  // Notify saga executor: reservation expired
  // Saga will transition Order to FAILED
```

---

## 7. Low-level design

### Saga log schema

```sql
CREATE TABLE saga_logs (
  saga_id      UUID        PRIMARY KEY,
  order_id     UUID        NOT NULL UNIQUE,
  current_step VARCHAR(30) NOT NULL,
  step_history JSONB       NOT NULL DEFAULT '[]',
  created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at   TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- step_history example:
-- [
--   { "step": "PRICING",   "status": "SUCCESS", "ts": "...", "payload": {...} },
--   { "step": "RESERVING", "status": "SUCCESS", "ts": "...", "reservationId": "..." },
--   { "step": "PAYING",    "status": "FAILED",  "ts": "...", "declineCode": "INSUFFICIENT_FUNDS" }
-- ]
```

### Inventory schema

```sql
CREATE TABLE inventory (
  sku_id      VARCHAR(50) PRIMARY KEY,
  available   INT         NOT NULL CHECK (available >= 0),
  reserved    INT         NOT NULL CHECK (reserved >= 0),
  total       INT         NOT NULL,
  version     INT         NOT NULL DEFAULT 0,
  updated_at  TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Invariant: available + reserved <= total
-- available >= 0 enforced by CHECK constraint (prevents oversell at DB level)

CREATE TABLE inventory_reservations (
  reservation_id UUID        PRIMARY KEY,
  order_id       UUID        NOT NULL,
  sku_id         VARCHAR(50) NOT NULL,
  quantity       INT         NOT NULL,
  status         VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
  expires_at     TIMESTAMPTZ NOT NULL,
  created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_reservations_order ON inventory_reservations(order_id);
CREATE INDEX idx_reservations_expiry ON inventory_reservations(expires_at)
  WHERE status = 'ACTIVE';
```

### Order schema

```sql
CREATE TABLE orders (
  order_id         UUID        PRIMARY KEY,
  user_id          UUID        NOT NULL,
  cart_id          UUID        NOT NULL,
  status           VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  pricing_snapshot JSONB       NOT NULL,
  shipping_address JSONB       NOT NULL,
  subtotal         BIGINT      NOT NULL,
  discount_amount  BIGINT      NOT NULL DEFAULT 0,
  tax_amount       BIGINT      NOT NULL,
  total_amount     BIGINT      NOT NULL,
  payment_id       UUID,
  idempotency_key  VARCHAR(64) NOT NULL UNIQUE,
  created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
  confirmed_at     TIMESTAMPTZ
) PARTITION BY RANGE (created_at);

CREATE INDEX idx_orders_user ON orders(user_id, created_at DESC);
CREATE INDEX idx_orders_status ON orders(status)
  WHERE status IN ('PENDING', 'CONFIRMED');
```

### Pricing snapshot (why it matters)

```json
// Prices are snapshotted at checkout time onto the Order record
// This means: even if catalog prices change after checkout, the order
// always reflects what the customer was shown and agreed to pay

{
  "items": [
    { "skuId": "SKU-123", "name": "Widget", "quantity": 2,
      "unitPrice": 1999, "lineTotal": 3998 },
    { "skuId": "SKU-456", "name": "Gadget", "quantity": 1,
      "unitPrice": 4999, "lineTotal": 4999 }
  ],
  "coupon": { "code": "SAVE10", "discountAmount": 899 },
  "subtotal": 8997,
  "tax": 720,
  "total": 8818,
  "snapshotAt": "2024-06-15T14:30:00Z"
}
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Coordination | Saga (orchestrator) | 2PC distributed transaction | 2PC requires all services available simultaneously; saga tolerates partial failures |
| Saga type | Orchestrator (central saga log) | Choreography (events) | Orchestrator is easier to reason about, debug, and monitor; choreography is harder to trace |
| Inventory reservation | DB conditional decrement | Optimistic lock | Conditional decrement is atomic and prevents oversell at DB level; optimistic lock requires retry |
| Payment idempotency | orderId as payment idempotency key | Random key per attempt | Deterministic key enables safe retry after timeout without double-charge |
| Pricing | Snapshot at checkout | Live prices at confirmation | Snapshot prevents price changes between checkout and confirmation from surprising the customer |
| Compensation | Explicit release step | Reservation TTL only | TTL is a safety net; explicit release is faster and more reliable |

### Scaling triggers

- **Checkout throughput**: saga executor is stateless; scale horizontally. Each instance handles a subset of active sagas.
- **Inventory service**: shard by `skuId` hash when single instance exceeds 10k writes/s. Hot SKUs (flash sale items) get dedicated shards.
- **Order DB**: partition by `created_at` month. Archive orders older than 2 years to data warehouse.
- **Flash sales**: use a virtual waiting room (same pattern as ticket booking) to serialize admission. Pre-warm inventory cache for known hot SKUs.
- **Cart service**: carts are read-heavy; cache in Redis with write-through. Cart data is low-value (can be rebuilt from session).

### Observability

- Checkout success rate (% of attempts that result in CONFIRMED order — alert if < 55%).
- Saga step failure rate per step (which step fails most often?).
- Compensation success rate (% of compensations that complete — alert if < 99%).
- Sagas stuck in COMPENSATING > 5 minutes (alert immediately).
- Inventory reservation conflict rate (% of reservations that fail due to insufficient stock).
- Payment decline rate (% of payment attempts declined — alert if > 10%, may indicate fraud spike).
- Reservation expiry rate (% of reservations that expire before confirmation — indicates slow payment flow).
- Order confirmation latency p50/p95/p99 (end-to-end saga duration).

### Security

- Payment tokens are never stored in the order service — passed through to payment service and discarded.
- Pricing snapshot is immutable after creation — prevents price manipulation attacks.
- Idempotency keys are scoped to userId — prevents one user from replaying another user's checkout.
- Inventory reservation is tied to orderId — a reservation cannot be used for a different order.
- Rate-limit checkout attempts per user (e.g., 10/minute) to prevent inventory hoarding attacks.
