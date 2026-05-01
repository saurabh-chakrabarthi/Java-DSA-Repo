package com.java.poc.java8.function;

import java.util.function.Function;

public class SquareOfNum {

    public static void main(String[] args) {
        Function<Integer,Integer> f = i->i*i;
        System.out.println("Square of 40 is : "+f.apply(40));
        System.out.println("Square of 50 is : "+f.apply(50));
    }
}
