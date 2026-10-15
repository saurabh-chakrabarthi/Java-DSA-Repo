# System Design Foundations

Use these topics as a compact reference while creating cases. Each topic should answer: what problem does this solve, what does it cost, and when should it not be used?

## Study order

1. **Requirements and SLOs** — functional scope, exclusions, latency percentiles, availability, durability, RPO/RTO.
2. **Estimation** — QPS, read/write ratio, peak multiplier, object size, retention, bandwidth, and growth.
3. **Data and consistency** — relational versus non-relational models, indexing, transactions, replication, partitioning, idempotency.
4. **Serving path** — load balancing, caching, CDNs, pagination, rate limits, API versioning.
5. **Async work** — queues, streams, consumer groups, retries, dead-letter queues, backpressure, outbox pattern.
6. **Reliability** — timeouts, circuit breaking, graceful degradation, multi-AZ/region decisions, disaster recovery.
7. **Security and operations** — authentication, authorization, encryption, PII/PHI classification, audit logs, metrics, tracing, alerts.

## Reusable questions

- What is the source of truth and who is allowed to write it?
- Which operation needs strong consistency, and where is eventual consistency acceptable?
- What happens if a client retries, a consumer receives a message twice, or a dependency is unavailable?
- Which metric would reveal that the user experience is failing before a user reports it?
