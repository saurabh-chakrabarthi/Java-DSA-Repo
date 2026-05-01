package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class ArraySortingOnePass {
    public static void main(String[] args) {
        int[] nums = new int[]{-4,-1,0,3,10};
        System.out.println("Unsorted Array : "+ Arrays.toString(nums));
        System.out.println("Sorted Array : "+ Arrays.toString(sortedArray(nums)));
    }
    public static int[] sortedArray(int[] nums) {
        int i=0;
        int j=nums.length-1;
        int k=nums.length-1;
        int[] ans=new int[nums.length];

        while(i<=j){
            int val1=nums[i];
            int val2=nums[j];

            if(val2>val1){
                ans[k]=val2;
                k--;
                j--;
            }else{
                ans[k]=val1;
                k--;
                i++;
            }
        }
        return ans;
    }
}
