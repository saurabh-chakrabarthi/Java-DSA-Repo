package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConcatinateElementsInList {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("hello","world","how","are","you");
        System.out.println("New string is : "+stringList.stream().collect(Collectors.joining()));
    }

}
