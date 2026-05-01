package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

import java.util.Map;
import java.util.stream.Collectors;

public class FindCountInEachDept {
    public static void main(String[] args) {
        Map<String, Long> studentCount = new Employee().getEmployeeList().stream().collect(Collectors.groupingBy(Employee::getDepartmantName, Collectors.counting()));
        System.out.println("Employee count in each dept : "+studentCount);
    }
}
