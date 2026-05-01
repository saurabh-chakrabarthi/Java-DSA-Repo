package com.java.poc.java_basics.oops;

public class MainClass {
    public static void main(String[] args) {
        // Call the static method without creating an instance of MathUtility
        int result = Static.square(5);
        System.out.println("The square of 5 is: " + result); // Output: The square of 5 is: 25
    }
}
