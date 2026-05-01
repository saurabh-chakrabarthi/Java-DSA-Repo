package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class FindByRank {
    public static void main(String[] args) {
        List<Employee> collect = new Employee().getEmployeeList().stream().filter(a -> a.getRank() > 50 && a.getRank() < 100).collect(Collectors.toList());
        System.out.println("Ranked list : "+collect);
    }
}
