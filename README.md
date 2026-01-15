# Thread-Safe Rate Limiter

## Overview
This project implements a **thread-safe Token Bucket rate limiter** in Java using **Maven**.  
It demonstrates:

- **Concurrency control** with multiple threads
- **Token bucket algorithm** for controlling request rate
- **Stress testing** for multiple users
- Writing **JUnit 5 tests** for reliability

This project is a **production-grade implementation**, suitable for microservices or API gateways to **prevent request overload**.

---

## Features
- **Thread-safe**: Handles concurrent requests from multiple threads/users safely
- **Configurable capacity**: Set max tokens per user
- **Configurable refill rate**: Tokens refill automatically over time
- **Stress Test Demo**: Simulates hundreds of requests per user across multiple users
- **JUnit 5 Tests**: Validates correctness and thread safety

---

## Project Structure

```
rate-limiter/
 ├── pom.xml
 ├── src/
 │    ├── main/java/com/example/ratelimiter/
 │    │      ├── TokenBucket.java
 │    │      ├── RateLimiter.java
 │    │      └── Demo.java
 │    └── test/java/com/example/ratelimiter/
 │           └── RateLimiterTest.java
 └── README.md
```

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Git (optional, for version control)

### Compile Project
```bash
mvn compile
```

### Run Demo
```bash
mvn exec:java -Dexec.mainClass="com.example.ratelimiter.Demo"
```

Expected output:
```
pool-1-thread-1 | user1 -> true
pool-1-thread-2 | user2 -> true
...
All requests processed!
```

### Run Tests
```bash
mvn test
```

Expected output:
```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## How It Works

1. **Token Bucket Algorithm**
   - Each user has a bucket with **max tokens (capacity)**.
   - Every request consumes 1 token.
   - Tokens **refill at a fixed rate** over time.
   - Requests exceeding token count are **blocked**.

2. **Concurrency Handling**
   - Uses **synchronized blocks / ReentrantLocks** for thread safety.
   - Each user has an independent token bucket.

3. **Stress Test**
   - Simulates **50+ users** sending **hundreds of requests each**.
   - Validates thread safety and correct rate limiting behavior.

---

## Sample Output (Stress Test)
```
===== Stress Test Summary =====
Total users: 50
Requests per user: 100
Total requests: 5000
Allowed: 250
Blocked: 4750
================================
```

---

## Technologies Used
- **Java 17**
- **Maven 3**
- **JUnit 5**
- **ExecutorService / ThreadPool** for concurrency

---



## Author
**Pooja Kantrod** – Senior Cognitive Engineer  
Portfolio-ready, demonstrates **Java concurrency, system design, and stress testing skills**.