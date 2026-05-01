package com.java.poc.java_basics.recursion;

public class FactorialofN {
    public static void main(String[] args) {
        int n = 6;
        System.out.println(calculateFactorial(n));
    }

    public static int calculateFactorial(int n) {
        if(n == 0 || n == 1){
            return 1;
        }

        return n * calculateFactorial(n-1);
    }
}
