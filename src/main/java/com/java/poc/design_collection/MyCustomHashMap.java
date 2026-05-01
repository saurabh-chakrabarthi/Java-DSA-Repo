package com.java.poc.design_collection;

import java.util.HashMap;
        import java.util.Objects;

class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Override equals() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    // Override hashCode() method
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return name + ", " + age;
    }
}

public class MyCustomHashMap {
    public static void main(String[] args) {
        // Create HashMap with Person as key
        HashMap<Person, String> personMap = new HashMap<>();

        // Adding key-value pairs
        Person p1 = new Person("John", 30);
        Person p2 = new Person("Alice", 25);
        Person p3 = new Person("John", 30); // Same values as p1

        personMap.put(p1, "Engineer");
        personMap.put(p2, "Doctor");

        // Accessing values using custom object as key
        System.out.println("p1's profession: " + personMap.get(p1)); // Output: Engineer
        System.out.println("p2's profession: " + personMap.get(p2)); // Output: Doctor

        // Even though p3 is a different object, equals() and hashCode() ensure correct lookup
        System.out.println("p3's profession: " + personMap.get(p3)); // Output: Engineer
    }
}
