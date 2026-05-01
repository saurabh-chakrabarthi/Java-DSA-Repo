package com.java.poc.dsa.array_and_hashing;

import java.util.*;

public class ThreeSum {

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 4, 6, 6, 7};
        System.out.println("input array : " + Arrays.toString(arr));
        int target = 8;
        System.out.println("target : " + target);
        System.out.println("The pairs are : "+threeSum(arr));
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> threeSumSet  = new HashSet<>();
        Arrays.sort(nums);

        for(int i=0; i<nums.length-2;i++){
            int j =i+1;
            int k = nums.length-1;

            while(j<k){
                int sum = nums[i]+nums[j]+nums[k];
                if(sum==0){
                    threeSumSet.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++;
                    k--;
                }
                else if (sum > 0){
                    k--;
                }
                else {
                    j++;
                }
            }
        }
        System.out.println(threeSumSet);
        return new ArrayList<>(threeSumSet);
    }
}
