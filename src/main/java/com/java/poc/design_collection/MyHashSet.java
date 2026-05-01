package com.java.poc.design_collection;

import java.util.LinkedList;

class MyHashSet<E> {
    private static final int INITIAL_CAPACITY = 16; // Default initial capacity
    private LinkedList<E>[] buckets; // Array of buckets
    private int size; // Number of elements

    public MyHashSet() {
        buckets = new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    // Hash function to get bucket index
    private int getBucketIndex(E element) {
        return element == null ? 0 : Math.abs(element.hashCode()) % buckets.length;
    }

    public boolean add(E element) {
        int bucketIndex = getBucketIndex(element);
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new LinkedList<>();
        }

        // Check for duplicates
        for (E e : buckets[bucketIndex]) {
            if (e.equals(element)) {
                return false; // Element already exists
            }
        }

        // Add new element
        buckets[bucketIndex].add(element);
        size++;
        return true; // Element added
    }

    public boolean contains(E element) {
        int bucketIndex = getBucketIndex(element);
        if (buckets[bucketIndex] != null) {
            for (E e : buckets[bucketIndex]) {
                if (e.equals(element)) {
                    return true; // Element found
                }
            }
        }
        return false; // Element not found
    }

    public boolean remove(E element) {
        int bucketIndex = getBucketIndex(element);
        if (buckets[bucketIndex] != null) {
            for (E e : buckets[bucketIndex]) {
                if (e.equals(element)) {
                    buckets[bucketIndex].remove(e); // Remove the element
                    size--;
                    return true; // Element removed
                }
            }
        }
        return false; // Element not found
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        MyHashSet<String> set = new MyHashSet<>();
        set.add("one");
        set.add("two");
        set.add("three");

        System.out.println(set.contains("two")); // Output: true
        System.out.println(set.contains("four")); // Output: false

        set.remove("two");
        System.out.println(set.contains("two")); // Output: false
        System.out.println("Size: " + set.size()); // Output: Size: 2
    }
}

