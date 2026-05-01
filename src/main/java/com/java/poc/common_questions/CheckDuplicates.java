package com.java.poc.common_questions;

import java.util.HashSet;
import java.util.Set;

public class CheckDuplicates {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 20};
        System.out.println("Array contains duplicates: " + checkDuplicatesUsingHashSet(arr));
    }

    private static String checkDuplicates(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    return "Yes";
                }
            }
        }
        return "No";
    }

    private static String checkDuplicatesUsingHashSet(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (set.contains(arr[i])) {
                return "Yes";
            } else {
                set.add(arr[i]);
            }
        }
        return "No";
    }
}
