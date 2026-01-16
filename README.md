# Thread-Safe Rate Limiter (Java)

This project demonstrates **multiple rate limiting algorithms** implemented in Java with a strong focus on:

- Thread safety
- Clean design
- Real-world behavior comparison

## 🚀 Implemented Rate Limiting Algorithms

### 1. Fixed Window Rate Limiter
Limits the number of requests in a **fixed time window**.

**How it works:**
- Time is divided into fixed intervals (e.g., 1 second)
- A counter tracks requests in the current window
- Counter resets when the window expires

**Pros**
- Simple and fast
- Low memory usage
- Easy to scale with Redis

**Cons**
- Allows request bursts at window boundaries

---

### 2. Sliding Window Rate Limiter
Limits requests based on a **moving time window**.

**How it works:**
- Stores timestamps of recent requests
- Counts requests within the last N milliseconds
- Rejects requests exceeding the limit

**Pros**
- Very accurate
- Prevents burst traffic
- Fair request distribution

**Cons**
- Higher memory usage
- More CPU overhead
- Harder to scale in distributed systems

---

### 3. Token Bucket Rate Limiter
Allows requests based on available tokens that refill over time.

**How it works:**
- Tokens are added to a bucket at a fixed rate
- Each request consumes one token
- Requests are rejected when the bucket is empty

**Pros**
- Allows controlled bursts
- Smooth traffic shaping
- Industry standard (used by AWS, Stripe, etc.)

**Cons**
- Slightly more complex to implement
- Requires precise time handling

---

## 🧠 Comparison Summary

| Algorithm       | Burst Handling | Accuracy | Complexity | Distributed Friendly |
|----------------|---------------|----------|------------|----------------------|
| Fixed Window   | ❌ Poor        | ❌ Low   | ⭐ Easy     | ⭐ Yes               |
| Sliding Window | ⭐ Excellent   | ⭐ High  | ❌ High    | ❌ Hard              |
| Token Bucket   | ⭐ Excellent   | ⭐ High  | ⚖️ Medium | ⭐ Yes               |

---

## 🧪 Demo

The `RateLimiterDemo` class demonstrates all three algorithms using a multithreaded executor.

```bash
mvn clean compile
java -cp target/classes com.example.ratelimiter.demo.RateLimiterDemo

----

**Output**

=== Fixed Window Demo ===
pool-1-thread-3 | Request 2 | allowed=true
pool-1-thread-1 | Request 0 | allowed=true
pool-1-thread-8 | Request 7 | allowed=false
pool-1-thread-10 | Request 9 | allowed=false
pool-1-thread-4 | Request 3 | allowed=true
pool-1-thread-2 | Request 1 | allowed=true
pool-1-thread-7 | Request 6 | allowed=false
pool-1-thread-5 | Request 4 | allowed=true
pool-1-thread-9 | Request 8 | allowed=false

**Observation**
- Exactly 5 requests are allowed
- Remaining requests in the same window are rejected
- Hard cutoff behavior

---

=== Sliding Window Demo ===
pool-2-thread-4 | Request 3 | allowed=true
pool-2-thread-3 | Request 2 | allowed=true
pool-2-thread-5 | Request 4 | allowed=true
pool-2-thread-1 | Request 0 | allowed=true
pool-2-thread-7 | Request 6 | allowed=false
pool-2-thread-2 | Request 1 | allowed=false
pool-2-thread-6 | Request 5 | allowed=true

**Observation**
- Requests are throttled smoothly
- No sudden burst allowed at time boundaries
- More accurate rate enforcement

---


=== Token Bucket Demo ===
pool-3-thread-2 | Request 1 | allowed=true
pool-3-thread-1 | Request 0 | allowed=true
pool-3-thread-3 | Request 2 | allowed=true
pool-3-thread-4 | Request 3 | allowed=true
pool-3-thread-5 | Request 4 | allowed=true
pool-3-thread-6 | Request 5 | allowed=false
pool-3-thread-7 | Request 6 | allowed=false
pool-3-thread-8 | Request 7 | allowed=false

**Observation**
- Initial burst is allowed due to pre-filled tokens
- Requests are smoothed over time
- Mimics real-world API throttling behavior

------

**Author**
Pooja Kantrod

