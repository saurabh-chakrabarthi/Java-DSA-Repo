package com.java.poc.dsa.array_and_hashing;

public class MaxRepeatingNumberInArray {
    static int tallest = 0;
    static int frequency = 0;

    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 1, 3, 3, 4, 5, 6, 7, 7, 7, 7};
        calculateMaxRepeatingNumber(arr);
    }

    private static void calculateMaxRepeatingNumber(int ar[]) {
        int max = ar[0];
        int num = 1;
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] > max) {
                max = ar[i];
                num = 1;
            } else if (ar[i] == max) {
                num++;
            }
        }
        System.out.println(num);
    }
}
