# <System name>

**Status:** Draft | In review | Complete
**Last reviewed:** YYYY-MM-DD
**Scope level:** Interview / production-minded / implementation-backed

## 1. Problem statement

Describe the user problem and the business outcome in two or three sentences.

## 2. Requirements

### Functional requirements

- FR-1:

### Non-functional requirements

| Dimension | Target | Why it matters |
| --- | --- | --- |
| Latency | p95 / p99 | |
| Availability | | |
| Durability | | |
| Consistency | | |
| Security / privacy | | |

### Explicitly out of scope

-

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
| --- | --- | --- | --- |
| Active users | | | |
| Requests | | | |
| Storage | | | |
| Bandwidth | | | |

State a peak multiplier and retention period.

## 4. API and domain model

Document request/response shapes, error behavior, pagination/idempotency semantics, and core entities. Identify each entity's source of truth and lifecycle state transitions.

## 5. High-level design

Describe components, data stores, ownership, and synchronous/asynchronous boundaries. Include a diagram (Mermaid, Excalidraw export, or image) when it clarifies a relationship.

## 6. Critical flows and failure behavior

For each important flow, list the steps and state what happens on timeout, duplicate delivery, partial failure, and retry.

## 7. Low-level design

Define the central interfaces/classes or services, persistence schema/indexes, invariants, concurrency controls, and validation rules.

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
| --- | --- | --- | --- |
| | | | |

Cover scaling triggers, migrations, observability, security, and cost controls.

## 9. Review checklist

Complete [design-review-checklist.md](design-review-checklist.md) before marking the case complete.
