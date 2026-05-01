package com.java.poc.java8.lambda;

public class LambdaPrint {

    public static void main(String[] args) {
        ILambdaPrint i = () -> System.out.println("Hello");
        i.print();
    }
}
