package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class IncrementArrayLastElementBy1 {
    public static void main(String[] args) {
        int[] inputArr = new int[]{9,9,9};
        System.out.println("old array : "+ Arrays.toString(inputArr));
        System.out.println("new array "+ Arrays.toString(plusOne(inputArr)));
    }

    public static int[] plusOne(int[] digits) {
        int carry = 1;
        for (int i = digits.length-1; i>= 0; i--) {
            digits[i] += carry;
            if (digits[i] <= 9) {
                return digits;
            }
            digits[i] = 0;
        }
        int[] ret = new int[digits.length+1];
        ret[0] = 1;
        return ret;
    }
}
