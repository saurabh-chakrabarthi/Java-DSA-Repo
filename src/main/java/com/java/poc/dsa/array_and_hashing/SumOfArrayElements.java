package com.java.poc.dsa.array_and_hashing;

public class SumOfArrayElements {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("sum " + sumOfElements(arr));

    }

    private static int sumOfElements(int[] arr) {
        int sum = 0;
        for (int anArr : arr) {
            sum = sum + anArr;
        }
        return sum;
    }
}
