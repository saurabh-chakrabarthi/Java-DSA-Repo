package com.java.poc.java8.lambda;

public class LambdaLength {

    public static void main(String[] args) {
        String b = "abc";
        ILambdaLength i = a -> a.length();
        System.out.println("Length of the String " + b + " is : " + i.len(b));
    }
}
