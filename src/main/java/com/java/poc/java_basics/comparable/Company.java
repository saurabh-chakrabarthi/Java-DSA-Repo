package com.java.poc.java_basics.comparable;

import java.util.ArrayList;
import java.util.Collections;

public class Company {
  public static void main(String[] args) {
    Employee e1 = new Employee("Alex",26,53000);
    Employee e2 = new Employee("Bob",25,55000);
    Employee e3 = new Employee("Cathy",24,50000);

    ArrayList<Employee> employees = new ArrayList<>();
    employees.add(e1);
    employees.add(e2);
    employees.add(e3);

    System.out.println("natural insertion order : "+employees.toString());

    Collections.sort(employees);

    System.out.println("sorting based on age"+employees.toString());

    Collections.sort(employees, new Employee.ageComparator());

    System.out.println("sorting based on age comparator"+employees.toString());

    Collections.sort(employees, new Employee.salComparator());

    System.out.println("sorting based on sal comparator"+employees.toString());
  }
}
