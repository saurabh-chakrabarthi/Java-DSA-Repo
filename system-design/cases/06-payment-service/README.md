# Payment Service

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 5/10 — had idempotency and ledger concepts but missing double-entry accounting detail, reconciliation algorithm, and webhook ordering logic
**After score:** 9/10

---

## 1. Problem statement

Accept payments through external processors while ensuring a customer is never charged twice and internal order/payment records can be reconciled with provider truth. The service must handle network failures between our system and the provider gracefully, maintain an immutable audit ledger, and never store raw card data.

---

## 2. Requirements

### Functional requirements

- Authorize, capture, refund, and void payments for an order.
- Idempotently process client requests and provider webhooks.
- Maintain an immutable double-entry ledger for every money movement.
- Expose payment status to the order service and customer.
- Run nightly reconciliation against provider settlement reports.

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Correctness | Zero double charges | A double charge is a P0 incident and regulatory risk |
| Availability | 99.99% for payment status reads | Checkout must always show payment state |
| Latency | p95 < 3s for authorization | Checkout UX; provider calls dominate latency |
| Durability | Immutable ledger; 7-year retention | Regulatory and audit requirement |
| Consistency | Strong for payment state transitions | Eventual consistency risks double-charge window |

### Explicitly out of scope

- Becoming a card processor or acquiring bank.
- Calculating tax, FX conversion, or pricing.
- Fraud scoring (separate ML service; payment service consumes its output).

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Peak payment rate | 100 orders/s | — | Modest throughput; correctness >> performance |
| Payment record size | ~2 KB | 100/s × 2 KB × 86,400 = ~17 GB/day | Small; Postgres handles this easily |
| Ledger entries | 4 per payment (debit/credit × 2 accounts) | 400 entries/s peak | Append-only; partition by date for archival |
| Provider latency | 500ms–2s | — | Async capture pattern; synchronous auth with timeout |
| Reconciliation | Daily, 1M transactions/day | ~12 transactions/s avg | Batch job; not on critical path |
| Webhook volume | ~2 webhooks per payment | 200/s peak | Idempotent processing; out-of-order handling required |

Peak multiplier: 10x. Ledger retention: 7 years. Payment record hot retention: 2 years.

---

## 4. API and domain model

### REST API

```
// Initiate payment (authorize + capture in one step, or authorize only)
POST /v1/payments
  Headers: Idempotency-Key: <uuid>
  Body: {
    orderId:        UUID,
    amount:         { value: 1999, currency: "USD" },  // in cents
    paymentMethod:  { type: "CARD_TOKEN", token: "<provider-token>" },
    captureMode:    ENUM(IMMEDIATE, MANUAL),
    metadata:       { orderId, customerId }
  }
  Response 201: { paymentId, status: "AUTHORIZED" | "CAPTURED", providerReference }
  Response 402: payment declined (with declineCode)
  Response 409: idempotency conflict

// Capture a previously authorized payment
POST /v1/payments/{paymentId}/capture
  Headers: Idempotency-Key: <uuid>
  Body: { amount? }   // partial capture supported
  Response 200: { paymentId, status: "CAPTURED" }

// Refund
POST /v1/payments/{paymentId}/refunds
  Headers: Idempotency-Key: <uuid>
  Body: { amount, reason }
  Response 201: { refundId, status: "PENDING" }

// Void (cancel before capture)
POST /v1/payments/{paymentId}/void
  Response 200: { paymentId, status: "VOIDED" }

// Get payment status
GET /v1/payments/{paymentId}
  Response 200: { paymentId, status, amount, currency, ledgerEntries, refunds }

// Provider webhook (internal, verified by signature)
POST /internal/webhooks/{provider}
  Body: provider-specific event
```

### Domain model

```
PaymentIntent {
  paymentId:          UUID PK
  orderId:            UUID NOT NULL UNIQUE   -- one payment per order
  merchantId:         UUID NOT NULL
  amount:             BIGINT NOT NULL        -- in smallest currency unit (cents)
  currency:           CHAR(3) NOT NULL
  status:             ENUM(CREATED, PROCESSING, AUTHORIZED, CAPTURED, FAILED,
                           CANCELED, VOIDED, REFUND_PENDING, REFUNDED, PARTIALLY_REFUNDED)
  providerReference:  VARCHAR               -- provider's transaction ID
  providerName:       VARCHAR               -- "stripe", "braintree", etc.
  idempotencyKey:     VARCHAR(64) NOT NULL
  captureMode:        ENUM(IMMEDIATE, MANUAL)
  failureCode:        VARCHAR NULL
  failureMessage:     TEXT NULL
  createdAt:          TIMESTAMPTZ NOT NULL
  updatedAt:          TIMESTAMPTZ NOT NULL
  version:            INT NOT NULL DEFAULT 0  -- optimistic lock
}

Refund {
  refundId:          UUID PK
  paymentId:         UUID FK → PaymentIntent
  amount:            BIGINT NOT NULL
  reason:            TEXT
  status:            ENUM(PENDING, SUCCEEDED, FAILED)
  providerRefundId:  VARCHAR
  idempotencyKey:    VARCHAR(64) NOT NULL
  createdAt:         TIMESTAMPTZ NOT NULL
}

LedgerEntry {                               -- IMMUTABLE, append-only
  entryId:       UUID PK
  paymentId:     UUID NOT NULL
  refundId:      UUID NULL
  entryType:     ENUM(DEBIT, CREDIT)
  accountType:   ENUM(CUSTOMER, MERCHANT, PLATFORM_FEE, REFUND_LIABILITY)
  amount:        BIGINT NOT NULL
  currency:      CHAR(3) NOT NULL
  description:   TEXT
  occurredAt:    TIMESTAMPTZ NOT NULL
  -- No UPDATE or DELETE ever; corrections are new entries
}

OutboxEvent {
  eventId:    UUID PK
  eventType:  VARCHAR NOT NULL
  payload:    JSONB NOT NULL
  createdAt:  TIMESTAMPTZ NOT NULL
  published:  BOOLEAN NOT NULL DEFAULT false
}
```

State machine for `PaymentIntent`:
```
CREATED → PROCESSING → AUTHORIZED → CAPTURED
                    ↘ FAILED
                    ↘ CANCELED (before AUTHORIZED)
AUTHORIZED → VOIDED
CAPTURED → REFUND_PENDING → REFUNDED | PARTIALLY_REFUNDED
```

**Invariant:** Transitions are monotonic. A payment can never go from `CAPTURED` back to `AUTHORIZED`. All transitions are validated against the current state before applying.

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Payment API                                          │
Checkout ──POST────▶│  1. Idempotency check                                │
                    │  2. Persist PaymentIntent(CREATED)                   │
                    │  3. Call ProviderAdapter.authorize()                  │
                    │  4. Update status + write LedgerEntry + Outbox       │
                    │     (single transaction)                              │
                    │  5. Return result to caller                           │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Provider Adapter                                     │
                    │  - Normalizes Stripe/Braintree/Adyen APIs            │
                    │  - Sends provider idempotency key                    │
                    │  - Maps provider errors to internal codes            │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Payment Provider (Stripe / Braintree / Adyen)       │
                    └──────────────────────┬───────────────────────────────┘
                                           │ webhooks
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Webhook Handler                                      │
                    │  - Verify signature                                   │
                    │  - Idempotent state transition                        │
                    │  - Write LedgerEntry + Outbox (single transaction)   │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Reconciliation Job (nightly)                        │
                    │  - Download provider settlement report               │
                    │  - Compare with LedgerEntry records                  │
                    │  - Flag discrepancies for manual review              │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Outbox Relay → Kafka → Order Service, Analytics     │
                    └──────────────────────────────────────────────────────┘
```

---

## 6. Critical flows and failure behavior

### Authorization flow (the hardest part)

The core challenge: we call an external provider. The call can fail in three ways:
1. **Provider rejects** (card declined): clear failure, update status to `FAILED`.
2. **Provider succeeds, we crash before persisting**: on recovery, we don't know if the charge happened.
3. **Network timeout**: provider may or may not have processed the charge.

**Solution: write-before-call + provider idempotency key**

```
1. Persist PaymentIntent(CREATED) with idempotencyKey
2. Set status = PROCESSING (marks that we've started the provider call)
3. Call provider.authorize(providerIdempotencyKey = paymentId)
   // providerIdempotencyKey is our paymentId — deterministic, safe to retry
4. On success:
   BEGIN TRANSACTION
     UPDATE payment_intents SET status = AUTHORIZED, provider_reference = ?
     INSERT INTO ledger_entries (DEBIT customer, CREDIT merchant)
     INSERT INTO outbox_events (PaymentAuthorized)
   COMMIT
5. On provider failure (4xx): UPDATE status = FAILED
6. On timeout / network error:
   - Do NOT update status yet
   - Recovery job: query provider using providerIdempotencyKey to get actual status
   - Apply the result (idempotent)
```

**Why `providerIdempotencyKey = paymentId`?** If we retry the provider call with the same key, the provider returns the original result instead of charging again. This is the standard pattern used by Stripe, Adyen, etc.

### Webhook processing

Webhooks arrive asynchronously and may be:
- **Duplicate**: same event delivered twice (at-least-once delivery from provider).
- **Out of order**: `payment.captured` arrives before `payment.authorized`.
- **Late**: arrives hours after the synchronous call already updated the status.

```
handleWebhook(event):
  1. Verify HMAC signature (reject if invalid)
  2. Look up PaymentIntent by providerReference
  3. Check if this event has already been processed (idempotency by providerEventId)
  4. Validate state transition: is the new status reachable from current status?
     - If yes: apply transition + write ledger + outbox (single transaction)
     - If no (e.g., CAPTURED webhook but already CAPTURED): log and ignore (idempotent)
     - If out-of-order (e.g., CAPTURED before AUTHORIZED): buffer for 30s, retry
  5. Acknowledge webhook (return 200) — even if we ignored it
```

**Critical:** Always return 200 to the provider after processing (even for duplicates). Returning 4xx/5xx causes the provider to retry indefinitely.

### Reconciliation

```
Nightly reconciliation job:
1. Download provider settlement report (CSV/API) for previous day
2. For each provider transaction in report:
   a. Look up LedgerEntry by providerReference
   b. Compare: amount, currency, status, timestamp
   c. If match: mark as reconciled
   d. If mismatch: create ReconciliationDiscrepancy record
   e. If not found in our DB: create ReconciliationDiscrepancy (ghost charge)
3. For each LedgerEntry not in provider report:
   a. Create ReconciliationDiscrepancy (missing from provider)
4. Alert on-call if discrepancy count > 0
```

---

## 7. Low-level design

### Double-entry ledger

Every money movement creates two entries (debit + credit) that sum to zero. This is the same principle as accounting.

```
Payment of $19.99 (customer pays merchant):
  DEBIT  customer_receivable  $19.99
  CREDIT merchant_payable     $19.99

Refund of $19.99:
  DEBIT  merchant_payable     $19.99
  CREDIT customer_receivable  $19.99

Platform fee of $0.50:
  DEBIT  merchant_payable     $0.50
  CREDIT platform_revenue     $0.50
```

**Why double-entry?** Any discrepancy (debits ≠ credits) immediately signals a bug. Auditors understand it. Corrections are new entries, not updates — full history is preserved.

### Idempotency implementation

```sql
-- Unique constraint on (merchantId, idempotencyKey)
CREATE UNIQUE INDEX idx_payment_idempotency
  ON payment_intents(merchant_id, idempotency_key);

-- On duplicate request:
-- INSERT ... ON CONFLICT (merchant_id, idempotency_key) DO NOTHING RETURNING *
-- If RETURNING is empty, SELECT the existing record and return it
```

### Schema

```sql
CREATE TABLE payment_intents (
  payment_id         UUID        PRIMARY KEY,
  order_id           UUID        NOT NULL UNIQUE,
  merchant_id        UUID        NOT NULL,
  amount             BIGINT      NOT NULL,
  currency           CHAR(3)     NOT NULL,
  status             VARCHAR(30) NOT NULL,
  provider_reference VARCHAR(255),
  provider_name      VARCHAR(50),
  idempotency_key    VARCHAR(64) NOT NULL,
  capture_mode       VARCHAR(20) NOT NULL,
  failure_code       VARCHAR(50),
  failure_message    TEXT,
  created_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at         TIMESTAMPTZ NOT NULL DEFAULT now(),
  version            INT         NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX idx_payment_idempotency
  ON payment_intents(merchant_id, idempotency_key);
CREATE INDEX idx_payment_order ON payment_intents(order_id);
CREATE INDEX idx_payment_provider_ref ON payment_intents(provider_reference)
  WHERE provider_reference IS NOT NULL;

CREATE TABLE ledger_entries (
  entry_id      UUID        PRIMARY KEY,
  payment_id    UUID        NOT NULL,
  refund_id     UUID,
  entry_type    VARCHAR(10) NOT NULL,   -- DEBIT | CREDIT
  account_type  VARCHAR(30) NOT NULL,
  amount        BIGINT      NOT NULL,
  currency      CHAR(3)     NOT NULL,
  description   TEXT,
  occurred_at   TIMESTAMPTZ NOT NULL DEFAULT now()
  -- NO updated_at, NO deleted_at — immutable
);

CREATE INDEX idx_ledger_payment ON ledger_entries(payment_id);
-- Partition by occurred_at for archival:
-- PARTITION BY RANGE (occurred_at)
```

### Provider adapter interface

```java
interface ProviderAdapter {
    AuthorizationResult authorize(AuthorizationRequest request);
    CaptureResult capture(String providerReference, Money amount, String idempotencyKey);
    RefundResult refund(String providerReference, Money amount, String idempotencyKey);
    VoidResult voidPayment(String providerReference);
    // For recovery: query provider for the status of a transaction
    TransactionStatus queryStatus(String providerIdempotencyKey);
}

// StripeAdapter, BraintreeAdapter, AdyenAdapter implement this interface
// Each maps provider-specific error codes to internal PaymentFailureCode enum:
//   CARD_DECLINED, INSUFFICIENT_FUNDS, EXPIRED_CARD, FRAUD_SUSPECTED,
//   PROVIDER_ERROR, NETWORK_ERROR
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Idempotency | Write-before-call + provider idempotency key | Optimistic lock only | Provider idempotency key prevents double-charge even if we retry after timeout |
| Ledger | Double-entry, immutable, append-only | Mutable balance table | Immutable ledger enables audit, corrections without data loss, and discrepancy detection |
| Webhook vs polling | Webhooks as primary, polling as recovery | Polling only | Webhooks are lower latency; polling is the fallback for missed webhooks |
| Provider abstraction | ProviderAdapter interface | Direct Stripe SDK calls | Enables provider switching without changing business logic; A/B testing providers |
| Reconciliation | Nightly batch | Real-time streaming | Provider settlement reports are daily; real-time reconciliation is not possible |
| State transitions | Monotonic, validated | Free-form updates | Prevents impossible states (e.g., CAPTURED → AUTHORIZED); bugs surface immediately |

### Scaling triggers

- **Write throughput**: at 1,000 payments/s, consider read replicas for status queries. At 10,000/s, shard by `merchantId`.
- **Ledger size**: partition `ledger_entries` by month. Archive partitions older than 2 years to cold storage (still queryable via foreign data wrapper or data warehouse).
- **Provider diversification**: add a second provider for redundancy. Route by card type (Visa → Stripe, Amex → Braintree) or by availability.
- **Multi-currency**: add FX conversion service. Ledger stores original currency + converted amount + exchange rate at time of transaction.

### Observability

- Authorization success rate (alert if < 95% — may indicate provider issue or fraud spike).
- Provider latency p50/p95/p99 (alert if p95 > 3s).
- Idempotency key collision rate (should be low; spike indicates client retry storm).
- Webhook processing lag (time from provider event to our DB update).
- Reconciliation discrepancy count (alert if > 0).
- PROCESSING state age (payments stuck in PROCESSING > 5 minutes need recovery job attention).
- Refund success rate.

### Security

- Never store raw card numbers, CVV, or full PAN. Store only provider tokens.
- All provider API keys stored in secrets manager (AWS Secrets Manager / HashiCorp Vault), not in config files.
- Webhook signature verification is mandatory — reject any webhook without a valid HMAC.
- Payment API requires mTLS or signed JWTs from internal services.
- PCI DSS scope: this service is in-scope. Isolate in a separate network segment. Limit access to payment DB to payment service only.
- Audit log every status transition with actor, timestamp, and reason.
