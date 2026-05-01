package com.java.poc.dsa.binary_search;

public class SquareRoot {
    public static void main(String[] args) {
        int number = 16;
        int sqrt = findSquareRoot(number);
        System.out.println("The square root of " + number + " is: " + sqrt);
    }

    public static int findSquareRoot(int x) {
        if (x < 2) {
            return x; // The square root of 0 is 0 and the square root of 1 is 1
        }
        int left = 2, right = x / 2;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long squared = (long) mid * mid; // Use long to prevent overflow
            if (squared == x) {
                return mid; // Found the exact square root
            } else if (squared < x) {
                left = mid + 1; // Try for a larger number
            } else {
                right = mid - 1; // Try for a smaller number
            }
        }
        return right; // The integer part of the square root
    }
}
