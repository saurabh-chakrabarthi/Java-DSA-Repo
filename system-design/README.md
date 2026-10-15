# System Design Workspace

This is a spec-driven interview-prep track. Each case is an end-to-end design exercise, not a collection of disconnected diagrams.

## How to work a case

1. Copy [the case template](templates/system-design-case.md) into `cases/<sequence>-<system-name>/README.md`.
2. Write the problem statement, functional requirements, explicit exclusions, NFRs, and assumptions before choosing technology.
3. Derive traffic, storage, bandwidth, and availability targets from the assumptions.
4. Add the API/data model, HLD, critical flows, failure handling, and LLD only as the requirements justify them.
5. Record alternatives and rejected tradeoffs. Finish with [the design-review checklist](templates/design-review-checklist.md).

An estimate is a model, not a fact: label every input and show the arithmetic. An unknown is acceptable; an unstated assumption is not.

## Layout

```text
system-design/
├── foundations/       # Concepts and reusable decision frameworks
├── cases/             # Complete, independently reviewable exercises
├── templates/         # Starting points and review criteria
└── ROADMAP.md         # Recommended dependency-aware study sequence
```

## Case status

| Case | Status | Focus |
| --- | --- | --- |
| [URL shortener](cases/01-url-shortener/README.md) | Reference case | Requirements to production tradeoffs |
| [Rate limiter](cases/02-rate-limiter/README.md) | Complete | Distributed quotas, hot keys, graceful enforcement |
| [Notification service](cases/03-notification-service/README.md) | Complete | Fanout, provider delivery, preferences, retries |
| [Chat service](cases/04-chat-service/README.md) | Complete | WebSockets, ordering, presence, offline delivery |
| [File storage](cases/05-file-storage/README.md) | Complete | Multipart upload, metadata, object lifecycle |
| [Payment service](cases/06-payment-service/README.md) | Complete | Idempotency, ledgers, provider webhooks, reconciliation |
| [Ticket booking](cases/07-ticket-booking/README.md) | Complete | Inventory holds, contention, payment confirmation |
| [Ride hailing](cases/08-ride-hailing/README.md) | Complete | Geospatial dispatch, state machines, live location |
| [Search autocomplete](cases/09-search-autocomplete/README.md) | Complete | Prefix index, ranking, low-latency serving |
| [Web crawler](cases/10-web-crawler/README.md) | Complete | URL frontier, politeness, deduplication, distributed workers |
| [E-commerce checkout](cases/11-ecommerce-checkout/README.md) | Complete | Cart, inventory reservation, orders, saga recovery |
| Parking lot | Planned | Object modeling, allocation, pricing |
| Car rental | Planned | Inventory, reservations, consistency |
| Appointment scheduling / EHR | Planned | Privacy, booking contention, auditability |
| Social feed | Planned | Fanout, ranking boundaries, caching |
| Audio/video streaming | Planned | Media pipeline, CDN, adaptive delivery |
| Distributed compute platform | Planned | Scheduling, retries, coordination |

Use [ROADMAP.md](ROADMAP.md) to build these in order, and [foundations/README.md](foundations/README.md) for the concepts each case exercises.
