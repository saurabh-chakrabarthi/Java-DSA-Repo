package com.java.poc.design_collection;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyConcurrentHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private final Segment<K, V>[] segments;

    // Inner class to represent each segment
    private static class Segment<K, V> {
        private final Lock lock = new ReentrantLock();
        private final Node<K, V>[] table;

        public Segment(int capacity) {
            this.table = new Node[capacity];
        }

        // Basic node structure for key-value pairs
        private static class Node<K, V> {
            final K key;
            volatile V value;
            Node<K, V> next;

            public Node(K key, V value, Node<K, V> next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }

        // Add or update a key-value pair
        public void put(K key, V value) {
            lock.lock();
            try {
                int index = key.hashCode() % table.length;
                Node<K, V> node = table[index];

                if (node == null) {
                    table[index] = new Node<>(key, value, null);
                } else {
                    Node<K, V> prev = null;
                    while (node != null) {
                        if (node.key.equals(key)) {
                            node.value = value; // Update value
                            return;
                        }
                        prev = node;
                        node = node.next;
                    }
                    prev.next = new Node<>(key, value, null); // Add new node
                }
            } finally {
                lock.unlock();
            }
        }

        // Get the value for a given key
        public V get(K key) {
            int index = key.hashCode() % table.length;
            Node<K, V> node = table[index];

            while (node != null) {
                if (node.key.equals(key)) {
                    return node.value;
                }
                node = node.next;
            }
            return null;
        }

        // Remove a key-value pair
        public void remove(K key) {
            lock.lock();
            try {
                int index = key.hashCode() % table.length;
                Node<K, V> node = table[index];
                Node<K, V> prev = null;

                while (node != null) {
                    if (node.key.equals(key)) {
                        if (prev == null) {
                            table[index] = node.next;
                        } else {
                            prev.next = node.next;
                        }
                        return;
                    }
                    prev = node;
                    node = node.next;
                }
            } finally {
                lock.unlock();
            }
        }
    }

    // Constructor for initializing the concurrent hash map with given capacity
    public MyConcurrentHashMap(int capacity) {
        segments = new Segment[DEFAULT_CAPACITY];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = new Segment<>(capacity / segments.length);
        }
    }

    // Hashing function to determine segment
    private int getSegmentIndex(Object key) {
        return key.hashCode() % segments.length;
    }

    // Put method for adding key-value pair
    public void put(K key, V value) {
        int segmentIndex = getSegmentIndex(key);
        segments[segmentIndex].put(key, value);
    }

    // Get method to retrieve a value by key
    public V get(K key) {
        int segmentIndex = getSegmentIndex(key);
        return segments[segmentIndex].get(key);
    }

    // Remove a key-value pair
    public void remove(K key) {
        int segmentIndex = getSegmentIndex(key);
        segments[segmentIndex].remove(key);
    }

    // Main method to test the implementation
    public static void main(String[] args) {
        MyConcurrentHashMap<String, Integer> map = new MyConcurrentHashMap<>(16);

        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        System.out.println("Value for 'two': " + map.get("two"));

        map.remove("two");
        System.out.println("Value for 'two' after removal: " + map.get("two"));
    }
}
