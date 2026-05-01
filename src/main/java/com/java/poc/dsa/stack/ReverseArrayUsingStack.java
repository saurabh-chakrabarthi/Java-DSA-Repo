package com.java.poc.dsa.stack;

import java.util.Arrays;
import java.util.Stack;

public class ReverseArrayUsingStack {

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5};
        System.out.println("Input Array is : "+ Arrays.toString(arr));
        System.out.println("Reversed Array is : "+ Arrays.toString(reverseArray(arr)));
    }

    public static int[] reverseArray(int[] array) {
        Stack<Integer> stack = new Stack<>();
        for (int j : array) {
            stack.push(j);
        }
        int[] reversedArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            reversedArray[i] = stack.pop();
        }
        return reversedArray;
    }

}
