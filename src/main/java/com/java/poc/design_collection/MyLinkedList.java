package com.java.poc.design_collection;

class MyLinkedList {
    // Node class representing each element in the linked list
    private static class Node {
        int data; // Data of the node
        Node next; // Reference to the next node

        Node(int data) {
            this.data = data; // Initialize node with data
            this.next = null; // Initially, the next node is null
        }
    }

    private Node head; // Head of the linked list

    // Constructor to initialize the linked list
    public MyLinkedList() {
        this.head = null; // Initially, the list is empty
    }

    // Insert a new node at the end of the linked list
    public void insert(int data) {
        Node newNode = new Node(data); // Create a new node
        if (head == null) {
            head = newNode; // If the list is empty, set head to the new node
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next; // Traverse to the end of the list
        }
        current.next = newNode; // Link the new node at the end
    }

    // Delete a node by value
    public void delete(int value) {
        if (head == null) return; // If the list is empty, do nothing
        // Handle deletion of head node
        if (head.data == value) {
            head = head.next; // Move head to the next node
            return;
        }
        Node current = head;
        while (current.next != null) {
            if (current.next.data == value) {
                current.next = current.next.next; // Bypass the node to delete it
                return;
            }
            current = current.next; // Move to the next node
        }
    }

    // Traverse and print the linked list
    public void traverse() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> "); // Print the current node's data
            current = current.next; // Move to the next node
        }
        System.out.println("null"); // Indicate the end of the list
    }

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList(); // Create a new linked list
        linkedList.insert(1); // Insert data
        linkedList.insert(2);
        linkedList.insert(3);
        linkedList.insert(4);
        linkedList.traverse(); // Output: 1 -> 2 -> 3 -> 4 -> null

        linkedList.delete(2); // Delete a node
        linkedList.traverse(); // Output: 1 -> 3 -> 4 -> null

        linkedList.delete(1); // Delete head node
        linkedList.traverse(); // Output: 3 -> 4 -> null
    }
}
