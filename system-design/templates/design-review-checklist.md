# System Design Review Checklist

- [ ] Requirements distinguish must-haves from intentional exclusions.
- [ ] Each NFR has a target and a reason.
- [ ] Estimates expose assumptions, peak load, and retention.
- [ ] The source of truth and write owner are explicit for every critical datum.
- [ ] API contracts describe failure, retry, idempotency, and authorization behavior.
- [ ] The HLD shows data flow and sync/async boundaries.
- [ ] Critical flows cover duplicate events, timeout, dependency failure, and recovery.
- [ ] Consistency decisions are tied to user-visible invariants.
- [ ] Security, privacy, auditing, and data retention are addressed where relevant.
- [ ] Metrics, logs, traces, alerts, and operational runbooks are named.
- [ ] At least one alternative has been compared with its cost and risk.
- [ ] The design states what will change first as load or product scope grows.
