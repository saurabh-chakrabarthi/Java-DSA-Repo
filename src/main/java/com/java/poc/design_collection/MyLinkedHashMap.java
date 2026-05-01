package com.java.poc.design_collection;

import java.util.HashMap;

public class MyLinkedHashMap<K, V> {

    // Inner class representing a doubly linked list node
    private class Node {
        K key;
        V value;
        Node prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // HashMap to store key-value pairs and their respective nodes in the linked list
    private HashMap<K, Node> map;
    // Pointers to the head and tail of the doubly linked list
    private Node head, tail;
    // Capacity of the LinkedHashMap (if we want to limit the size)
    private int capacity;
    // Current size of the LinkedHashMap
    private int size;

    // Constructor to initialize with a capacity
    public MyLinkedHashMap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.map = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    // Put method to add or update a key-value pair
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            moveToTail(node);
        } else {
            Node newNode = new Node(key, value);
            if (size == capacity) {
                removeHead();
            }
            addToTail(newNode);
            map.put(key, newNode);
            size++;
        }
    }

    // Get method to retrieve a value by key
    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node node = map.get(key);
        moveToTail(node);
        return node.value;
    }

    // Method to remove a key-value pair
    public void remove(K key) {
        if (!map.containsKey(key)) {
            return;
        }
        Node node = map.get(key);
        removeNode(node);
        map.remove(key);
        size--;
    }

    // Method to move a node to the tail (most recent)
    private void moveToTail(Node node) {
        if (node == tail) {
            return;
        }
        removeNode(node);
        addToTail(node);
    }

    // Helper method to remove a node from the linked list
    private void removeNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    // Helper method to add a node to the tail
    private void addToTail(Node node) {
        if (tail != null) {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
        if (head == null) {
            head = node;
        }
    }

    // Method to remove the head (least recent) node
    private void removeHead() {
        if (head == null) {
            return;
        }
        map.remove(head.key);
        if (head.next != null) {
            head.next.prev = null;
        }
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
    }

    // Method to display the LinkedHashMap (for testing purposes)
    public void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.key + "=" + current.value + " ");
            current = current.next;
        }
        System.out.println();
    }

    // Main method to test the LinkedHashMap implementation
    public static void main(String[] args) {
        MyLinkedHashMap<Integer, String> linkedHashMap = new MyLinkedHashMap<>(3);

        linkedHashMap.put(1, "One");
        linkedHashMap.put(2, "Two");
        linkedHashMap.put(3, "Three");
        linkedHashMap.display();  // Output: 1=One 2=Two 3=Three

        linkedHashMap.get(1);  // Access key 1 to move it to the end
        linkedHashMap.display();  // Output: 2=Two 3=Three 1=One

        linkedHashMap.put(4, "Four");  // This will remove the least recently used (key 2)
        linkedHashMap.display();  // Output: 3=Three 1=One 4=Four
    }
}
