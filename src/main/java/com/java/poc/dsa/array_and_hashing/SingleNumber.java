package com.java.poc.dsa.array_and_hashing;

public class SingleNumber {
    public static void main(String[] args) {
        int [] arr = new int[] {1,1,2,2,3};
        System.out.println("Single Number is : "+findSingleNumber(arr));
    }

    private static int findSingleNumber(int[] arr) {

        int num = 0;
        for(int i : arr){
            num = num ^ i;
        }

        return num;
    }
}
