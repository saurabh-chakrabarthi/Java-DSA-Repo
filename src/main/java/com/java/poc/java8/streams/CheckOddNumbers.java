package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CheckOddNumbers {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        System.out.println("Odd numbers are : "+list.stream().filter(a->a%2 !=0).collect(Collectors.toList()));
    }
}
