package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class RotateArray {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Original Array"+Arrays.toString(arr));
        int n = 2;

        //approach 1
        //rotateArray(arr, n);
        //System.out.println("Rotated Array"+Arrays.toString(arr));

        //approach 2
        //rotate(arr,n);
        //System.out.println("Rotated Array"+Arrays.toString(arr));

        //approach 3
        rotateUsingTmpArray(arr,n);
        System.out.println("Rotated Array"+Arrays.toString(arr));
    }

    private static void rotateArray(int[] arr, int n) {
        n %= arr.length;
        reverse(arr, 0, arr.length - 1);
        reverse(arr, 0, n - 1);
        reverse(arr, n, arr.length - 1);
    }

    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    private static void rotate(int[] arr, int n) {
        // speed up the rotation
        n %= arr.length;
        int temp, previous;
        for (int i = 0; i < n; i++) {
            previous = arr[arr.length - 1];
            for (int j = 0; j < arr.length; j++) {
                temp = arr[j];
                arr[j] = previous;
                previous = temp;
            }
        }
    }

    public static void rotateUsingTmpArray(int[] arr, int n) {
        if(n > arr.length)
            n=n%arr.length;

        int[] result = new int[arr.length];

        for(int i=0; i < n; i++){
            result[i] = arr[arr.length-n+i];
        }

        int j=0;
        for(int i=n; i<arr.length; i++){
            result[i] = arr[j];
            j++;
        }

        System.arraycopy( result, 0, arr, 0, arr.length );
    }
}
