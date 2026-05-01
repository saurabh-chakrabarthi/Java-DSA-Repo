package com.java.poc.java8.streams;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class OddEvenNumbers {
    public static void main(String[] args)
    {
        List<Integer> listOfIntegers = Arrays.asList(71, 18, 42, 21, 67, 32, 95, 14, 56, 87);

        Map<Boolean, List<Integer>> oddEvenNumbersMap =
                listOfIntegers.stream().collect(Collectors.partitioningBy(i -> i % 2 == 0));

        Set<Entry<Boolean, List<Integer>>> entrySet = oddEvenNumbersMap.entrySet();

        for (Entry<Boolean, List<Integer>> entry : entrySet)
        {
            System.out.println("--------------");

            if (entry.getKey())
            {
                System.out.println("Even Numbers");
            }
            else
            {
                System.out.println("Odd Numbers");
            }

            System.out.println("--------------");

            List<Integer> list = entry.getValue();

            for (int i : list)
            {
                System.out.println(i);
            }
        }
    }
}
