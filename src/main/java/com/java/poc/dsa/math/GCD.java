package com.java.poc.dsa.math;

public class GCD {

    public static void main(String args[]) {
        int a, b;
        int[] arr = {2, 4, 8};
        int result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            result = gcd(arr[i], result);
        }
        System.out.println("GCD is " + result);
    }

    public static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

}
