package com.java.poc.java8.lambda;

public class LambdaString {
    public static void main(String[] args) {
        ILambdaString exclaim = (s) -> s + "!";
        ILambdaString ask = (s) -> s + "?";
        printFormatted("Hello", exclaim);
        printFormatted("Hello", ask);
    }
    public static void printFormatted(String str, ILambdaString format) {
        String result = format.run(str);
        System.out.println(result);
    }
}
