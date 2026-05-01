package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupByDept {
    public static void main(String[] args) {
        Map<String, List<Employee>> mapData = new Employee().getEmployeeList().stream().collect(Collectors.groupingBy(Employee::getDepartmantName));
        System.out.println("Students grouped by the department names : "+mapData);
    }
}
