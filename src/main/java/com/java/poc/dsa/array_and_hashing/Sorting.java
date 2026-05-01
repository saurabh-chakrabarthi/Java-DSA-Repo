package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class Sorting {

    public static void main(String[] args) {
        int arr[] = new int[]{6, 3, 1, 8, 9, 2, 4, 7, 10, 5};

        System.out.println(Arrays.toString(arr));

        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr));

        sortArray();
    }

    public static void sortArray() {
        int[] arr = new int[]{78, 34, 1, 3, 90, 34, -1, -4, 6, 55, 20, -65};
        System.out.println("Array elements before sorting: " + Arrays.toString(arr));

//sorting logic
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int tmp = 0;
                if (arr[i] > arr[j]) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        System.out.println("Array elements after sorting: " + Arrays.toString(arr));
    }
}
