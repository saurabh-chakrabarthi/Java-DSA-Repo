# Ride-Hailing Dispatch

**Status:** Complete
**Scope level:** Production-minded interview design
**Before score:** 4/10 — named H3/geohash but skipped the matching algorithm, driver state machine, and location ingest architecture
**After score:** 9/10

---

## 1. Problem statement

Match riders to nearby available drivers, track a trip through its lifecycle, and provide reliable status/location updates under city-scale load. The hardest problems are: ingesting 200k location updates/second efficiently, finding the best nearby driver in under 10 seconds, and preventing a driver from accepting two trips simultaneously.

---

## 2. Requirements

### Functional requirements

- Rider requests a ride with pickup and dropoff location.
- System estimates pickup time and fare.
- System matches rider to a nearby available driver and sends an offer.
- Driver accepts or declines; system re-dispatches on decline/timeout.
- Track trip state through pickup, in-progress, and completion.
- Provide real-time location updates to rider during trip.

### Non-functional requirements

| Dimension | Target | Why it matters |
|---|---|---|
| Match latency | p95 < 10 seconds from request to driver offer | Rider experience; longer waits cause cancellations |
| Location freshness | Driver location < 10 seconds stale | Stale locations cause poor matches and wrong ETAs |
| Location ingest | 200k writes/s | 1M drivers × 1 update/5s |
| Availability | 99.99% for trip status reads | Rider must always see trip state |
| Correctness | A driver has at most one active trip | Double-assignment is a P0 incident |

### Explicitly out of scope

- Payment settlement (use payment service).
- Map routing algorithm implementation (use Google Maps / HERE Maps API).
- Driver onboarding and background checks.
- Surge pricing calculation.

---

## 3. Assumptions and back-of-the-envelope estimates

| Input | Assumption | Calculation | Design implication |
|---|---|---|---|
| Online drivers | 1 million | — | — |
| Location update frequency | Every 5 seconds | 1M / 5 = 200k writes/s | Cannot use Postgres; need a purpose-built location store |
| Location record size | ~100 bytes | 200k × 100 B = 20 MB/s | Redis or specialized geo store; not object storage |
| Location retention | 30 seconds (current position only) | 1M × 100 B = 100 MB | Fits in Redis with TTL |
| Active trips | 100k simultaneous | — | Trip store is modest; correctness is the challenge |
| Ride requests | 10k/s peak | — | Dispatch service must handle 10k concurrent matching operations |
| City coverage | 100 cities | — | Geo index partitioned by city/region |

Peak multiplier: 3x (rush hour). Location data: ephemeral (30s TTL). Trip data: durable (7 years).

---

## 4. API and domain model

### REST/WebSocket API

```
// Rider: request a ride
POST /v1/ride-requests
  Body: { pickupLocation: { lat, lng }, dropoffLocation: { lat, lng }, rideType }
  Response 201: { requestId, estimatedPickupSeconds, estimatedFare }

// Rider: cancel request
DELETE /v1/ride-requests/{requestId}

// Driver: update location (high-frequency, WebSocket preferred)
WebSocket: driver sends every 5s:
  { type: "LOCATION", lat, lng, heading, speed, sequence }

// Driver: receive trip offer
WebSocket: server sends:
  { type: "TRIP_OFFER", offerId, pickupLocation, dropoffLocation,
    estimatedDistance, estimatedFare, expiresAt }

// Driver: accept or decline offer
POST /v1/trip-offers/{offerId}/accept
POST /v1/trip-offers/{offerId}/decline

// Driver: update trip state
POST /v1/trips/{tripId}/arrived-at-pickup
POST /v1/trips/{tripId}/start
POST /v1/trips/{tripId}/complete

// Rider: get trip status (polling or WebSocket)
GET /v1/trips/{tripId}
  Response 200: { tripId, status, driverLocation, driverEta, ... }
```

### Domain model

```
RideRequest {
  requestId:       UUID PK
  riderId:         UUID NOT NULL
  pickupLocation:  POINT NOT NULL
  dropoffLocation: POINT NOT NULL
  rideType:        ENUM(STANDARD, PREMIUM, POOL)
  status:          ENUM(SEARCHING, OFFERED, MATCHED, CANCELED, EXPIRED)
  estimatedFare:   BIGINT
  createdAt:       TIMESTAMPTZ NOT NULL
  expiresAt:       TIMESTAMPTZ NOT NULL   -- auto-cancel after 5 minutes
}

TripOffer {
  offerId:    UUID PK
  requestId:  UUID FK → RideRequest
  driverId:   UUID NOT NULL
  expiresAt:  TIMESTAMPTZ NOT NULL   -- driver has 15s to accept
  status:     ENUM(PENDING, ACCEPTED, DECLINED, EXPIRED)
}

Trip {
  tripId:          UUID PK
  requestId:       UUID NOT NULL UNIQUE
  riderId:         UUID NOT NULL
  driverId:        UUID NOT NULL
  status:          ENUM(ACCEPTED, DRIVER_EN_ROUTE, ARRIVED, IN_PROGRESS, COMPLETED, CANCELED)
  pickupLocation:  POINT NOT NULL
  dropoffLocation: POINT NOT NULL
  acceptedAt:      TIMESTAMPTZ
  pickupAt:        TIMESTAMPTZ
  completedAt:     TIMESTAMPTZ
  fareAmount:      BIGINT
  distanceMeters:  INT
}

DriverState {                           -- source of truth for driver availability
  driverId:       UUID PK
  status:         ENUM(OFFLINE, AVAILABLE, OFFERED, ON_TRIP)
  currentTripId:  UUID NULL
  lastLocation:   POINT
  lastUpdatedAt:  TIMESTAMPTZ
  version:        INT NOT NULL DEFAULT 0   -- optimistic lock for assignment
}

// Ephemeral (Redis only):
DriverLocation {
  driverId:  UUID
  lat:       FLOAT
  lng:       FLOAT
  heading:   INT
  speed:     FLOAT
  updatedAt: TIMESTAMP
  TTL:       30 seconds
}
```

State transitions:
- `DriverState.status`: `OFFLINE → AVAILABLE → OFFERED → ON_TRIP → AVAILABLE`
- `Trip.status`: `ACCEPTED → DRIVER_EN_ROUTE → ARRIVED → IN_PROGRESS → COMPLETED | CANCELED`

**Hard invariant:** `DriverState.status = ON_TRIP` implies exactly one active `Trip` with `driverId`. A driver cannot be `AVAILABLE` while having an active trip.

---

## 5. High-level design

```
                    ┌──────────────────────────────────────────────────────┐
                    │  Location Ingest Service                              │
Driver App ─WS─────▶│  - Receives 200k location updates/s                 │
                    │  - Validates sequence (drop stale updates)           │
                    │  - Writes to Redis Geo index (GEOADD)                │
                    │  - Publishes to Kafka: driver-locations               │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Redis Geo Index (sharded by city/region)            │
                    │  - GEOADD drivers:{cityId} lng lat driverId          │
                    │  - GEORADIUS for nearby driver lookup                │
                    │  - TTL 30s per driver entry                          │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Dispatch Service                                     │
Rider App ─────────▶│  1. Receive ride request                            │
                    │  2. Query Redis Geo: drivers within 5km radius       │
                    │  3. Filter: status=AVAILABLE, correct vehicle type   │
                    │  4. Rank candidates (distance + rating + acceptance) │
                    │  5. Send offer to top candidate                      │
                    │  6. Wait 15s for acceptance; re-dispatch on decline  │
                    └──────────────────────┬───────────────────────────────┘
                                           │
                    ┌──────────────────────▼───────────────────────────────┐
                    │  Assignment Service                                   │
                    │  - Atomic driver assignment (optimistic lock)        │
                    │  - Creates Trip record                               │
                    │  - Updates DriverState: AVAILABLE → ON_TRIP          │
                    └──────────────────────────────────────────────────────┘

                    ┌──────────────────────────────────────────────────────┐
                    │  Realtime Gateway (WebSocket)                        │
                    │  - Pushes trip state updates to rider                │
                    │  - Pushes driver location to rider during trip       │
                    │  - Pushes offers to driver                           │
                    └──────────────────────────────────────────────────────┘
```

---

## 6. Critical flows and failure behavior

### Location ingest (200k writes/s)

The challenge: 200k writes/s is too high for Postgres. Redis Geo handles this natively.

```
// Driver WebSocket sends location every 5s
handleLocationUpdate(driverId, lat, lng, heading, speed, sequence):

1. Validate sequence: if sequence <= lastKnownSequence[driverId]: drop (stale)
2. Update Redis:
   GEOADD drivers:{cityId} lng lat driverId
   HSET driver:{driverId} lat lng heading speed updatedAt sequence
   EXPIRE driver:{driverId} 30  -- driver disappears if no update for 30s
3. Publish to Kafka: driver-locations (for analytics, ETA updates)
4. If driver is ON_TRIP: push location to rider via Realtime Gateway
```

**Why Redis Geo?** Redis GEOADD is O(log N) and handles 200k writes/s on a single node. GEORADIUS queries return nearby drivers in O(N+log M) where N is results and M is total entries. Sharding by city keeps each shard manageable.

### Dispatch flow (the matching algorithm)

```
dispatch(rideRequest):

attempt = 0
while attempt < 5 and rideRequest.status != MATCHED:
  attempt++

  // Step 1: Find candidates
  candidates = redis.GEORADIUS(
    key="drivers:{cityId}",
    lng=request.pickupLng, lat=request.pickupLat,
    radius=5km,
    count=20,
    sort=ASC  // nearest first
  )

  // Step 2: Filter and rank
  availableDrivers = []
  for driverId in candidates:
    state = db.getDriverState(driverId)
    if state.status == AVAILABLE and state.vehicleType matches request.rideType:
      availableDrivers.add({driverId, distance, rating, acceptanceRate})

  if availableDrivers.empty:
    if attempt < 5: expand radius to 10km, wait 2s, retry
    else: return NO_DRIVERS_AVAILABLE

  // Step 3: Rank (weighted score)
  // score = 0.5 * (1/distance) + 0.3 * rating + 0.2 * acceptanceRate
  ranked = sort(availableDrivers, by=score, desc)

  // Step 4: Offer to top candidate
  topDriver = ranked[0]
  offer = createOffer(rideRequest, topDriver, expiresAt=now+15s)
  sendOfferToDriver(topDriver.driverId, offer)  // via Realtime Gateway

  // Step 5: Wait for acceptance (15s timeout)
  result = waitForAcceptance(offer.offerId, timeout=15s)

  if result == ACCEPTED:
    assignDriver(rideRequest, topDriver)  // atomic assignment
    return MATCHED
  else:  // DECLINED or TIMEOUT
    markOfferExpired(offer)
    continue  // try next candidate
```

### Atomic driver assignment (preventing double-assignment)

This is the hardest correctness problem: two dispatch workers might try to assign the same driver simultaneously.

```
assignDriver(rideRequest, driverId):

BEGIN TRANSACTION
  // Optimistic lock: only assign if driver is still AVAILABLE
  updated = UPDATE driver_states
    SET status = 'ON_TRIP', current_trip_id = ?, version = version + 1
    WHERE driver_id = ? AND status = 'AVAILABLE' AND version = ?

  if updated == 0:
    ROLLBACK
    return ASSIGNMENT_FAILED  // driver was taken by another request

  INSERT INTO trips (tripId, requestId, riderId, driverId, status='ACCEPTED', ...)
  UPDATE ride_requests SET status = 'MATCHED'
  INSERT INTO outbox (TripCreated event)
COMMIT

return SUCCESS
```

If assignment fails (driver taken), dispatch service tries the next ranked candidate.

### Driver goes offline mid-trip

```
// Location ingest: driver TTL expires (no update for 30s)
// Detected by: Kafka consumer watching driver-locations topic
// If driver has active trip:
  1. Mark driver as UNREACHABLE
  2. Wait 60s for reconnection
  3. If reconnected: resume normally
  4. If not: escalate to support queue; notify rider; attempt re-dispatch
```

### Offer expiry

```
// TripOffer has expiresAt = now + 15s
// Offer expiry worker (runs every 5s):
  expiredOffers = SELECT * FROM trip_offers WHERE status='PENDING' AND expires_at < now()
  for each offer:
    UPDATE trip_offers SET status = 'EXPIRED'
    // Dispatch service is notified via Kafka event → tries next candidate
```

---

## 7. Low-level design

### Geo index design

```
// Redis Geo structure per city:
// Key: drivers:{cityId}
// Members: driverId
// Score: geohash (managed by Redis internally)

// Lookup nearby drivers:
GEORADIUS drivers:NYC -73.9857 40.7484 5 km ASC COUNT 20

// Driver metadata (separate hash):
HSET driver:{driverId} status available vehicleType STANDARD rating 4.8 acceptanceRate 0.92
EXPIRE driver:{driverId} 30

// Why separate hash from geo index?
// GEORADIUS only returns driverId; metadata fetched separately
// Allows updating metadata without touching geo index
```

### Schema

```sql
CREATE TABLE driver_states (
  driver_id       UUID        PRIMARY KEY,
  status          VARCHAR(20) NOT NULL DEFAULT 'OFFLINE',
  current_trip_id UUID,
  last_lat        DECIMAL(9,6),
  last_lng        DECIMAL(9,6),
  last_updated_at TIMESTAMPTZ,
  vehicle_type    VARCHAR(20),
  rating          DECIMAL(3,2),
  acceptance_rate DECIMAL(4,3),
  version         INT         NOT NULL DEFAULT 0
);

CREATE TABLE trips (
  trip_id          UUID        PRIMARY KEY,
  request_id       UUID        NOT NULL UNIQUE,
  rider_id         UUID        NOT NULL,
  driver_id        UUID        NOT NULL,
  status           VARCHAR(30) NOT NULL,
  pickup_lat       DECIMAL(9,6) NOT NULL,
  pickup_lng       DECIMAL(9,6) NOT NULL,
  dropoff_lat      DECIMAL(9,6) NOT NULL,
  dropoff_lng      DECIMAL(9,6) NOT NULL,
  accepted_at      TIMESTAMPTZ,
  pickup_at        TIMESTAMPTZ,
  completed_at     TIMESTAMPTZ,
  fare_amount      BIGINT,
  distance_meters  INT
);

CREATE INDEX idx_trips_driver ON trips(driver_id, status)
  WHERE status NOT IN ('COMPLETED', 'CANCELED');
CREATE INDEX idx_trips_rider ON trips(rider_id, accepted_at DESC);

-- Location history (for analytics, not for dispatch)
CREATE TABLE location_history (
  driver_id  UUID        NOT NULL,
  recorded_at TIMESTAMPTZ NOT NULL,
  lat        DECIMAL(9,6) NOT NULL,
  lng        DECIMAL(9,6) NOT NULL,
  PRIMARY KEY (driver_id, recorded_at)
) PARTITION BY RANGE (recorded_at);
-- Retain 7 days; archive to object storage
```

---

## 8. Tradeoffs and evolution

| Decision | Chosen approach | Alternative | Why this tradeoff |
|---|---|---|---|
| Location store | Redis Geo (ephemeral) | PostGIS (durable) | 200k writes/s is too high for Postgres; location is ephemeral (30s TTL) |
| Matching algorithm | Greedy nearest + rank score | Global optimal assignment (Hungarian algorithm) | Global optimal is O(n³); greedy is O(n log n) and good enough at city scale |
| Driver assignment | Optimistic lock on DriverState | Distributed lock (Redis SETNX) | DB optimistic lock is durable; Redis lock can be lost on Redis restart |
| Offer model | Sequential (one driver at a time) | Broadcast (offer to top 3 simultaneously) | Sequential is simpler; broadcast improves match speed but requires cancellation logic |
| Location history | Kafka → separate analytics DB | Store in trip DB | Location history is high-volume and analytics-only; separate store prevents trip DB bloat |
| Geo sharding | By city/region | By geohash prefix | City is a natural operational boundary; geohash sharding is more complex to manage |

### Scaling triggers

- **Location ingest**: shard Redis Geo by city. Each city shard handles ~2k drivers. Add shards when any city exceeds 50k drivers.
- **Dispatch service**: stateless; scale horizontally. Each instance handles a subset of active ride requests.
- **Trip DB**: partition by `accepted_at` month. Archive completed trips older than 90 days to data warehouse.
- **Realtime Gateway**: scale WebSocket gateways independently. Use Redis Pub/Sub for cross-gateway message routing.
- **Multi-city**: each city is an independent deployment unit. Global services (auth, payments) are shared.

### Observability

- Match latency p50/p95/p99 (time from ride request to driver acceptance).
- Location freshness (% of drivers with location < 10s old — alert if < 95%).
- Offer acceptance rate (% of offers accepted — alert if < 60%, indicates driver shortage).
- Dispatch attempt count per request (alert if avg > 2, indicates supply shortage).
- Double-assignment count (should always be 0 — alert immediately if > 0).
- Driver online count per city (for supply monitoring).
- Trip completion rate (% of accepted trips that complete — alert if < 95%).
- Location ingest lag (Kafka consumer group lag — alert if > 5s).
