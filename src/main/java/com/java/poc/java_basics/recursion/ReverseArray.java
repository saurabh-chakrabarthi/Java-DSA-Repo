package com.java.poc.java_basics.recursion;

import java.util.Arrays;

public class ReverseArray {
    public static void main(String[] args) {
        int arr[] = { 1,2,3,4,5};
        System.out.println("Original Array : "+Arrays.toString(arr));
        reverseArray(arr, 0, arr.length - 1);
        System.out.println("Reversed Array : "+Arrays.toString(arr));
        System.out.println("*******************");
        System.out.println("Original Array : "+Arrays.toString(arr));
        reverseArray2(arr, 0);
        System.out.println("Reversed Array : "+Arrays.toString(arr));
    }

    //Function to reverse array using recursion
    static void reverseArray(int arr[], int start, int end) {
        if (start < end) {
            int tmp = arr[start];
            arr[start] = arr[end];
            arr[end] = tmp;
            reverseArray(arr, start + 1, end - 1);
        }
    }

    //Function to reverse array using recursion
    static void reverseArray2(int arr[],int start) {
        int end = arr.length-start-1;
        if (start < end) {
            int tmp = arr[start];
            arr[start] = arr[end];
            arr[end] = tmp;
            reverseArray2(arr, start + 1);
        }
    }

}
