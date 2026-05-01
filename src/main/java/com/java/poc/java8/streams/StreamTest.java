package com.java.poc.java8.streams;

import com.java.poc.java8.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {

        List<Student> students = Setup.getAllStudents();

        /*****************************************************
         Get student with exact match name "jayesh"
         *****************************************************/
        Optional<Student> stud = students.stream()
                .filter(student -> student.getName().equals("Jayesh"))
                .findFirst();
        System.out.println(stud.isPresent() ? stud.get().getName() : "No student found");
        System.out.println("--------------------");

        /*****************************************************
         Get student with matching address "1235"
         *****************************************************/
        Optional<Student> stud1 = students.stream()
                .filter(student -> student.getAddress().getZipcode().equals("1235"))
                .findFirst();
        System.out.println(stud1.isPresent() ? stud1.get().getName() : "No student found");
        System.out.println("--------------------");

        /*****************************************************
         Get all student having mobile numbers 3333.
         *****************************************************/
        List<Student> stud2 = students.stream()
                .filter(student111 -> student111.getMobileNumbers().stream().anyMatch(x -> Objects.equals(x.getNumber(), "3333")))
                .collect(Collectors.toList());

        String result1 = stud2.stream().map(std -> std.getName()).collect(Collectors.joining(",", "[", "]"));
        System.out.println(result1);
        System.out.println("--------------------");

        /*****************************************************
         Get all student having mobile number 1233 and 1234
         *****************************************************/
        List<Student> stud3 = students.stream()
                .filter(student -> student.getMobileNumbers().stream().allMatch(x -> Objects.equals(x.getNumber(), "1233") || Objects.equals(x.getNumber(), "1234")))
                .collect(Collectors.toList());

        String result4 = stud3.stream().map(std -> std.getName()).collect(Collectors.joining(",", "[", "]"));
        System.out.println(result4);
        System.out.println("--------------------");

        /*****************************************************
         Create a List<Student> from the List<TempStudent>
         *****************************************************/
        TempStudent tmpStud1 = new TempStudent(
                "Jayesh1",
                201,
                new Address("12341"),
                Arrays.asList(new MobileNumber("12331"), new MobileNumber("12341")));

        TempStudent tmpStud2 = new TempStudent(
                "Khyati1",
                202,
                new Address("12351"),
                Arrays.asList(new MobileNumber("11111"), new MobileNumber("33331"), new MobileNumber("12331")));

        List<TempStudent> tmpStudents = Arrays.asList(tmpStud1, tmpStud2);

        List<Student> employeeList = tmpStudents.stream()
                .map(tmpStud -> new Student(tmpStud.name, tmpStud.age, tmpStud.address, tmpStud.mobileNumbers))
                .collect(Collectors.toList());

        System.out.println(employeeList);
        System.out.println("--------------------");

        /*****************************************************
         Convert List<Employee> to List<String> of student name
         *****************************************************/
        List<String> studentsName = employeeList.stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        System.out.println(studentsName.stream().collect(Collectors.joining(",")));
        System.out.println(studentsName.stream().collect(Collectors.joining(",", "[", "]")));
        System.out.println("--------------------");

        /*****************************************************
         Convert List<employees> to String
         *****************************************************/
        String name = students.stream()
                .map(Student::getName)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(name);
        System.out.println("--------------------");

        /*****************************************************
         Change the case of List<String>
         *****************************************************/
        List<String> nameList =
                Arrays.asList("Jayesh", "Dany", "Khyati", "Hello", "Mango");

        nameList.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
        System.out.println("--------------------");

        /*****************************************************
         Sort List<String>
         *****************************************************/
        List<String> namesList =
                Arrays.asList("Jayesh", "Dany", "Khyati", "Hello", "Mango");

        namesList.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("--------------------");

        /*****************************************************
         Conditionally apply Filter condition, say if flag is enabled then
         *****************************************************/
        boolean sortConditionFlag = true;

        Stream<Student> conditionalFilterResult = students.stream()
                .filter(std -> std.getName().startsWith("J"));

        if (sortConditionFlag) {
            conditionalFilterResult = conditionalFilterResult.sorted(Comparator.comparing(Student::getName));
        }

        System.out.println("Before sorting :");
        students.forEach(s -> System.out.println(s.getName()));

        List<Student> list = conditionalFilterResult.collect(Collectors.toList());
        System.out.println("After filter and conditional sorting :");
        list.forEach(s -> System.out.println(s.getName()));

    }
}
