package com.java.poc.java_basics.comparator;

import java.util.*;

public class SortEmployee {

    public static void main(String[] args) {
        List<Employee> empList = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setFirstName("John");
        emp1.setLastName("Wick");
        emp1.setEmpId(123);
        emp1.setAge(30);
        emp1.setSalary(100000);

        Employee emp2 = new Employee();
        emp2.setFirstName("Chris");
        emp2.setLastName("James");
        emp2.setEmpId(456);
        emp2.setAge(30);
        emp1.setSalary(80000);

        Employee emp3 = new Employee();
        emp3.setFirstName("John");
        emp3.setLastName("Hopkins");
        emp3.setEmpId(789);
        emp3.setAge(30);
        emp1.setSalary(120000);

        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3);


        Collections.sort(empList,Employee.AgeComparator);
        System.out.println(empList.toString());

        Collections.sort(empList,Employee.AgeComparator);
        System.out.println(empList.toString());

        Collections.sort(empList,Employee.AgeComparator);
        System.out.println(empList.toString());


    }

}
