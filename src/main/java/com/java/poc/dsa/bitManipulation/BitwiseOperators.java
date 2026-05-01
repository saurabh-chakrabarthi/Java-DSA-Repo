package com.java.poc.dsa.bitManipulation;

public class BitwiseOperators {

    public static void main(String[] args) {
        int n1=2;
        int n2=3;

        bitwiseOR(n1,n2);
        bitwiseAND(n1,n2);
        bitwiseCompliment(n1);
        bitwiseXOR(n1,n2);
        bitwiseLeftShift(n1);
        bitwiseRightShift(n1);
        bitwiseUnsignedRightShift(n1);
    }

    public static void bitwiseOR(int n1, int n2){
        int result = n1 | n2;
        System.out.println("bitwiseOR of "+n1+" and "+n2+" is :"+result);
    }

    public static void bitwiseAND(int n1, int n2){
        int result = n1 & n2;
        System.out.println("bitwiseAND of "+n1+" and "+n2+" is :"+result);
    }

    public static void bitwiseXOR(int n1, int n2){
        int result = n1 ^ n2;
        System.out.println("bitwiseXOR of "+n1+" and "+n2+" is :"+result);
    }

    public static void bitwiseCompliment(int n1){
        int result = ~n1 ;
        System.out.println("bitwiseCompliment of "+n1+" is :"+result);
    }

    public static void bitwiseLeftShift(int n1){
        int result = n1 << 3 ;
        System.out.println("bitwiseLeftShift of "+n1+" is :"+result);
    }

    public static void bitwiseRightShift(int n1){
        int result = n1 >> 2 ;
        System.out.println("bitwiseRightShift of "+n1+" is :"+result);
    }

    public static void bitwiseUnsignedRightShift(int n1){
        int result = n1 >>> 2 ;
        System.out.println("bitwiseUnsignedRightShift of "+n1+" is :"+result);
    }
}
