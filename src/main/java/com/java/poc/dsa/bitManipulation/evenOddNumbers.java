package com.java.poc.dsa.bitManipulation;

public class evenOddNumbers {
    public static void main(String[] args) {
        int num = 110;
        checkNum(num);
    }

    private static void checkNum(int num) {
        if((num & 1) ==0){
            System.out.println(num+" is even");
        }else {
            System.out.println(num+" is odd");
        }
    }
}
