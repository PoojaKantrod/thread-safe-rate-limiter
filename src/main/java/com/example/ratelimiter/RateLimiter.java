package com.example.ratelimiter;

import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private final ConcurrentHashMap<String, TokenBucket> buckets =
            new ConcurrentHashMap<>();

    private final int capacity;
    private final int refillRatePerSecond;

    public RateLimiter(int capacity, int refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
    }

    public boolean allowRequest(String key) {
        TokenBucket bucket = buckets.computeIfAbsent(
                key,
                k -> new TokenBucket(capacity, refillRatePerSecond)
        );
        return bucket.tryConsume();
    }
}