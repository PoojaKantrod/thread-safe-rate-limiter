package com.example.ratelimiter.demo;

import com.example.ratelimiter.core.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RateLimiterDemo {

    public static void main(String[] args) throws InterruptedException {

        RateLimiter fixedWindow = new FixedWindowRateLimiter(5, 1000);
        RateLimiter slidingWindow = new SlidingWindowRateLimiter(5, 1000);
        RateLimiter tokenBucket = new TokenBucketRateLimiter(5, 5);

        System.out.println("=== Fixed Window Demo ===");
        runDemo(fixedWindow);

        System.out.println("\n=== Sliding Window Demo ===");
        runDemo(slidingWindow);

        System.out.println("\n=== Token Bucket Demo ===");
        runDemo(tokenBucket);
    }

    private static void runDemo(RateLimiter limiter) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++) {
            int requestId = i;
            executor.submit(() -> {
                boolean allowed = limiter.allowRequest(); // or allowRequest("user1")
                System.out.println(
                        Thread.currentThread().getName()
                                + " | Request " + requestId
                                + " | allowed=" + allowed
                );

                try {
                    Thread.sleep(100); // simulate request interval
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS); // wait for all tasks to finish
    }
}