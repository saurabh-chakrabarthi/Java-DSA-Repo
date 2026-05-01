package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

import java.util.Map;
import java.util.stream.Collectors;

public class FindDeptByMaxEmployee {
    public static void main(String[] args) {
        Map<String, Long> collect = new Employee().getEmployeeList().stream().collect(Collectors.groupingBy(Employee::getDepartmantName, Collectors.counting()));
        System.out.println("Dept by employee : "+collect);

        Map.Entry<String, Long> stringLongEntry = new Employee().getEmployeeList().stream().collect(Collectors.groupingBy(Employee::getDepartmantName, Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).get();
        System.out.println("max employee : "+stringLongEntry);
    }
}
