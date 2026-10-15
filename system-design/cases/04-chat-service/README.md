# Chat Service

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 4/10 — correct concepts but missing sequencing algorithm, WebSocket connection routing, message schema, and fan-out math
**After score:** 9/10

---

## 1. Problem statement

Support one-to-one and group messaging with near-real-time delivery, durable history, read receipts, typing indicators, and offline notification handoff. The system must preserve message order within a conversation, handle device reconnects gracefully, and scale to millions of concurrent WebSocket connections.

---

## 2. Requirements

### Functional requirements

- Send and receive messages in 1:1 and group conversations (up to 1,000 members).
- Preserve message order within a conversation; deliver to all online members.
- Persist message history; support pagination and device sync after reconnect.
- Track read receipts per user per conversation.
- Support typing indicators and online presence (best-effort, not durable).
- Hand off to notification service when recipient is offline.

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Message delivery latency | p99 < 500 ms online-to-online | Core product experience |
| Message ordering | Strict per conversation | Out-of-order messages break UX |
| Availability | 99.99% for message send | Users expect chat to always work |
| Durability | No acknowledged message is lost | Acknowledged = server assigned a sequence number |
| Concurrent connections | 10M simultaneous WebSocket connections | Scale target |
| History retention | 90 days hot, indefinite cold | Compliance and UX |

### Explicitly out of scope

- End-to-end encryption (E2EE) — requires client-side key management.
- Media upload — use file storage service; chat stores the file reference URL.
- Voice/video calling.

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| DAU | 10 million | — | — |
| Messages/day | 20 per DAU | 200M messages/day | 2,300 writes/s avg; 23,000/s peak |
| Message size | 1 KB avg | 200M × 1 KB = 200 GB/day | ~6 TB/month before replication |
| 90-day hot storage | 200 GB/day × 90 | ~18 TB | Partition by conversationId; time-based archival |
| Concurrent connections | 10M | 10M WebSocket connections | ~100 gateway servers at 100k connections each |
| Group fanout | avg 10 members, max 1,000 | 2,300 × 10 = 23,000 fanout ops/s avg | Async fanout for large groups |
| Presence updates | 10M users × 1 heartbeat/30s | ~333k presence writes/s | Redis TTL-based presence; not DB |

Peak multiplier: 10x. Hot retention: 90 days. Cold: object storage (indefinite).

---

## 4. API and domain model

### WebSocket protocol (client ↔ gateway)

```json
// Client → Server: send message
{
  "type": "SEND",
  "clientMessageId": "client-uuid-123",   // client-generated, for dedup
  "conversationId": "conv-abc",
  "body": "Hello!",
  "replyToSequence": null
}

// Server → Client: message acknowledged (assigned server sequence)
{
  "type": "ACK",
  "clientMessageId": "client-uuid-123",
  "serverSequence": 4521,
  "serverTimestamp": 1718000000000
}

// Server → Client: new message delivered
{
  "type": "MESSAGE",
  "conversationId": "conv-abc",
  "senderId": "user-xyz",
  "serverSequence": 4521,
  "body": "Hello!",
  "serverTimestamp": 1718000000000
}

// Client → Server: mark read
{
  "type": "READ",
  "conversationId": "conv-abc",
  "upToSequence": 4521
}

// Client → Server: typing indicator (fire-and-forget, no ACK)
{ "type": "TYPING", "conversationId": "conv-abc" }

// Client → Server: sync after reconnect
{ "type": "SYNC", "conversationId": "conv-abc", "fromSequence": 4500 }
```

### REST API (for history, conversation management)

```
GET /v1/conversations/{id}/messages?before=<sequence>&limit=50
POST /v1/conversations          -- create 1:1 or group
POST /v1/conversations/{id}/members
DELETE /v1/conversations/{id}/members/{userId}
GET /v1/conversations/{id}/members
```

### Domain model

```
Message {
  conversationId:   VARCHAR PK (partition key)
  sequence:         BIGINT PK (sort key)    -- monotonic per conversation
  messageId:        UUID UNIQUE             -- for idempotency
  clientMessageId:  VARCHAR                 -- client-provided dedup key
  senderId:         UUID NOT NULL
  body:             TEXT
  mediaUrl:         TEXT NULL
  replyToSequence:  BIGINT NULL
  createdAt:        TIMESTAMPTZ NOT NULL
  deletedAt:        TIMESTAMPTZ NULL        -- soft delete
}

Conversation {
  conversationId: UUID PK
  type:           ENUM(ONE_TO_ONE, GROUP)
  name:           TEXT NULL
  createdAt:      TIMESTAMPTZ
  lastSequence:   BIGINT                   -- denormalized for quick sync
}

ConversationMember {
  conversationId:    UUID
  userId:            UUID
  joinedAt:          TIMESTAMPTZ
  lastReadSequence:  BIGINT DEFAULT 0      -- for read receipts
  PRIMARY KEY (conversationId, userId)
}

// Ephemeral (Redis only, not DB):
DeviceSession {
  userId:        UUID
  deviceId:      VARCHAR
  gatewayNodeId: VARCHAR    -- which gateway server holds this connection
  connectedAt:   TIMESTAMP
  TTL:           30 seconds (refreshed by heartbeat)
}

PresenceState {
  userId:    UUID
  status:    ENUM(ONLINE, AWAY, OFFLINE)
  lastSeen:  TIMESTAMP
  TTL:       60 seconds
}
```

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  WebSocket Gateway Cluster (stateful, 100k conn each) │
Device ─WS──────────▶  - Auth & session registration in Redis              │
                    │  - Route incoming frames to Conversation Service      │
                    │  - Receive delivery frames from Pub/Sub               │
                    └──────────────────────┬───────────────────────────────┘
                                           │ gRPC
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Conversation Service (sharded by conversationId)     │
                    │  - Assign sequence number (atomic increment)          │
                    │  - Persist message to Message Store                   │
                    │  - Publish to Kafka: messages.{conversationId}        │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Fanout Service (Kafka consumer)                      │
                    │  - Load ConversationMember list                       │
                    │  - For each member: look up DeviceSession in Redis    │
                    │  - Online: publish to gateway pub/sub channel         │
                    │  - Offline: enqueue to Notification Service           │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Message Store (Cassandra / DynamoDB)                 │
                    │  Partition key: conversationId                        │
                    │  Sort key: sequence (DESC for pagination)             │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Redis Cluster                                         │
                    │  - DeviceSession registry (TTL 30s)                   │
                    │  - Presence state (TTL 60s)                           │
                    │  - Gateway pub/sub (Redis Pub/Sub or Streams)         │
                    └──────────────────────────────────────────────────────┘
```

**Why Cassandra/DynamoDB for messages?**
- Write-heavy append workload (never update, rarely delete).
- Natural partition by `conversationId`; sort by `sequence` for ordered reads.
- Scales horizontally without resharding application logic.
- Alternatives: PostgreSQL with table partitioning (simpler ops, harder to scale past ~10 TB hot).

---

## 6. Critical flows and failure behavior

### Send message flow

1. Client sends `SEND` frame over WebSocket to gateway.
2. Gateway forwards to Conversation Service (sharded by `conversationId`).
3. Conversation Service:
   a. Check `clientMessageId` uniqueness (Redis or DB unique index) — return existing ACK if duplicate.
   b. Atomically increment `lastSequence` for the conversation (Redis INCR or DB atomic update).
   c. Persist `Message` row with assigned sequence.
   d. Publish `MessageCreated` event to Kafka.
   e. Return ACK with `serverSequence` to gateway → gateway sends ACK to sender.
4. Fanout Service consumes Kafka event → delivers to online recipients via pub/sub → offline recipients via notification service.

**Failure cases:**
- Client sends duplicate `clientMessageId` (retry after timeout): step 3a returns existing sequence → client receives same ACK. No duplicate message stored.
- Conversation Service crashes after DB write but before Kafka publish: Kafka publish is retried on recovery (outbox pattern or Kafka transactions). Message is not lost.
- Gateway crashes while holding connections: clients reconnect to any gateway. On reconnect, client sends `SYNC` with last known sequence → server returns missed messages.
- Fanout Service slow: messages are durable in Kafka (24h retention). Fanout catches up; online delivery may be delayed but messages are not lost.

### Reconnect / sync flow

1. Client reconnects to any gateway (gateways are stateless for routing).
2. Client sends `SYNC { conversationId, fromSequence: 4500 }`.
3. Gateway queries Message Store: `WHERE conversationId = ? AND sequence > 4500 ORDER BY sequence ASC LIMIT 100`.
4. Returns missed messages in order.
5. Client re-registers DeviceSession in Redis.

### Group fanout for large groups (1,000 members)

- Synchronous fanout to 1,000 members would block the Conversation Service.
- Fanout is always async via Kafka consumer.
- For groups > 100 members, fanout worker batches Redis lookups (pipeline 100 HGET calls).
- For groups > 500 members, fanout worker uses a separate high-parallelism consumer group.

### Typing indicators and presence

- Typing: client sends `TYPING` frame → gateway publishes to Redis Pub/Sub channel for the conversation → other gateways holding members of that conversation forward to their connected clients. No DB write. TTL: 5 seconds (client re-sends every 3s while typing).
- Presence: client heartbeat every 30s → gateway updates Redis TTL. If TTL expires → user is OFFLINE. No DB write for presence.

---

## 7. Low-level design

### Sequence assignment

```
// Option A (chosen): Redis INCR per conversation
// Fast, atomic, no DB round-trip on hot path
// Risk: Redis restart loses counter → recover from max(sequence) in Message Store on startup

INCR conv:{conversationId}:seq
// Returns new sequence atomically

// Option B: DB sequence (PostgreSQL SEQUENCE or atomic UPDATE)
UPDATE conversations SET last_sequence = last_sequence + 1
  WHERE conversation_id = ? RETURNING last_sequence;
// Slower (DB write on every message) but durable without recovery logic
// Chosen for simplicity if message rate < 1,000/s per conversation
```

For this scale (23,000 writes/s across all conversations), Redis INCR is preferred. Recovery: on Redis restart, seed counter from `SELECT MAX(sequence) FROM messages WHERE conversation_id = ?`.

### Message Store schema (Cassandra)

```cql
CREATE TABLE messages (
  conversation_id  UUID,
  sequence         BIGINT,
  message_id       UUID,
  client_message_id VARCHAR,
  sender_id        UUID,
  body             TEXT,
  media_url        TEXT,
  reply_to_sequence BIGINT,
  created_at       TIMESTAMP,
  deleted_at       TIMESTAMP,
  PRIMARY KEY (conversation_id, sequence)
) WITH CLUSTERING ORDER BY (sequence DESC)
  AND default_time_to_live = 7776000;  -- 90 days in seconds

CREATE INDEX ON messages (message_id);  -- for idempotency lookup
```

### DeviceSession in Redis

```
HSET session:user:{userId}:device:{deviceId}
  gatewayNodeId  "gateway-07"
  connectedAt    1718000000
EXPIRE session:user:{userId}:device:{deviceId} 30

// Fanout lookup: KEYS session:user:{userId}:* (or use a set per user)
SADD user:{userId}:devices {deviceId}
EXPIRE user:{userId}:devices 30
```

### Read receipt update

```sql
-- Postgres (ConversationMember table)
UPDATE conversation_members
  SET last_read_sequence = GREATEST(last_read_sequence, ?)
  WHERE conversation_id = ? AND user_id = ?;
-- GREATEST ensures monotonic update (no going backwards)
```

### Unread count (denormalized)

```
// Redis counter per (userId, conversationId)
// Incremented by fanout worker when message delivered to offline user
// Reset to 0 when READ frame received
INCRBY unread:{userId}:{conversationId} {count}
GET    unread:{userId}:{conversationId}
SET    unread:{userId}:{conversationId} 0
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Message store | Cassandra (wide-column) | PostgreSQL | Cassandra scales writes horizontally; natural partition by conversationId; no resharding |
| Sequence assignment | Redis INCR | DB atomic increment | Redis is 10x faster; recovery from DB on restart is straightforward |
| Fanout | Async via Kafka | Synchronous in Conversation Service | Async decouples message durability from delivery; large group fanout doesn't block sender ACK |
| Presence | Redis TTL | DB with heartbeat | Presence is ephemeral; DB writes for every heartbeat at 10M users is wasteful |
| Gateway routing | Redis pub/sub per conversation | Direct gRPC to target gateway | Pub/sub avoids needing to know which gateway holds each connection; simpler at scale |
| Typing indicators | Redis pub/sub, no DB | DB write | Typing is ephemeral; DB writes would be 10x the message write rate |

### Scaling triggers

- **Gateway connections**: add gateway instances when any node exceeds 80k connections. Gateways are stateless for routing — add freely.
- **Conversation Service**: shard by `conversationId` hash. Add shards when p99 sequence assignment > 5 ms.
- **Message Store**: Cassandra scales by adding nodes. Trigger at 70% disk utilization or when read latency p99 > 20 ms.
- **Large groups**: groups > 500 members get a dedicated fanout consumer group with higher parallelism.
- **Cold storage**: messages older than 90 days are archived to S3/object storage. History API queries cold storage for old messages (slower, acceptable).

### Observability

- Message delivery latency p50/p95/p99 (sender ACK time and recipient delivery time separately).
- WebSocket connection count per gateway (alert if > 90k).
- Fanout lag (Kafka consumer group lag — alert if > 5s for online delivery).
- Sequence assignment latency (Redis INCR p99).
- Reconnect rate (spike indicates gateway instability).
- Duplicate message rate (clientMessageId collision rate — should be 0).
- Offline notification handoff rate (% of messages that go to notification service).
- Message Store write/read latency p99.

### Security

- WebSocket connections require a valid JWT; gateway validates on connect and periodically re-validates.
- Message body is stored as-is (not E2EE in this design). At-rest encryption at the storage layer.
- Rate-limit message sends per user (e.g., 60 messages/minute) to prevent spam.
- Group membership changes require authorization (only admins can add/remove members in groups > 10).
