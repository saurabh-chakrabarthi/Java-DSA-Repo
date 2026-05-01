package com.java.poc.common_patterns;

public class TwoPointer {
    public static void main(String[] args) {
        int[] arr = { 2, 1, 5, 14, 7, 3, 10};
        int k = 3;
        System.out.println(hasPairWithSum(arr,k));
    }

    public static boolean hasPairWithSum(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) {
                return true;
            } else if (sum < target) {
                left++; // Increase the left pointer to get a larger sum
            } else {
                right--; // Decrease the right pointer to get a smaller sum
            }
        }

        return false;
    }

}
