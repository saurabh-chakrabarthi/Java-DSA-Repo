package com.java.poc.java8.lambda;

public class LambdaSum {
    public static void main(String[] args) {

        ILambdaSum i = (a, b) -> System.out.println("Sum is : "+(a+b));
        i.sum(4,5);
        i.sum(40,50);
    }
}
