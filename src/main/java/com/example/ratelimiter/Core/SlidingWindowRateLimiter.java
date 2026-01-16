package com.example.ratelimiter.core;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final long windowSizeMillis;
    private final Deque<Long> timestamps = new ArrayDeque<>();

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
    }

    @Override
    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();

        while (!timestamps.isEmpty() && now - timestamps.peekFirst() > windowSizeMillis) {
            timestamps.pollFirst();
        }

        if (timestamps.size() < maxRequests) {
            timestamps.addLast(now);
            return true;
        }

        return false;
    }
}