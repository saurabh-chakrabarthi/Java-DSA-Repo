package com.java.poc.dsa.stack;

public class StackImpl {

  private int[] array;
  private int top;
  private int capacity;

  StackImpl(int capacity) {
    this.array = new int[capacity];
    this.top = -1;
    this.capacity = capacity;
  }

  public void push(int item) {
    if (isFull()) {
      throw new RuntimeException("StackImpl is Full!");
    }
    array[++top] = item;
  }

  public int pop() {
    if (isEmpty()) {
      throw new RuntimeException("StackImpl is Empty!");
    }
    return array[top--];
  }

  public int peek() {
    if (isEmpty()) {
      throw new RuntimeException("StackImpl is Empty!");
    }
    return array[top];
  }

  private boolean isEmpty() {
    return top == -1;
  }

  private boolean isFull() {
    return top == capacity - 1;
  }

}
