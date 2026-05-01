package com.java.poc.dsa.array_and_hashing;

public class PrintAllSubArrays {
    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3};
        findSubArrays(arr);
    }

    private static void findSubArrays(int[] arr) {
        for(int i = 0; i < arr.length;i++){
            for(int j = 1; j < arr.length; j++){
                for(int k = i; k <= j; k++){
                    System.out.print(arr[k]+ " ");
                }
                System.out.println(" ");
            }
        }
    }
}
