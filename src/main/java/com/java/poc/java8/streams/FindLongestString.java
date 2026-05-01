package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;

public class FindLongestString {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello","world","I","love","Antartica");
        System.out.println(list.stream().mapToInt(String::length).max().getAsInt());
    }
}
