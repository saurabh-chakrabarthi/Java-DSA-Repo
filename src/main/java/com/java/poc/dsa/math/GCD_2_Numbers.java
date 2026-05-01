package com.java.poc.dsa.math;

public class GCD_2_Numbers {

    public static void main(String[] args) {
        //System.out.println(getGCD_1(13,26));
        System.out.println(getGCD_2(13, 26));
    }

    private static int getGCD_1(int i, int j) {
        int gcd = 1;
        if (i == 0) {
            return j;
        } else if (j == 0) {
            return i;
        } else if (i == j) {
            return i;
        }

        if (i % j == 0) {
            gcd = j;
        } else if (j % i == 0) {
            gcd = i;
        }
        return gcd;
    }

    static int getGCD_2(int a, int b) {
        if (b == 0) {
            return a;
        }
        return getGCD_2(b, a % b);
    }
}
