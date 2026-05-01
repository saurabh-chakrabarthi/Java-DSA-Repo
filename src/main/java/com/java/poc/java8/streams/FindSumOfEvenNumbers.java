package com.java.poc.java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindSumOfEvenNumbers {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        System.out.println("Sum of even numbers : "+nums.stream().filter(a->a%2==0).mapToInt(Integer::intValue).sum());
    }
}
