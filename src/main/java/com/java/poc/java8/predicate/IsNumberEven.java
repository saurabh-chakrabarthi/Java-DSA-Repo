package com.java.poc.java8.predicate;

import java.util.function.Predicate;

public class IsNumberEven {
    /*
    Predicate joining: using and, or, negate
    Predicate p1 : Check if number is greater than 10 or not
    Predicate p2 : Check if number is even or not
     */
    public static void main(String[] args) {
        Predicate<Integer> p1 = I -> I > 10;
        Predicate<Integer> p2 = I -> I%2 == 0;

        System.out.println(p1.and(p2).test(12));
        System.out.println(p1.and(p2).test(13));
        System.out.println(p1.and(p2).test(5));

        System.out.println(p1.or(p2).test(12));
        System.out.println(p1.or(p2).test(13));
        System.out.println(p1.or(p2).test(5));

        System.out.println(p1.negate().test(12));
        System.out.println(p1.negate().test(13));
        System.out.println(p1.negate().test(5));
    }
}
