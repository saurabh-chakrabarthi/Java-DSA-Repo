package com.java.poc.java8.predicate;

import java.util.function.Predicate;

public class IsStringLengthGreaterThan {
    /*
    checks if string length is greater than 5 or not
     */
    public static void main(String[] args) {
        Predicate<String> p = s -> s.length() > 5;
        System.out.println(p.test("test"));
        System.out.println(p.test("helloworld"));

    }
}
