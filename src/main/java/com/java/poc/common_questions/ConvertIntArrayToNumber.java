package com.java.poc.common_questions;

public class ConvertIntArrayToNumber {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            num = num * 10 + arr[i];
        }
        System.out.println(num);
    }
}
