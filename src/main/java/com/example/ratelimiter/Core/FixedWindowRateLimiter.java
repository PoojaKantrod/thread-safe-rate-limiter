package com.example.ratelimiter.core;

import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;

    private long windowStart;
    private final AtomicInteger requestCount = new AtomicInteger(0);

    public FixedWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.windowStart = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();

        if (now - windowStart >= windowSizeMillis) {
            windowStart = now;
            requestCount.set(0);
        }

        return requestCount.incrementAndGet() <= maxRequests;
    }
}