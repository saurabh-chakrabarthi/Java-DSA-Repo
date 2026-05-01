package com.java.poc.dsa.array_and_hashing;

public class MaxSubArraySizeK {
    public static void main(String[] args) {
        int[] arr = new int[]{-1,2,-3,4,-5,4,7,8,1,2};
        int k = 3;
        System.out.println("Max SubArray sum is : "+maxSubArrSizeK(arr,k));
    }

    private static int maxSubArrSizeK(int[] arr, int k) {

        int windowSum = 0;
        int maxSum = 0;

        for(int i=0;i<k;i++){
            windowSum += arr[i];
        }

        for(int j=k;j<arr.length;j++){
            windowSum += arr[j] - arr[j-k];
            maxSum = Math.max(maxSum,windowSum);
        }

        return maxSum;
    }

}


