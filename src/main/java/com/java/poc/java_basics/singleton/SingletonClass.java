package com.java.poc.java_basics.singleton;

class Singleton {
    // Private static variable to hold the single instance
    private static Singleton instance;

    // Private constructor to prevent instantiation from outside the class
    private Singleton() {
        System.out.println("Singleton instance created!");
    }

    // Public static method to provide access to the instance
    public static Singleton getInstance() {
        if (instance == null) {
            // Create the instance if it doesn't exist
            instance = new Singleton();
        }
        return instance;
    }

    // Example method in Singleton class
    public void showMessage() {
        System.out.println("Hello from Singleton class!");
    }
}

public class SingletonClass {
    public static void main(String[] args) {
        // Get the single instance of the Singleton class
        Singleton singleton1 = Singleton.getInstance();
        singleton1.showMessage();

        // Try to get another instance of the Singleton class
        Singleton singleton2 = Singleton.getInstance();
        singleton2.showMessage();

        // Verify that both references point to the same instance
        System.out.println("Are both instances same? " + (singleton1 == singleton2));
    }
}
