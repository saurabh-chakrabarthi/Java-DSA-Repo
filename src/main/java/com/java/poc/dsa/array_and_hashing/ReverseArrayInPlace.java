package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class ReverseArrayInPlace {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("input array is : " + Arrays.toString(arr));
        reverseArray(arr);
        System.out.println("reversed array is : " + Arrays.toString(arr));
    }

    private static void reverseArray(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }
}
