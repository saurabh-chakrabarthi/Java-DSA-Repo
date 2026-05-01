package com.java.poc.java8.lambda;

public class LambdaSquare {
    public static void main(String[] args) {
        ILambdaSquare i = a-> a*a;
        System.out.println("The square is : "+i.squareIt(4));
    }
}
