package com.java.poc.dsa.array_and_hashing;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
  public static void main(String[] args) {
    int[] nums = new int[]{2,3,4,6,7};
    int target = 7;
    twoSum(nums,target);
  }

  public static int[] twoSum(int[] nums, int target) {
    int delta;
    Map<Integer,Integer> numMap = new HashMap<>();
    for(int i = 0; i<nums.length;i++){
      delta = target - nums[i];
      if(numMap.containsKey(delta)){

        return new int[]{numMap.get(delta),i};
      }else{
        numMap.put(nums[i],i);
      }
    }
    return new int[]{-1,-1};
  }
}
