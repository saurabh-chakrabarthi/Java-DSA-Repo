package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class FindAllDepts {
    public static void main(String[] args) {
        List<String> deptList = new Employee().getEmployeeList().stream().map(a -> a.getDepartmantName()).collect(Collectors.toList());
        System.out.println("Department List is : "+deptList);
    }
}
