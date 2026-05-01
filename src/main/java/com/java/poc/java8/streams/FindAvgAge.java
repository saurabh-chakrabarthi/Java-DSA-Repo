package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

import java.util.Map;
import java.util.stream.Collectors;

public class FindAvgAge {
    public static void main(String[] args) {
        Map<String, Double> collect = new Employee().getEmployeeList().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
        System.out.println("Average age is "+collect);
    }
}
