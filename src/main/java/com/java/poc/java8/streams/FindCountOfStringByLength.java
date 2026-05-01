package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;

public class FindCountOfStringByLength {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("hello","world","how","are","you");
        System.out.println("Count of String with length greater than 4 : "+stringList.stream().filter(a->a.length()>4).count());
    }
}
