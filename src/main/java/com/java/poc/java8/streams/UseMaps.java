package com.java.poc.java8.streams;

import java.util.List;

public class UseMaps {
    public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3,4,5);
        list.stream().filter(a->a%2 !=0).map(a->a*a*a).filter(a->a>=12).forEach(System.out::println);


    }
}
