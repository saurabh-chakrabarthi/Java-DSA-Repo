package com.java.poc.dsa.math;

import java.util.Arrays;

public class Fibonacci {
    private static int max = 100;
    private static int nil = -1;
    private static int[] lookup = new int[max + 1];

    private static int fib_recursion(int i) {

        if (i >= 2) {
            return fib_recursion(i - 1) + fib_recursion(i - 2);
        } else if (i == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int fib_tabulation(int n) {
        /* Declare an array to store Fibonacci numbers. */
        int[] f = new int[n + 2]; // 1 extra to handle case, n = 0
        int i;

        /* 0th and 1st number of the series are 0 and 1*/
        f[0] = 0;
        f[1] = 1;

        for (i = 2; i <= n; i++) {
       /* Add the previous 2 numbers in the series
         and store it */
            f[i] = f[i - 1] + f[i - 2];
        }
        System.out.println(Arrays.toString(f));
        return f[n];
    }

    private static int fib_memoization(int n) {

        if (lookup[n] == nil) {
            if (n <= 1) {
                lookup[n] = n;
            } else {
                lookup[n] = fib_memoization(n - 1) + fib_memoization(n - 2);
            }
        }
        System.out.println(Arrays.toString(lookup));
        return lookup[n];
    }

    private static void initialize() {
        for (int i = 0; i < max; i++) {
            lookup[i] = nil;
        }
    }

    public static void main(String args[]) {

        System.out.println("Fibonacci Sum is :" + fib_recursion(99));
        System.out.println("Fibonacci Sum is :" + fib_tabulation(20));
        initialize();
        System.out.println("Fibonacci Sum is :" + fib_memoization(5));
    }
}
