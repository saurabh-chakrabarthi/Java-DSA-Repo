package com.java.poc.java_basics.comparable;

import java.util.Comparator;

public class Employee implements Comparable<Employee> {
  private String name;
  private int age;
  private int salary;

  public Employee(String name, int age, int salary) {
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }

  @Override
  public String toString() {
    return "Employee{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", salary=" + salary +
            '}';
  }

  @Override
  public int compareTo(Employee o) {
    if(this.age<o.age){
      return -1;
    }else if(this.age>o.age){
      return 1;
    }else{
      return 0;
    }
  }

  public static class ageComparator implements Comparator<Employee>{

    @Override
    public int compare(Employee o1, Employee o2) {
      if(o1.age<o2.age){
        return -1;
      }else if(o1.age>o2.age){
        return 1;
      }else{
        return 0;
      }
    }
  }

  public static class salComparator implements Comparator<Employee>{

    @Override
    public int compare(Employee o1, Employee o2) {
      if(o1.salary<o2.salary){
        return 1;
      }else if(o1.salary>o2.salary){
        return -1;
      }else{
        return 0;
      }
    }
  }
}
