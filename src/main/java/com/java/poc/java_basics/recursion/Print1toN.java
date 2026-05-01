package com.java.poc.java_basics.recursion;

public class Print1toN {
    public static void main(String[] args) {
        int n = 10;
        int start = 1;
        printFunction(n,start);
    }

    public static void printFunction(int n,int start){
        if(start>n){
            return;
        }
        System.out.println(start);
        printFunction(n,++start);
    }
}
