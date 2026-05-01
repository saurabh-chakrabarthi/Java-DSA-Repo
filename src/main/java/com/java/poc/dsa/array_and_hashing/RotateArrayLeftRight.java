package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class RotateArrayLeftRight {

    public static void main(String[] args) {
        int[] input_left = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] input_right = {1, 2, 3, 4, 5, 6, 7, 8};
        int k = 3;
        System.out.println("Rotate given array " + Arrays.toString(input_left) + " by "+k+" places to the left.");
        int[] leftRotatedArray = rotateLeft(input_left, input_left.length, k);
        System.out.println("Left Rotated array: " + Arrays.toString(leftRotatedArray));
        System.out.println("*****************************");
        System.out.println("Rotate given array " + Arrays.toString(input_right) + " by "+k+" places to the right.");
        int[] rightRotatedArray = rotateRight(input_right, input_right.length, k);
        System.out.println("Right Rotated array: " + Arrays.toString(rightRotatedArray));
    }

    private static int[] rotateLeft(int[] input, int length, int numOfRotations) {
        for (int i = 0; i < numOfRotations; i++) {
            int temp = input[0];
            for (int j = 0; j < length - 1; j++) {
                input[j] = input[j + 1];
            }
            input[length - 1] = temp;
            System.out.println("Intermediate array " + Arrays.toString(input));
        }
        return input;
    }

    private static int[] rotateRight(int[] input, int length, int numOfRotations) {
        for (int i = 0; i < numOfRotations; i++) {
            int temp = input[length - 1];
            for (int j = length - 1; j > 0; j--) {
                input[j] = input[j - 1];
            }
            input[0] = temp;
            System.out.println("Intermediate array " + Arrays.toString(input));
        }
        return input;
    }
}
