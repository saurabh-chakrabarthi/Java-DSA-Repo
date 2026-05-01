package com.java.poc.java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicates {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("apple", "banana", "melon", "apple");
        System.out.println("Removed Dupes : "+list.stream().distinct().collect(Collectors.toList()));
    }
}
