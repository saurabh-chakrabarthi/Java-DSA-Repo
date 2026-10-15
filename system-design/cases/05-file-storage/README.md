# File Storage Service

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 4/10 — correct shape but missing multipart upload mechanics, signed URL construction, state machine detail, and virus scan flow
**After score:** 9/10

---

## 1. Problem statement

Let users upload, retrieve, share, and delete large files reliably without routing file bytes through application servers. The service must support resumable multipart uploads up to 5 GB, enforce access control, generate time-limited download links, and integrate post-upload processing (virus scan, thumbnail generation) without blocking the upload acknowledgement.

---

## 2. Requirements

### Functional requirements

- Initiate a multipart upload session; upload parts directly to object storage via signed URLs.
- Complete an upload by submitting a part manifest; the file becomes available after verification.
- Generate time-limited, signed download URLs (not proxied through app servers).
- Share files with other users (read-only or read-write ACL).
- Soft-delete files; hard-delete after retention period.
- Support file metadata (name, MIME type, tags, custom attributes).

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Upload throughput | 10 TB/day ingress | 1M uploads × 10 MB avg |
| Download latency | p95 < 200 ms for first byte (CDN hit) | Core UX for file access |
| Durability | 99.999999999% (11 nines) | Files are user data; loss is unacceptable |
| Availability | 99.99% for metadata API; 99.9% for upload initiation | Downloads via CDN are more available than origin |
| Consistency | Strong for metadata; eventual for post-processing status | File must be accessible immediately after complete; scan status can lag |

### Explicitly out of scope

- Collaborative document editing (Google Docs-style).
- Full-text content search within files.
- Video transcoding pipeline.

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Uploads/day | 1 million | — | — |
| Avg file size | 10 MB | 1M × 10 MB = 10 TB/day ingress | Direct-to-object-store upload is mandatory; app servers cannot handle this |
| Max file size | 5 GB | 5 GB / 5 MB part = 1,000 parts max | Multipart with 5 MB parts; S3-compatible APIs support up to 10,000 parts |
| 30-day retention | 10 TB/day × 30 = 300 TB | ~300 TB hot storage | Object storage (S3/GCS) is cost-effective at this scale |
| Metadata size | ~2 KB/file | 1M × 2 KB = 2 GB/day | Postgres can handle this; index on ownerId, createdAt |
| Download requests | 10:1 read:write ratio | 10M downloads/day = 116/s avg | CDN absorbs >95% of downloads; origin rarely hit |
| Concurrent uploads | 10k simultaneous | 10k × 5 MB/part = 50 GB in-flight | Object store handles this natively |

Peak multiplier: 5x. Hot retention: 30 days. Cold/archive: 7 years (compliance).

---

## 4. API and domain model

### REST API

```
// Step 1: Initiate upload
POST /v1/uploads
  Body: { fileName, mimeType, sizeBytes, partCount, metadata? }
  Response 201: {
    uploadId,
    parts: [
      { partNumber: 1, signedUrl: "https://s3.../...", expiresAt },
      { partNumber: 2, signedUrl: "https://s3.../...", expiresAt },
      ...
    ]
  }

// Step 2: Client uploads each part directly to object store using signed URLs
// (No app server involvement — bytes go directly to S3/GCS)

// Step 3: Complete upload
POST /v1/uploads/{uploadId}/complete
  Body: { parts: [{ partNumber: 1, etag: "abc123" }, ...] }
  Response 202: { fileId, status: "VERIFYING" }
  // File becomes AVAILABLE after checksum verification and virus scan

// Poll status
GET /v1/files/{fileId}
  Response 200: { fileId, status, name, mimeType, sizeBytes, downloadUrl?, createdAt }

// Generate download URL
POST /v1/files/{fileId}/download-url
  Body: { expiresInSeconds: 3600 }
  Response 200: { signedUrl, expiresAt }

// Share file
POST /v1/files/{fileId}/shares
  Body: { recipientId, permission: "READ" | "READ_WRITE", expiresAt? }
  Response 201: { shareId }

// Delete file
DELETE /v1/files/{fileId}
  Response 204  // soft delete; hard delete after retention period
```

### Domain model

```
UploadSession {
  uploadId:          UUID PK
  ownerId:           UUID NOT NULL
  objectKey:         VARCHAR NOT NULL    -- pre-assigned S3 key
  providerUploadId:  VARCHAR             -- S3 multipart upload ID
  fileName:          VARCHAR NOT NULL
  mimeType:          VARCHAR NOT NULL
  sizeBytes:         BIGINT NOT NULL
  partCount:         INT NOT NULL
  status:            ENUM(INITIATED, UPLOADING, COMPLETING, FAILED, EXPIRED)
  checksumExpected:  VARCHAR NULL        -- client-provided SHA-256 (optional)
  expiresAt:         TIMESTAMPTZ         -- unfinished parts expire after 24h
  createdAt:         TIMESTAMPTZ NOT NULL
}

File {
  fileId:       UUID PK
  ownerId:      UUID NOT NULL
  objectKey:    VARCHAR NOT NULL UNIQUE  -- S3 object key
  objectVersion: VARCHAR                 -- S3 version ID
  fileName:     VARCHAR NOT NULL
  mimeType:     VARCHAR NOT NULL
  sizeBytes:    BIGINT NOT NULL
  checksum:     VARCHAR NOT NULL         -- SHA-256 of complete file
  status:       ENUM(VERIFYING, AVAILABLE, QUARANTINED, DELETED)
  metadata:     JSONB
  createdAt:    TIMESTAMPTZ NOT NULL
  deletedAt:    TIMESTAMPTZ NULL
}

FileShare {
  shareId:        UUID PK
  fileId:         UUID FK → File
  grantedBy:      UUID NOT NULL
  recipientId:    UUID NOT NULL
  permission:     ENUM(READ, READ_WRITE)
  expiresAt:      TIMESTAMPTZ NULL
  createdAt:      TIMESTAMPTZ NOT NULL
}
```

State transitions:
- `UploadSession`: `INITIATED → UPLOADING → COMPLETING → (triggers File creation) → EXPIRED | FAILED`
- `File`: `VERIFYING → AVAILABLE | QUARANTINED → DELETED`

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Metadata API                                         │
Client ─POST /uploads▶  - Create UploadSession                            │
                    │  - Generate signed part URLs (S3 presigned)          │
                    │  - Return part URLs to client                         │
                    └──────────────────────────────────────────────────────┘
                           │ (client uploads parts directly — no app server)
                    ┌──────▼───────────────────────────────────────────────┐
                    │  Object Store (S3 / GCS)                              │
                    │  - Receives multipart upload parts directly           │
                    │  - Stores raw bytes; generates ETags per part         │
                    └──────────────────────────────────────────────────────┘
                           │ (client calls POST /uploads/{id}/complete)
                    ┌──────▼───────────────────────────────────────────────┐
                    │  Metadata API: complete()                             │
                    │  - Validate part manifest (partNumbers, ETags)        │
                    │  - Call S3 CompleteMultipartUpload                    │
                    │  - Create File record (status=VERIFYING)              │
                    │  - Publish FileUploaded event to Kafka                │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Post-Processing Workers (Kafka consumers)            │
                    │                                                       │
                    │  ┌─────────────────┐  ┌──────────────────────────┐  │
                    │  │  Virus Scanner  │  │  Thumbnail Generator     │  │
                    │  │  (ClamAV/cloud) │  │  (images/videos only)    │  │
                    │  └────────┬────────┘  └──────────┬───────────────┘  │
                    │           │                       │                  │
                    │           └───────────┬───────────┘                  │
                    │                       ▼                              │
                    │           Update File.status → AVAILABLE             │
                    │           (or QUARANTINED if virus found)            │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Download path                                        │
Client ─GET /files/{id}/download-url                                       │
                    │  - Auth check + ACL check                            │
                    │  - Generate S3 presigned GET URL (TTL 1h)            │
                    │  - Client fetches directly from CDN → S3             │
                    └──────────────────────────────────────────────────────┘
```

**Why direct-to-object-store upload?**
- 10 TB/day through app servers would require enormous bandwidth and compute.
- S3 presigned URLs allow the client to upload directly; the app server only handles metadata.
- S3 multipart upload is atomic from the client's perspective — parts can be uploaded in parallel and resumed.

---

## 6. Critical flows and failure behavior

### Multipart upload flow

1. Client calls `POST /v1/uploads` → API creates `UploadSession`, calls `S3.createMultipartUpload()` to get `providerUploadId`, generates N presigned PUT URLs (one per part, 5 MB each, 1-hour expiry).
2. Client uploads each part directly to S3 using the presigned URLs. Parts can be uploaded in parallel. S3 returns an ETag per part.
3. Client calls `POST /v1/uploads/{id}/complete` with `[{partNumber, etag}]`.
4. API validates: all expected parts present, ETags match. Calls `S3.completeMultipartUpload()`.
5. API creates `File` record (status=`VERIFYING`), publishes `FileUploaded` event, returns `202`.
6. Virus scanner consumes event → downloads file from S3 → scans → updates `File.status` to `AVAILABLE` or `QUARANTINED`.

**Failure cases:**
- Client crashes mid-upload: parts already uploaded are retained by S3 for 24h (lifecycle policy). Client can resume by re-calling `POST /v1/uploads` with the same `uploadId` (idempotent) and re-uploading only missing parts.
- `completeMultipartUpload` fails: `UploadSession` stays in `COMPLETING` state. Client retries `POST /v1/uploads/{id}/complete` — idempotent by `uploadId`.
- Virus scanner finds malware: `File.status = QUARANTINED`. File is not accessible. Owner is notified. Object is deleted from S3 after 7 days.
- Post-processing worker crashes: Kafka offset not committed → event reprocessed. Virus scan is idempotent (re-scanning same object is safe).

### Download flow

1. Client calls `POST /v1/files/{fileId}/download-url`.
2. API checks: file exists, status=`AVAILABLE`, caller has READ permission (owner or valid share).
3. Generate S3 presigned GET URL with 1-hour TTL.
4. Return URL to client.
5. Client fetches directly from CDN (CloudFront/Fastly) → CDN caches the response → S3 on miss.

**Access control:** Presigned URLs are scoped to a specific object key and expire. Even if leaked, they expire. For sensitive files, use shorter TTLs (5 minutes) and log every URL generation.

### Orphan cleanup

- Unfinished `UploadSession` records older than 24h: background job calls `S3.abortMultipartUpload()` and marks session `EXPIRED`.
- S3 lifecycle policy also aborts incomplete multipart uploads after 24h (belt-and-suspenders).
- `File` records in `VERIFYING` state for > 1 hour: alert (post-processing worker may be stuck).

---

## 7. Low-level design

### Signed URL generation

```java
// Part upload URL (presigned PUT)
String generatePartUploadUrl(String objectKey, int partNumber, String providerUploadId) {
    return s3Client.generatePresignedUrl(
        objectKey,
        HttpMethod.PUT,
        Map.of(
            "partNumber", String.valueOf(partNumber),
            "uploadId", providerUploadId
        ),
        Duration.ofHours(1)
    ).toString();
}

// Download URL (presigned GET)
String generateDownloadUrl(String objectKey, Duration ttl) {
    return s3Client.generatePresignedUrl(
        objectKey,
        HttpMethod.GET,
        Collections.emptyMap(),
        ttl
    ).toString();
}
```

### Object key strategy

```
// Key format: {ownerId}/{year}/{month}/{fileId}/{fileName}
// Example: user-abc/2024/06/file-xyz/report.pdf
//
// Why include ownerId: enables S3 bucket policies scoped to owner prefix
// Why include date: enables lifecycle policies and cost analysis by time
// Why include fileId: globally unique, prevents collisions
// Why include fileName: human-readable in S3 console; not used for lookup
```

### Schema

```sql
CREATE TABLE upload_sessions (
  upload_id          UUID        PRIMARY KEY,
  owner_id           UUID        NOT NULL,
  object_key         VARCHAR(500) NOT NULL,
  provider_upload_id VARCHAR(255),
  file_name          VARCHAR(255) NOT NULL,
  mime_type          VARCHAR(100) NOT NULL,
  size_bytes         BIGINT      NOT NULL,
  part_count         INT         NOT NULL,
  status             VARCHAR(20) NOT NULL DEFAULT 'INITIATED',
  checksum_expected  VARCHAR(64),
  expires_at         TIMESTAMPTZ NOT NULL,
  created_at         TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE files (
  file_id        UUID        PRIMARY KEY,
  owner_id       UUID        NOT NULL,
  object_key     VARCHAR(500) NOT NULL UNIQUE,
  object_version VARCHAR(100),
  file_name      VARCHAR(255) NOT NULL,
  mime_type      VARCHAR(100) NOT NULL,
  size_bytes     BIGINT      NOT NULL,
  checksum       VARCHAR(64) NOT NULL,
  status         VARCHAR(20) NOT NULL DEFAULT 'VERIFYING',
  metadata       JSONB,
  created_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
  deleted_at     TIMESTAMPTZ
);

CREATE INDEX idx_files_owner ON files(owner_id, created_at DESC)
  WHERE deleted_at IS NULL;
CREATE INDEX idx_files_status ON files(status)
  WHERE status IN ('VERIFYING', 'QUARANTINED');

CREATE TABLE file_shares (
  share_id     UUID        PRIMARY KEY,
  file_id      UUID        NOT NULL REFERENCES files,
  granted_by   UUID        NOT NULL,
  recipient_id UUID        NOT NULL,
  permission   VARCHAR(20) NOT NULL,
  expires_at   TIMESTAMPTZ,
  created_at   TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_shares_recipient ON file_shares(recipient_id, file_id);
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Upload path | Direct-to-S3 via presigned URLs | Proxy through app server | 10 TB/day cannot go through app servers; S3 handles multipart natively |
| Post-processing | Async (Kafka → workers) | Synchronous in complete() | Virus scan can take seconds; blocking complete() would hurt UX |
| File availability | Only after virus scan | Immediately on complete | Security requirement; quarantined files must never be accessible |
| Download URLs | Presigned S3 URLs (time-limited) | Proxy through app server | CDN can cache presigned URLs; no bandwidth cost on app servers |
| Metadata store | PostgreSQL | DynamoDB | Relational queries (owner + status + date range) are natural in SQL; scale is manageable |
| Object key | Includes ownerId + date | Random UUID only | Enables S3 prefix-based policies and lifecycle rules; human-readable in console |

### Scaling triggers

- **Metadata DB**: partition `files` table by `owner_id` hash when single instance exceeds 10M rows with slow queries. Or migrate to DynamoDB for key-value access patterns.
- **Post-processing**: add more virus scanner instances when scan queue lag > 5 minutes. Virus scanning is CPU-bound and scales horizontally.
- **Storage cost**: implement tiered storage — files not accessed in 30 days move to S3 Infrequent Access; 90 days → Glacier. Lifecycle policies handle this automatically.
- **Multi-region**: replicate object store cross-region for disaster recovery. Metadata DB uses read replicas in secondary regions.

### Observability

- Upload completion rate (% of initiated uploads that complete — alert if < 95%).
- Upload part failure rate (S3 PUT errors).
- Virus scan queue lag (alert if > 5 minutes).
- Files stuck in VERIFYING state > 1 hour (alert).
- Download URL generation latency p99.
- CDN hit rate for downloads (should be > 80%).
- Orphan object count (S3 objects without a File record — indicates cleanup job issues).
- Storage growth rate vs budget.

### Security

- Presigned URLs are scoped to a specific object key, HTTP method, and expiry — cannot be used for other objects.
- S3 bucket is private; no public access. All access is via presigned URLs or IAM roles.
- Virus scan before making files available — quarantined files are never accessible to users.
- File metadata (name, size, owner) is stored in Postgres; object bytes are in S3. Compromise of metadata DB does not expose file bytes.
- Rate-limit upload initiation per user (e.g., 100 uploads/hour) to prevent storage abuse.
