package com.java.poc.dsa.array_and_hashing;

public class MinMaxArraySum {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};

        miniMaxSum(arr);
    }

    private static void miniMaxSum(int[] arr) {
        long max = 0;
        long temp = 0;
        long min = 0;
        long sum = 0;
        for (int anArr : arr) {
            sum = sum + anArr;
        }

        for (int element : arr) {
            temp = sum - element;
            if (temp > max) {
                max = temp;
            }
            if (min == 0) {
                min = temp;
            }
            if (temp < min) {
                min = temp;
            }
        }

        System.out.println("max " + max);
        System.out.println("min " + min);

    }
}
