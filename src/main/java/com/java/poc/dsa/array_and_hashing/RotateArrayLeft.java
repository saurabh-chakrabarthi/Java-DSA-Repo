package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class RotateArrayLeft {
  public static void main(String[] args) {
    int rotations = 4;
    int[] nums = new int[]{1, 2, 3, 4, 5};
    rotate(rotations, nums);
  }

  private static void rotate(int rotations, int[] nums) {
    //Initial array : [1,2,3,4,5]
    //rotation 1 : [2,3,4,5,1]
    //rotation 2 : [3,4,5,1,2]
    //rotation 3 : [4,5,1,2,3]
    //rotation 4 : [5,1,2,3,4]

    int[] tmp = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
      tmp[i] = nums[i];
    }

    for(int j = nums.length;j<rotations;j++){
      nums[j-nums.length] = nums[j];
    }

    for(int k = 0;k<nums.length;k++){
      nums[k+rotations-nums.length] = tmp[k];
    }
    System.out.println(Arrays.toString(tmp));
  }
}
