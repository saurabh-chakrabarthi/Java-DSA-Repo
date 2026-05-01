package com.java.poc.java8.streams;

import com.java.poc.java8.model.Employee;

public class FindTotalCount {
    public static void main(String[] args) {
        long count = new Employee().getEmployeeList().stream().count();
        System.out.println("Total student count is : "+count);
    }
}
