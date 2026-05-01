package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckIfNumExists {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,3,5,6,42);
        System.out.println("Number 42 exists : "+list.stream().anyMatch(a->a.equals(42)));
    }
}
