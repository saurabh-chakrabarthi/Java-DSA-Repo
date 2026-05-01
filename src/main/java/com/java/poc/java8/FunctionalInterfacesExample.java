package com.java.poc.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionalInterfacesExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("--- Input ---");
        System.out.println(numbers);
        // Function
        System.out.println("--- Function Start ---");
        Function<Integer, Integer> squareFunction = x -> x * x;
        List<Integer> squaredNumbers = numbers.stream()
                .map(squareFunction)
                .collect(Collectors.toList());
        System.out.println("squaredNumbers : "+squaredNumbers); // Output: [1, 4, 9, 16, 25]
        System.out.println("--- Function End ---");


        // Predicate
        System.out.println("--- Predicate Start ---");
        Predicate<Integer> isOdd = x -> x % 2 != 0;
        List<Integer> oddNumbers = numbers.stream()
                .filter(isOdd)
                .collect(Collectors.toList());
        System.out.println("oddNumbers: "+ oddNumbers); // Output: [1, 3, 5]
        System.out.println("--- Predicate End ---");


        // Consumer
        System.out.println("--- Consumer Start ---");
        Consumer<Integer> printConsumer = System.out::println;
        System.out.println("printConsumer : ");
        numbers.forEach(printConsumer);
        System.out.println("--- Consumer End ---");
        // Output:
        // 1
        // 2
        // 3
        // 4
        // 5

        // Supplier
        System.out.println("--- Supplier Start ---");
        Random random = new Random();
        Supplier<Integer> randomSupplier = () -> numbers.get(random.nextInt(numbers.size()));
        System.out.println("random number : "+randomSupplier.get());
        System.out.println("--- Supplier End ---");


        // BiFunction
        System.out.println("--- BiFunction Start ---");
        BiFunction<Integer, Integer, Integer> sumWithIndex = (num, index) -> num + index;
        List<Integer> summedWithIndex = IntStream.range(0, numbers.size())
                .mapToObj(i -> sumWithIndex.apply(numbers.get(i), i))
                .collect(Collectors.toList());
        System.out.println("summedWithIndex : "+summedWithIndex); // Output: [1, 3, 5, 7, 9]
        System.out.println("--- BiFunction End ---");


        // UnaryOperator
        System.out.println("--- Unary Start ---");
        UnaryOperator<Integer> doubleOperator = x -> x * 2;
        List<Integer> doubledNumbers = numbers.stream()
                .map(doubleOperator)
                .collect(Collectors.toList());
        System.out.println("doubledNumbers : "+doubledNumbers); // Output: [2, 4, 6, 8, 10]
        System.out.println("--- Unary End ---");
    }
}

