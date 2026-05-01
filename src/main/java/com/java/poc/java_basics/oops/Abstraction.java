package com.java.poc.java_basics.oops;

// Abstract class representing a Shape
abstract class Abstraction {
    // Abstract method to be implemented by subclasses
    abstract void draw();
}

// Subclass representing a Circle
class Circle extends Abstraction {
    @Override
    void draw() {
        System.out.println("Drawing a Circle");
    }
}

// Subclass representing a Rectangle
class Rectangle extends Abstraction {
    @Override
    void draw() {
        System.out.println("Drawing a Rectangle");
    }
}

