package com.java.poc.java_basics.recursion;

public class PrintNTimes {
    public static void main(String[] args) {
        int n = 10;
        printFunction(n);
    }

    public static void printFunction(int n){
        if(n == 0){
            return;
        }
        System.out.println("Hello World "+n);
        printFunction(--n);
    }
}
