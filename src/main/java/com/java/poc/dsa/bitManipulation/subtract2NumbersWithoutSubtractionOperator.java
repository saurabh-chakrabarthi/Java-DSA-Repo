package com.java.poc.dsa.bitManipulation;

public class subtract2NumbersWithoutSubtractionOperator {
    public static void main(String[] args) {
        int a = 15;
        int b = 7;

        System.out.println("Subtracting "+a+" from "+b+" is : "+addNums(a,b));
    }

    private static int addNums(int a, int b) {
        if(a==0){
            return b;
        }
        if(b==0){
            return a;
        }
        while(b!=0) {
            int carry = ~a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }
}
