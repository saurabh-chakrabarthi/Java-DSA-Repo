package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RemoveNull {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello","world","I","love","Antartica",null);
        System.out.println(list.stream().filter(Objects::nonNull).collect(Collectors.toList()));
    }
}
