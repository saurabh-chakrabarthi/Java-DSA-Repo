package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class FindByAge {
    public static void main(String[] args) {
        List<Employee> collect = new Employee().getEmployeeList().stream().filter(a -> a.getAge() < 30).collect(Collectors.toList());
        System.out.println("List of employee with age less than 30 : "+collect);
    }
}
