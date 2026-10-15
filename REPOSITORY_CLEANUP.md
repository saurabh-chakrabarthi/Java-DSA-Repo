# Repository Cleanup Plan

The initial transformation established a stable information architecture without moving the canonical DSA source code. The completed cleanup removed stale duplicate/generated material; remaining actions need an explicit decision.

## Preserved

- `src/main/java/com/java/poc/`: Java DSA, curated LeetCode practice, design collections, and Java fundamentals.
- `src/test/`: existing test and utility material.
- `DSA_PREP.md`: DSA navigation and practice sequence.

## Added structure

- `system-design/`: spec-driven system-design curriculum and cases.
- `docs/resources/`: non-source interview-prep PDFs.
- Root README: two-track landing page and repository boundaries.
- `.gitignore`: protection for IDE/build artifacts and future local-secret files.

## Completed cleanup

- Removed the stale nested `Java_Master_POC/` duplicate. Root `src/` is the sole canonical DSA source tree.
- Removed generated output: `target/` and `out/`.
- Removed IDE/workspace metadata: `.idea/`, `.metadata/`, `.vscode/`, and root `.iml` files.
- Moved the Blind 75 PDFs to `docs/resources/`.

## Deliberate follow-up decisions

| Area | Current observation | Recommended action | Why it is not automatic |
| --- | --- | --- | --- |
| `github-recovery-codes.txt` | Recovery codes were rotated and the tracked file was removed | No further action for these codes | Rotation invalidates the values; prior commits may still contain the old file. |
| `VisitCounter/` | Separate Java service | Adopt as a documented system-design implementation or split into its own repository | It has a distinct build/lifecycle. |
| Root `pom.xml` dependencies | Broad/older practice dependencies | Audit and minimize after confirming which examples need each library | Dependency removal can break existing exercises. |

## Safe cleanup sequence

1. Recovery codes have been rotated and the working-tree copy removed.
2. Decide whether `VisitCounter` becomes a standalone repository or a named implementation companion to a system-design case.
3. Audit Maven dependencies and upgrade/remove them in separately testable changes.
