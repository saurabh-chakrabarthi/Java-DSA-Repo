package com.java.poc.design_collection;

import java.util.Arrays;

class MyArrayList {
    private Object[] elements; // Array to store the elements
    private int size; // Current number of elements in the MyArrayList
    private static final int INITIAL_CAPACITY = 10; // Default initial capacity

    // Constructor to initialize the MyArrayList
    public MyArrayList() {
        elements = new Object[INITIAL_CAPACITY]; // Create an array with initial capacity
        size = 0; // Initialize size
    }

    // Add an element to the MyArrayList
    public void add(Object element) {
        if (size == elements.length) {
            resize(); // Resize the array if it is full
        }
        elements[size] = element; // Add the element to the array
        size++; // Increment the size
    }

    // Remove an element at the specified index
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1]; // Shift elements to the left
        }
        elements[size - 1] = null; // Clear the last element
        size--; // Decrement the size
    }

    // Get an element at the specified index
    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return elements[index]; // Return the element
    }

    // Get the current size of the MyArrayList
    public int size() {
        return size; // Return the size
    }

    // Resize the underlying array when it's full
    private void resize() {
        int newCapacity = elements.length * 2; // Double the capacity
        elements = Arrays.copyOf(elements, newCapacity); // Create a new array with the new capacity
    }

    public static void main(String[] args) {
        MyArrayList myArrayList = new MyArrayList(); // Create an instance of MyArrayList
        myArrayList.add("Hello");
        myArrayList.add("World");
        myArrayList.add(123);
        myArrayList.add(45.67);

        System.out.println("Size: " + myArrayList.size()); // Output: Size: 4
        System.out.println("Element at index 1: " + myArrayList.get(1)); // Output: Element at index 1: World

        myArrayList.remove(1); // Remove element at index 1
        System.out.println("Size after removal: " + myArrayList.size()); // Output: Size after removal: 3

        System.out.println("Element at index 1 after removal: " + myArrayList.get(1)); // Output: Element at index 1 after removal: 123
    }
}

