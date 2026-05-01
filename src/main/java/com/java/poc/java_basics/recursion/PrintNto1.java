package com.java.poc.java_basics.recursion;

public class PrintNto1 {
    public static void main(String[] args) {
        int n = 10;
        printFunction(n);
    }

    public static void printFunction(int n){
        if(n==0){
            return;
        }
        System.out.println(n);
        printFunction(--n);
    }
}
