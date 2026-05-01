package com.java.poc.java8.streams;

import java.util.List;

public class PrintEvenNumbers {
    public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3,4,5);
        list.stream().filter(a->a%2!=0).forEach(System.out::println);
    }
}
