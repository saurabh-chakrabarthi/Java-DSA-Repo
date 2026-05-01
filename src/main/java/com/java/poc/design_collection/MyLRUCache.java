package com.java.poc.design_collection;

import java.util.HashMap;

class MyLRUCache {
    // Node class to represent each entry in the doubly linked list
    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity; // Maximum number of items in the cache
    private final HashMap<Integer, Node> cache; // Maps keys to their corresponding nodes
    private final Node head; // Dummy head of the doubly linked list
    private final Node tail; // Dummy tail of the doubly linked list

    public MyLRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(0, 0); // Initialize head
        this.tail = new Node(0, 0); // Initialize tail
        head.next = tail; // Connect head to tail
        tail.prev = head; // Connect tail to head
    }

    // Retrieve a value from the cache
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1; // Return -1 if the key is not found
        }
        Node node = cache.get(key);
        moveToHead(node); // Move the accessed node to the head of the list
        return node.value; // Return the value
    }

    // Insert or update a value in the cache
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value; // Update value
            moveToHead(node); // Move the updated node to the head of the list
        } else {
            Node newNode = new Node(key, value);
            cache.put(key, newNode); // Add new node to the cache
            addToHead(newNode); // Add new node to the head of the list

            if (cache.size() > capacity) {
                Node tailPrev = removeTail(); // Remove the least recently used node
                cache.remove(tailPrev.key); // Remove from the cache as well
            }
        }
    }

    // Helper method to add a node to the head of the doubly linked list
    private void addToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    // Helper method to remove a node from the doubly linked list
    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    // Move a node to the head (most recently used position)
    private void moveToHead(Node node) {
        removeNode(node); // Remove from its current position
        addToHead(node); // Add to the head
    }

    // Remove the tail node (least recently used)
    private Node removeTail() {
        Node tailNode = tail.prev;
        removeNode(tailNode); // Remove from the linked list
        return tailNode; // Return the removed node
    }

    public static void main(String[] args) {
        MyLRUCache lruCache = new MyLRUCache(2); // Create an LRU Cache with a capacity of 2

        lruCache.put(1, 1); // Cache is {1=1}
        lruCache.put(2, 2); // Cache is {1=1, 2=2}
        System.out.println(lruCache.get(1)); // Return 1 and move key 1 to head, cache is {2=2, 1=1}
        lruCache.put(3, 3); // Evicts key 2, cache is {1=1, 3=3}
        System.out.println(lruCache.get(2)); // Return -1 (not found)
        lruCache.put(4, 4); // Evicts key 1, cache is {3=3, 4=4}
        System.out.println(lruCache.get(1)); // Return -1 (not found)
        System.out.println(lruCache.get(3)); // Return 3
        System.out.println(lruCache.get(4)); // Return 4
    }
}

