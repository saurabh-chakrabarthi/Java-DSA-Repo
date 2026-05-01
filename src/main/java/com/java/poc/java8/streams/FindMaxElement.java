package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;

public class FindMaxElement {
    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1,3,5,8,9,34,76,8);
        System.out.println("Max element in the list is : "+integerList.stream().mapToInt(Integer::intValue).max().getAsInt());
    }
}
