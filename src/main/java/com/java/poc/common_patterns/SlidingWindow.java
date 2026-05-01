package com.java.poc.common_patterns;

public class SlidingWindow {

    public static void main(String[] args) {
        int[] arr = { 2, 1, 5, 14, 7, 3, 10};
        int k = 3;
        System.out.println("Max Sum of Subarray is : "+maxSumSubarray(arr,k));
    }

    public static int maxSumSubarray(int[] arr, int k) {
        int windowSum = 0, maxSum = 0;

        // First, sum up the first 'k' elements
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        maxSum = windowSum;

        // Slide the window across the array
        for (int i = k; i < arr.length; i++) {
            windowSum += arr[i] - arr[i - k]; // Add next element, subtract the element going out
            maxSum = Math.max(maxSum, windowSum); // Track the maximum sum
        }

        return maxSum;
    }

}
