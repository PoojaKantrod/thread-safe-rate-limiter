package com.example.ratelimiter;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadLocalRandom;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        // Rate limiter: capacity 5 tokens, refill 1 token/sec
        RateLimiter limiter = new RateLimiter(5, 1);

        int totalUsers = 50;         // 50 users
        int requestsPerUser = 100;   // each sends 100 requests

        // Thread pool large enough for concurrency
        ExecutorService executor = Executors.newFixedThreadPool(100);

        AtomicInteger totalAllowed = new AtomicInteger();
        AtomicInteger totalBlocked = new AtomicInteger();

        // Submit requests for all users
        for (int u = 1; u <= totalUsers; u++) {
            String userId = "user" + u;

            for (int r = 0; r < requestsPerUser; r++) {
                executor.submit(() -> {
                    boolean allowed = limiter.allowRequest(userId);

                    if (allowed) totalAllowed.incrementAndGet();
                    else totalBlocked.incrementAndGet();

                    // Optional: random delay to simulate real traffic
                    try {
                        Thread.sleep(ThreadLocalRandom.current().nextInt(10, 50));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    // Print every 1000th request to reduce console spam
                    int total = totalAllowed.get() + totalBlocked.get();
                    if (total % 1000 == 0) {
                        System.out.println("Processed requests so far: " + total);
                    }
                });
            }
        }

        // Wait for all tasks to finish
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);

        // Summary
        System.out.println("===== Stress Test Summary =====");
        System.out.println("Total users: " + totalUsers);
        System.out.println("Requests per user: " + requestsPerUser);
        System.out.println("Total requests: " + (totalUsers * requestsPerUser));
        System.out.println("Allowed: " + totalAllowed.get());
        System.out.println("Blocked: " + totalBlocked.get());
        System.out.println("================================");
    }
}