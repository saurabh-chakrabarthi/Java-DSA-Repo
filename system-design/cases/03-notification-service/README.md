# Notification Service

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 4/10 — correct shape but missing fanout math, provider adapter detail, preference schema, and DLQ strategy
**After score:** 9/10

---

## 1. Problem statement

Deliver product events to users via push, email, SMS, or in-app inbox while respecting per-user channel preferences, provider rate limits, retry semantics, and auditability. The service must decouple producers (order service, auth service, etc.) from delivery mechanics so that adding a new channel or swapping a provider requires no producer changes.

---

## 2. Requirements

### Functional requirements

- Accept event-triggered and scheduled notifications with templates and per-user channel preferences.
- Deduplicate notification requests using an idempotency key.
- Provide delivery status per notification and per channel attempt.
- Respect user opt-outs, quiet hours, and channel preferences.
- Support transactional (password reset, OTP) and marketing (campaign) notification types.

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Transactional delivery latency | p95 < 60 seconds end-to-end | Password resets and OTPs are time-sensitive |
| Marketing delivery latency | Best-effort within 4 hours | Campaigns are bulk; latency is acceptable |
| Availability | 99.9% for request acceptance | Producers must not be blocked by provider outages |
| Durability | No accepted notification is silently dropped | Every accepted request must eventually deliver or DLQ |
| Throughput | 1,200 requests/s peak | 10x average of 116/s |

### Explicitly out of scope

- Authoring a rich campaign UI (drag-and-drop email builder).
- Owning third-party provider billing and contracts.
- Real-time delivery receipts from end devices (best-effort from provider webhooks).

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Notification requests | 10M/day | 116/s avg, 1,160/s peak | Queue must absorb 10x bursts |
| Channels per notification | avg 2 (push + email) | 10M × 2 = 20M channel sends/day | Channel queues sized for 20M/day |
| Message size | ~2 KB (template + data) | 20M × 2 KB = 40 GB/day in-flight | Kafka topic retention: 24h, ~40 GB |
| Delivery audit retention | 7 days hot, 90 days cold | 20M × 7 days × ~500 B = ~70 GB hot | Fits in a medium Postgres instance |
| Provider rate limits | Email: 1M/hour, SMS: 100/s, Push: 500k/s | — | Per-channel worker pools sized accordingly |
| Retry budget | 3 attempts, exponential backoff (1m, 5m, 30m) | Max delay: 36 minutes | DLQ after 3 failures |

Peak multiplier: 10x. Quiet hours: 10 PM–8 AM local time (per user timezone).

---

## 4. API and domain model

### REST API

```
POST /v1/notifications
  Headers: Idempotency-Key: <uuid>
  Body: {
    recipientId:    UUID,
    templateId:     STRING,
    channels:       ["PUSH", "EMAIL", "SMS", "IN_APP"],  // requested channels
    templateData:   { key: value },
    priority:       ENUM(TRANSACTIONAL, MARKETING),
    scheduledAt?:   TIMESTAMP   // null = send immediately
  }
  Response 202: { notificationId, status: "ACCEPTED" }
  Response 409: idempotency conflict (returns original notificationId)

GET /v1/notifications/{notificationId}
  Response 200: {
    notificationId, status, channels: [
      { channel, status, attemptCount, lastAttemptAt, providerMessageId }
    ]
  }

POST /v1/notifications/{notificationId}/cancel
  Response 200 | 409 (already delivered)

// Provider webhook (internal, verified by signature)
POST /internal/webhooks/{provider}
  Body: provider-specific delivery receipt
```

### Domain model

```
Notification {
  notificationId: UUID PK
  recipientId:    UUID NOT NULL
  templateId:     VARCHAR NOT NULL
  templateData:   JSONB NOT NULL
  priority:       ENUM(TRANSACTIONAL, MARKETING)
  requestedChannels: VARCHAR[]
  status:         ENUM(ACCEPTED, PROCESSING, PARTIALLY_DELIVERED, DELIVERED, FAILED, CANCELED)
  idempotencyKey: VARCHAR(64) UNIQUE
  scheduledAt:    TIMESTAMPTZ
  createdAt:      TIMESTAMPTZ NOT NULL
}

DeliveryAttempt {
  attemptId:        UUID PK
  notificationId:   UUID FK → Notification
  channel:          ENUM(PUSH, EMAIL, SMS, IN_APP)
  status:           ENUM(PENDING, SENT, DELIVERED, FAILED, BOUNCED)
  providerMessageId: VARCHAR   -- provider's ID for webhook correlation
  attemptNumber:    INT
  attemptedAt:      TIMESTAMPTZ
  errorCode:        VARCHAR
  errorMessage:     TEXT
}

UserPreference {
  userId:         UUID PK
  channel:        ENUM(PUSH, EMAIL, SMS, IN_APP)
  enabled:        BOOLEAN
  quietHoursStart: TIME   -- e.g., 22:00
  quietHoursEnd:   TIME   -- e.g., 08:00
  timezone:       VARCHAR -- e.g., "America/New_York"
}

NotificationTemplate {
  templateId:   VARCHAR PK
  channel:      ENUM(PUSH, EMAIL, SMS, IN_APP)
  subject:      TEXT   -- email subject / push title
  bodyTemplate: TEXT   -- Mustache/Handlebars template
  version:      INT
}
```

State transitions:
- `Notification`: `ACCEPTED → PROCESSING → DELIVERED | PARTIALLY_DELIVERED | FAILED | CANCELED`
- `DeliveryAttempt`: `PENDING → SENT → DELIVERED | FAILED | BOUNCED`

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Notification API                                     │
Producer ──POST────▶│  - Idempotency check                                 │
                    │  - Persist Notification record                        │
                    │  - Publish to Kafka: notifications.incoming           │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Fanout Worker (Kafka consumer group)                 │
                    │  - Load UserPreference                                │
                    │  - Evaluate quiet hours, opt-outs                    │
                    │  - Render template per channel                        │
                    │  - Publish to per-channel queues                      │
                    └──────┬──────────┬──────────┬──────────┬──────────────┘
                           │          │          │          │
                    ┌──────▼──┐ ┌─────▼──┐ ┌────▼───┐ ┌───▼────┐
                    │  Push   │ │ Email  │ │  SMS   │ │ In-App │
                    │ Worker  │ │ Worker │ │ Worker │ │ Worker │
                    │ (FCM/   │ │(SES/   │ │(Twilio/│ │(DB     │
                    │  APNs)  │ │SendGrid│ │Vonage) │ │ write) │
                    └──────┬──┘ └─────┬──┘ └────┬───┘ └───┬────┘
                           │          │          │          │
                    ┌──────▼──────────▼──────────▼──────────▼──────────────┐
                    │  DeliveryAttempt store (Postgres)                     │
                    └──────────────────────────────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Webhook Handler                                       │
                    │  Provider webhooks → verify signature → update        │
                    │  DeliveryAttempt status                               │
                    └──────────────────────────────────────────────────────┘
```

**Key design decisions:**
- Kafka topics per channel (`notifications.push`, `notifications.email`, etc.) allow independent scaling and backpressure per provider.
- Transactional notifications use a high-priority Kafka partition; marketing uses standard partitions.
- In-App channel is a simple DB write (no external provider); it is always attempted regardless of quiet hours.
- Fanout worker is the only place that reads preferences — single responsibility.

---

## 6. Critical flows and failure behavior

### Transactional notification flow

1. Producer calls `POST /v1/notifications` with `priority: TRANSACTIONAL`.
2. API checks idempotency key → persists `Notification(ACCEPTED)` → publishes to `notifications.incoming` (high-priority partition).
3. Fanout worker consumes → loads preferences → skips disabled channels → renders templates → publishes to `notifications.push` and `notifications.email`.
4. Push worker calls FCM/APNs → on success, writes `DeliveryAttempt(SENT)` → webhook later updates to `DELIVERED`.
5. Email worker calls SES → on success, writes `DeliveryAttempt(SENT)`.

**Failure cases:**
- Provider returns 5xx: exponential backoff retry (1m, 5m, 30m). After 3 failures → `DeliveryAttempt(FAILED)` → publish to DLQ topic.
- Provider returns 4xx (invalid token, bad address): no retry → `DeliveryAttempt(FAILED)` with error code. Alert if error rate > 1%.
- Fanout worker crashes mid-processing: Kafka offset not committed → message reprocessed. Idempotency key on `DeliveryAttempt(notificationId, channel)` prevents duplicate sends.
- Quiet hours: message is delayed (re-enqueued with `scheduledAt = quietHoursEnd`), not dropped.

### Provider outage

- Push provider (FCM) down: push channel queue backs up. Email and SMS continue independently.
- Queue depth alert at 10k messages. If queue age > 15 minutes for TRANSACTIONAL, page on-call.
- DLQ messages are reviewed manually or replayed after provider recovery.

### Webhook processing

1. Provider sends webhook to `POST /internal/webhooks/ses`.
2. Verify HMAC signature (reject if invalid — prevents spoofed delivery receipts).
3. Look up `DeliveryAttempt` by `providerMessageId`.
4. Apply monotonic state transition: `SENT → DELIVERED` or `SENT → BOUNCED`. Never go backwards.
5. If `providerMessageId` not found (webhook arrived before our DB write): store in a pending-webhook table, retry correlation after 30s.

---

## 7. Low-level design

### Idempotency

```sql
-- Unique constraint prevents duplicate notification records
CREATE UNIQUE INDEX idx_notifications_idempotency
  ON notifications(idempotency_key);

-- Unique constraint prevents duplicate delivery attempts per channel
CREATE UNIQUE INDEX idx_delivery_attempt_unique
  ON delivery_attempts(notification_id, channel, attempt_number);
```

### Fanout worker: preference evaluation

```java
List<Channel> resolveChannels(Notification n, UserPreference prefs) {
    return n.getRequestedChannels().stream()
        .filter(ch -> prefs.isEnabled(ch))
        .filter(ch -> ch == IN_APP || !prefs.isQuietHour(ch, Instant.now()))
        .collect(toList());
    // IN_APP always bypasses quiet hours
    // If all channels filtered out and priority=TRANSACTIONAL: use fallback channel (email)
}
```

### Provider adapter interface

```java
interface ProviderAdapter {
    // Returns provider's message ID for webhook correlation
    ProviderResult send(RenderedMessage message);
    boolean verifyWebhookSignature(HttpRequest request);
    DeliveryStatus parseWebhookEvent(HttpRequest request);
}

// Implementations: FcmAdapter, ApnsAdapter, SesAdapter, TwilioAdapter
// Each normalizes provider-specific errors to internal error codes:
//   INVALID_TOKEN, RATE_LIMITED, PROVIDER_ERROR, INVALID_ADDRESS
```

### DLQ strategy

```
DLQ topic: notifications.dlq
  - Message includes: original notification, channel, all attempt records, final error
  - Retention: 7 days
  - Consumer: DLQ dashboard (manual review) + auto-replay job (for PROVIDER_ERROR after recovery)
  - Alert: DLQ depth > 100 messages → P2 alert
  - Auto-replay: only for PROVIDER_ERROR; never auto-replay INVALID_TOKEN or INVALID_ADDRESS
```

### Schema

```sql
CREATE TABLE notifications (
  notification_id   UUID        PRIMARY KEY,
  recipient_id      UUID        NOT NULL,
  template_id       VARCHAR(100) NOT NULL,
  template_data     JSONB       NOT NULL,
  priority          VARCHAR(20) NOT NULL,
  requested_channels VARCHAR[]  NOT NULL,
  status            VARCHAR(30) NOT NULL DEFAULT 'ACCEPTED',
  idempotency_key   VARCHAR(64) UNIQUE,
  scheduled_at      TIMESTAMPTZ,
  created_at        TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE delivery_attempts (
  attempt_id          UUID        PRIMARY KEY,
  notification_id     UUID        NOT NULL REFERENCES notifications,
  channel             VARCHAR(20) NOT NULL,
  status              VARCHAR(20) NOT NULL DEFAULT 'PENDING',
  provider_message_id VARCHAR(255),
  attempt_number      INT         NOT NULL DEFAULT 1,
  attempted_at        TIMESTAMPTZ,
  error_code          VARCHAR(50),
  error_message       TEXT
);

CREATE INDEX idx_delivery_by_notification ON delivery_attempts(notification_id);
CREATE INDEX idx_delivery_by_provider_msg ON delivery_attempts(provider_message_id)
  WHERE provider_message_id IS NOT NULL;
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Queue topology | Per-channel Kafka topics | Single topic with channel field | Independent scaling and backpressure per provider; one slow provider doesn't block others |
| Fanout timing | Fanout worker (async, after API accept) | Synchronous fanout in API | API response time independent of preference lookup and template rendering |
| Retry strategy | Exponential backoff, 3 attempts, then DLQ | Infinite retries | Bounded retry prevents queue buildup; DLQ enables manual review |
| Quiet hours | Delay (re-enqueue) | Drop | Dropping is a bad user experience; delay respects preference without data loss |
| Webhook correlation | providerMessageId lookup | Poll provider API | Webhooks are push-based and lower latency; polling is a fallback |
| Template rendering | Fanout worker (before channel queue) | Per-channel worker | Single render per notification; channel workers receive ready-to-send payloads |

### Scaling triggers

- **Fanout throughput**: add Kafka partitions and fanout worker instances when consumer lag > 30s.
- **Email volume**: SES has per-account sending limits; request limit increases or add a second provider (SendGrid) with round-robin routing.
- **SMS cost**: SMS is expensive; add a preference gate requiring explicit opt-in before sending any SMS.
- **In-App inbox**: as in-app volume grows, move from DB polling to WebSocket push (reuse chat service infrastructure).

### Observability

- Queue consumer lag per channel (alert if TRANSACTIONAL lag > 30s).
- Provider error rate per provider per error type.
- DLQ depth (alert if > 100).
- End-to-end delivery latency p50/p95 per priority tier.
- Opt-out violation rate (should be 0 — alert immediately if > 0).
- Template render failure rate.
- Webhook processing lag (time from provider send to DELIVERED status).
