package com.java.poc.java_basics.oops;

// Main class to demonstrate abstraction
public class Main {
    public static void main(String[] args) {
        Abstraction circle = new Circle();     // Shape reference to Circle object
        Abstraction rectangle = new Rectangle(); // Shape reference to Rectangle object

        circle.draw();     // Output: Drawing a Circle
        rectangle.draw();  // Output: Drawing a Rectangle
    }
}
