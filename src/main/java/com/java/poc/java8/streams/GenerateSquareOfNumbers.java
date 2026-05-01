package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenerateSquareOfNumbers {
    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1,2,3,4,5);
        System.out.println("Square of the input is : "+integerList.stream().map(a->a*a).collect(Collectors.toList()));
    }
}
