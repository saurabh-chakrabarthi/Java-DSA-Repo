package com.java.poc.java_basics.oops;

public class AggregationComposition {
    public static void main(String[] args) {
        // Create instances for aggregation and composition
        Department engineeringDepartment = new Department("Engineering");
        Engine carEngine = new Engine();

        // Create a Car object that aggregates a Department and composes an Engine
        Car1 myCar = new Car1("Toyota", carEngine, engineeringDepartment);

        // Display Car information
        myCar.displayInfo(); // Output vehicle brand and department name
        myCar.start();       // Start the engine
    }
}
// Superclass representing a generic Vehicle
class Vehicle {
    private String brand;

    public Vehicle(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void displayInfo() {
        System.out.println("Vehicle Brand: " + brand);
    }
}

// Subclass that inherits from Vehicle
class Car1 extends Vehicle {
    private Engine engine; // Composition relationship
    private Department department; // Aggregation relationship

    public Car1(String brand, Engine engine, Department department) {
        super(brand);
        this.engine = engine; // Car "has a" Engine (Composition)
        this.department = department; // Car "belongs to" a Department (Aggregation)
    }

    public void start() {
        engine.start(); // Delegates start action to Engine
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Department: " + department.getName());
    }
}

// Class representing an Engine
class Engine {
    public void start() {
        System.out.println("Engine starts");
    }
}

// Class representing a Department
class Department {
    private String name;

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

