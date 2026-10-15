# System Design Roadmap

Build depth by reusing the same reasoning framework as systems become more complex.

| Stage | Deliverable | Prerequisites | Design muscles |
| --- | --- | --- | --- |
| 0. Foundations | Notes in `foundations/` | None | Latency, availability, data ownership, partitioning, caching, queues, consistency |
| 1. URL shortener | Complete reference case | Stage 0 | API design, IDs, cache-aside, read scaling, durability |
| 2. Rate limiter + notifications | Complete cases | Stage 1 | Distributed counters, queues, retries, delivery preferences |
| 3. Chat + file storage | Complete cases | Stage 2 | Persistent connections, ordering, object metadata, lifecycle |
| 4. Payments + ticket booking | Complete cases | Stage 3 | Idempotency, ledgers, reservations, contention |
| 5. Ride hailing + autocomplete | Complete cases | Stage 3 | Geospatial indexing, state machines, low-latency ranking |
| 6. Web crawler + e-commerce checkout | Complete cases | Stages 2–4 | Distributed work, deduplication, inventory, sagas |
| 7. Parking lot + car rental | Planned cases | Stage 4 | LLD, allocation, pricing, reservation holds |
| 8. Appointment scheduling / EHR | Planned case | Stages 4–6 | Double-booking prevention, authorization, auditing, PII/PHI boundaries |
| 9. Social feed | Planned case | Stages 0–1 | Write fanout, timelines, ranking contracts, cache invalidation |
| 10. Audio/video streaming | Planned case | Social feed | Upload/transcode pipeline, object storage, CDN, adaptive bitrate |
| 11. Distributed compute | Planned case | Stages 0–1 | Scheduler, leases, idempotency, retries, observability |

## Definition of done for a case

- The scope, exclusions, and success criteria are clear enough to prevent solution-first design.
- NFRs are measurable and tied to a user or business outcome.
- Capacity estimates state inputs, formulas, peak factors, and the resulting design implication.
- The HLD identifies ownership, synchronous versus asynchronous paths, and data stores.
- At least one critical path is expressed as a sequence with failure behavior.
- The LLD covers the central domain model, API contracts, invariants, and concurrency control.
- Tradeoffs and operational signals explain how the design behaves after launch.
