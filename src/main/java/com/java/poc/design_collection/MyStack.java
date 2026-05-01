package com.java.poc.design_collection;

class MyStack<T> {
    private Node<T> top; // The top of the stack
    private int size; // Size of the stack

    // Node class to represent each element in the stack
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public MyStack() {
        this.top = null; // Initialize top as null
        this.size = 0;   // Initialize size as 0
    }

    // Push an element onto the stack
    public void push(T data) {
        Node<T> newNode = new Node<>(data); // Create a new node
        newNode.next = top; // Point new node to the current top
        top = newNode; // Update top to the new node
        size++; // Increment size
    }

    // Pop the top element from the stack
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("MyStack is empty"); // Handle underflow
        }
        T poppedData = top.data; // Get data from the top node
        top = top.next; // Update top to the next node
        size--; // Decrement size
        return poppedData; // Return the popped data
    }

    // Peek at the top element of the stack
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("MyStack is empty"); // Handle empty stack
        }
        return top.data; // Return data from the top node
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return size == 0; // Return true if size is 0
    }

    // Get the size of the stack
    public int size() {
        return size; // Return current size
    }

    public static void main(String[] args) {
        MyStack<Integer> stack = new MyStack<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println("Top element: " + stack.peek()); // Output: Top element: 30
        System.out.println("Popped element: " + stack.pop()); // Output: Popped element: 30
        System.out.println("New top element: " + stack.peek()); // Output: New top element: 20
        System.out.println("MyStack size: " + stack.size()); // Output: MyStack size: 2
        System.out.println("Is stack empty? " + stack.isEmpty()); // Output: Is stack empty? false

        stack.pop(); // Remove 20
        stack.pop(); // Remove 10

        System.out.println("Is stack empty? " + stack.isEmpty()); // Output: Is stack empty? true
    }
}
