package com.example.ratelimiter;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterTest {

    @Test
    void shouldAllowUpToCapacity() {
        RateLimiter limiter = new RateLimiter(3, 1);

        assertTrue(limiter.allowRequest("user"));
        assertTrue(limiter.allowRequest("user"));
        assertTrue(limiter.allowRequest("user"));
        assertFalse(limiter.allowRequest("user"));
    }

    @Test
    void shouldRefillTokensOverTime() throws InterruptedException {
        RateLimiter limiter = new RateLimiter(1, 1);

        assertTrue(limiter.allowRequest("user"));
        assertFalse(limiter.allowRequest("user"));

        Thread.sleep(1100);

        assertTrue(limiter.allowRequest("user"));
    }

    @Test
    void shouldBeThreadSafe() throws InterruptedException {
        RateLimiter limiter = new RateLimiter(5, 1);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        AtomicInteger allowedCount = new AtomicInteger();

        for (int i = 0; i < 20; i++) {
            executor.submit(() -> {
                if (limiter.allowRequest("user")) {
                    allowedCount.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(5, allowedCount.get());
    }
}