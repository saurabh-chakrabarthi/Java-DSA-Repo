package com.java.poc.design_collection;

import java.util.LinkedList;

class MyHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16; // Default initial capacity
    private LinkedList<Entry<K, V>>[] buckets; // Array of buckets
    private int size; // Number of key-value pairs

    public MyHashMap() {
        buckets = new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // Hash function to get bucket index
    private int getBucketIndex(K key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % buckets.length;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new LinkedList<>();
        }

        for (Entry<K, V> entry : buckets[bucketIndex]) {
            if (entry.key.equals(key)) {
                entry.value = value; // Update existing key
                return;
            }
        }

        // Add new entry
        buckets[bucketIndex].add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        if (buckets[bucketIndex] != null) {
            for (Entry<K, V> entry : buckets[bucketIndex]) {
                if (entry.key.equals(key)) {
                    return entry.value; // Return the value if key is found
                }
            }
        }
        return null; // Return null if key is not found
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        if (buckets[bucketIndex] != null) {
            Entry<K, V> toRemove = null;
            for (Entry<K, V> entry : buckets[bucketIndex]) {
                if (entry.key.equals(key)) {
                    toRemove = entry;
                    break;
                }
            }
            if (toRemove != null) {
                buckets[bucketIndex].remove(toRemove); // Remove the entry
                size--;
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        System.out.println(map.get("one"));   // Output: 1
        System.out.println(map.get("two"));   // Output: 2
        System.out.println(map.get("four"));  // Output: null

        map.remove("two");
        System.out.println(map.get("two"));   // Output: null
        System.out.println("Size: " + map.size()); // Output: Size: 2
    }
}

