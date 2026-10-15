# Interview Prep: Java DSA + System Design

One place for Java-based DSA practice and interview-ready system-design exercises.

## Start here

| Track | Entry point | What it contains |
| --- | --- | --- |
| DSA | [DSA_PREP.md](DSA_PREP.md) | Phase-based problem practice and links to existing Java solutions. |
| System design | [system-design/README.md](system-design/README.md) | Spec-driven foundations, end-to-end cases, templates, and a learning roadmap. |
| Reference material | [docs/resources/](docs/resources/) | Blind 75 PDF cheat sheets and other non-source study material. |

## Repository boundaries

- `src/main/java/com/java/poc/` is the existing Java DSA and Java fundamentals collection. Treat it as stable; system-design work must not require moving or rewriting it.
- `system-design/` is a documentation-first design workspace. A case can later gain a small implementation or prototype, but its specification remains the source of truth.
- `VisitCounter/` is a separate Java service. It is intentionally not part of the system-design track until explicitly adopted.

## Working agreement

For every new system-design exercise, start from [the case template](system-design/templates/system-design-case.md), review it against [the quality checklist](system-design/templates/design-review-checklist.md), and only then add diagrams or code.
