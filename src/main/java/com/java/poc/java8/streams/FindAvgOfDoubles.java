package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;

public class FindAvgOfDoubles {
    public static void main(String[] args) {
        List<Double> list = Arrays.asList(1.4,2.9,3.7,4.0,5.6);
        System.out.println("Average is : "+list.stream().mapToDouble(Double::doubleValue).average().getAsDouble());
    }
}
