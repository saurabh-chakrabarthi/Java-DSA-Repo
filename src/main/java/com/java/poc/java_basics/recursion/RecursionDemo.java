package com.java.poc.java_basics.recursion;

public class RecursionDemo {

    public static void main(String[] args) {
        int count = 0;
        function(count);
    }

    public static void function(int count){
        if(count == 10){
            return;
        }
        System.out.println(count++);
        function(count);

    }
}
