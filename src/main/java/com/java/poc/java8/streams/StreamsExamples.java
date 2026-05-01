package com.java.poc.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsExamples {
    public static void main(String[] args) {

        example();
    }


    public static void example() {

        System.out.println("---------- Filtered ----------");
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> stream1 = list.stream();
        Stream<String> filteredStream = stream1.filter(s -> s.startsWith("a"));
        //filteredStream.forEach(System.out::println);
        List<String> list1 = filteredStream.collect(Collectors.toList());
        System.out.println("list : " + list1);


        System.out.println("---------- Upper Case ----------");

        String[] array = {"a", "b", "c"};
        Stream<String> stream2 = Arrays.stream(array);
        Stream<String> upperCaseStream = stream2.map(String::toUpperCase);
        //upperCaseStream.forEach(System.out::println);
        Optional<String> concatenated = upperCaseStream.reduce((s1, s2) -> s1 + s2);
        System.out.println("concatenated : " + concatenated);

        System.out.println("---------- Sorted ----------");

        Stream<String> stream3 = Stream.of("a", "b", "c", "d", "e", "a", "b");
        Stream<String> sortedStream = stream3.sorted();
        //sortedStream.forEach(System.out::println);
        long count = sortedStream.count();
        System.out.println("count : " + count);

        System.out.println("---------- Distinct ----------");

        Stream<String> stream4 = Stream.of("a", "b", "c", "d", "e", "a", "b");
        Stream<String> distinctStream = stream4.distinct();
        //distinctStream.forEach(System.out::println);
        Optional<String> first = distinctStream.findFirst();
        System.out.println("first : " + first);

        System.out.println("---------- All Operations Together ----------");

        List<String> strings = Arrays.asList("a", "b", "c", "a", "b", "c");
        List<String> result = strings.stream()
                .filter(s -> s.startsWith("a"))
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        System.out.println(result); // Output: [A]
    }

}
