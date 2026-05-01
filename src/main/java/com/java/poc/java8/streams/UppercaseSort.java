package com.java.poc.java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UppercaseSort {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("apple", "Banana", "Orange", "leMoN");
        System.out.println("New string is : "+list.stream().map(String::toUpperCase).sorted().collect(Collectors.toList()));

    }
}
