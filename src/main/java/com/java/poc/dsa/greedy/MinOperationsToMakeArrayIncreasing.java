package com.java.poc.dsa.greedy;

public class MinOperationsToMakeArrayIncreasing {

    public static void main(String[] args) {
        int[] nums = {3,2,1,2,1,7};
        System.out.println(minOperations(nums));
    }

    public static int minOperations(int[] nums) {
        int count = 0;

        for(int i=0;i<nums.length-1;i++){
            if(nums[i]>=nums[i+1]){
                count += nums[i] - nums[i+1]+1;
                nums[i+1] = nums[i] + 1;
            }
        }

        return count;
    }
}
