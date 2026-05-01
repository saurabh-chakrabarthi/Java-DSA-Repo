package com.java.poc.java_basics.recursion;

public class FibonacciRecursion {
    public static void main(String[] args) {
        int num = 10;
        System.out.println(num+"th fibonacci number is : "+getNthFibonacci(num));

    }

    public static int getNthFibonacci(int num){
        if(num <=1){
            return num;
        }
        int n_1 = getNthFibonacci(num-1);
        int n_2 = getNthFibonacci(num-2);

        return n_1+n_2;
    }

}
