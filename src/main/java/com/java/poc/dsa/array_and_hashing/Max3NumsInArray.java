package com.java.poc.dsa.array_and_hashing;

public class Max3NumsInArray {

    public static void main(String[] args) {

        int[] arr = new int[]{1, 2, 3, 4, 5};
        int current_sum = 0;
        int max_sum = 0;

        for (int i = 0; i < arr.length; i++) {
            if (i + 2 < arr.length) {
                current_sum = arr[i] + arr[i + 1] + arr[i + 2];

                if (max_sum < current_sum) {
                    max_sum = current_sum;
                }
            }

        }

        System.out.println("max sum is : " + max_sum);
    }
}
