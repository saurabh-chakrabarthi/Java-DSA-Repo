package com.java.poc.java8.streams;

import java.util.Arrays;

public class FindNthBiggestElement {

    public static void main(String[] args) {
        int[] arr = {5, 3, 9, 1, 7, 7, 9};
        int n = 3;
        int nthBiggestElement = findNthBiggestElement(arr, n);
        System.out.println("The " + n + "th biggest element is: " + nthBiggestElement);
    }

    public static int findNthBiggestElement(int[] arr, int n) {
        return Arrays.stream(arr).sorted().skip(n - 1).findFirst().getAsInt();
    }
}
