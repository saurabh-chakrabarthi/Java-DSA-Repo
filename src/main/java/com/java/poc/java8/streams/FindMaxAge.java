package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

import java.util.OptionalInt;

public class FindMaxAge {
    public static void main(String[] args) {
        OptionalInt max = new Employee().getEmployeeList().stream().mapToInt(student -> student.getAge()).max();
        System.out.println("max age is : "+max.getAsInt());
    }
}
