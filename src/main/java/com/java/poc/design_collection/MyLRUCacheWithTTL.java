package com.java.poc.design_collection;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyLRUCacheWithTTL extends LinkedHashMap<Integer, Long> {
    private final int capacity;
    private final long ttl; // Time-to-live in milliseconds

    public MyLRUCacheWithTTL(int capacity, long ttl) {
        super(capacity, 0.75f, true); // accessOrder = true for LRU order
        this.capacity = capacity;
        this.ttl = ttl;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Long> eldest) {
        // Check if the eldest entry is expired
        if (size() > capacity) {
            return true; // Remove if capacity exceeded
        }
        // Check if the entry has expired
        return System.currentTimeMillis() - eldest.getValue() > ttl;
    }

    public Integer get(int key) {
        Long timestamp = super.get(key);
        if (timestamp == null || isExpired(timestamp)) {
            remove(key); // Remove expired entry
            return null; // return null if key not found or expired
        }
        return key; // Return the key as a proxy for its existence
    }

    public void put(int key) {
        super.put(key, System.currentTimeMillis()); // Store current time as the value
    }

    private boolean isExpired(Long timestamp) {
        return System.currentTimeMillis() - timestamp > ttl;
    }

    public static void main(String[] args) throws InterruptedException {
        // Create an LRU cache with a capacity of 2 and a TTL of 5 seconds
        MyLRUCacheWithTTL lruCache = new MyLRUCacheWithTTL(2, TimeUnit.SECONDS.toMillis(5));

        lruCache.put(1); // cache is {1}
        System.out.println(lruCache.get(1)); // return 1

        Thread.sleep(6000); // wait for 6 seconds to expire

        System.out.println(lruCache.get(1)); // return null (expired)

        lruCache.put(2); // cache is {2}
        lruCache.put(3); // cache is {2, 3}
        System.out.println(lruCache.get(2)); // return 2

        lruCache.put(4); // LRU key was 3, remove key 3, cache is {2, 4}
        System.out.println(lruCache.get(3)); // return null (not found)
    }
}
