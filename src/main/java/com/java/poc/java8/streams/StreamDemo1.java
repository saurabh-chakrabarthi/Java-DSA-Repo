package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class StreamDemo1 {

    public static void main(String[] args) {

        Integer[] arr = new Integer[] { 100, 100, 9, 8, 200 };
        List<Integer> list = Arrays.asList(arr);
        // Stored the array as list
        OptionalInt avg = list.stream().mapToInt(n -> n).max();
                //.filter(n -> n > 100).average();

        /* Converted it into Stream and filtered out the numbers
            which are greater than 100. Finally calculated the average
        */

        if (avg.isPresent())
            System.out.println(avg.getAsInt());

        String str = "Hello World ! How are you ?";
        char[] StrArray =  str.toCharArray();
        System.out.println(Stream.of(StrArray).count());
    }
}
