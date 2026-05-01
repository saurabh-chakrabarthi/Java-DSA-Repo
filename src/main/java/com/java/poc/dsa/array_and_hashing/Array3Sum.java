package com.java.poc.dsa.array_and_hashing;

import java.util.*;

/**
 * 15. 3Sum
 * Solved
 * Medium
 * Topics
 * Companies
 * Hint
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 *
 * Notice that the solution set must not contain duplicate triplets.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation:
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
 * The distinct triplets are [-1,0,1] and [-1,-1,2].
 * Notice that the order of the output and the order of the triplets does not matter.
 * Example 2:
 *
 * Input: nums = [0,1,1]
 * Output: []
 * Explanation: The only possible triplet does not sum up to 0.
 * Example 3:
 *
 * Input: nums = [0,0,0]
 * Output: [[0,0,0]]
 * Explanation: The only possible triplet sums up to 0.
 *
 *
 * Constraints:
 *
 * 3 <= nums.length <= 3000
 * -105 <= nums[i] <= 105
 */
public class Array3Sum {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        System.out.println("input array : " + Arrays.toString(arr));
        int target = 10;
        System.out.println("target : " + target);
        List<List<Integer>> arrElementList = calculate_using_brute_force(arr, target);
        List<List<Integer>> arrElementList1 = calculate_using_sorting(arr,target);

        if (arrElementList.size() == 0) {
            System.out.println("No matching triplet found !");
        } else {
            System.out.println("The matching triplet(s) are : ");
            for (List<Integer> i : arrElementList) {
                System.out.println(Arrays.toString(i.toArray()));
            }
        }
    }

    private static List<List<Integer>> calculate_using_brute_force(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> subList = null;
        HashSet<List<Integer>> set = new HashSet<>();

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == target) {
                        subList = new ArrayList<Integer>();
                        subList.add(nums[i]);
                        subList.add(nums[j]);
                        subList.add(nums[k]);
                        set.add(subList);
                    }
                }
            }
        }

        list.addAll(set);

        return list;
    }


    private static List<List<Integer>> calculate_using_sorting(int[] nums,int target) {
        List<List<Integer>> elementList = new ArrayList<List<Integer>>();
        Arrays.sort(nums);

        for(int i=0;i<nums.length-2;i++){
            if(i == 0 || nums[i] != nums[i - 1]){
                int low = i+1;
                int high = nums.length-1;
                int sum = target-nums[i];

                while(low<high){
                    if(nums[low]+nums[high] == sum){
                        elementList.add(Arrays.asList(nums[i],nums[low],nums[high]));
                        while(low<high && nums[low] == nums[low+1]) low++;
                        while(low<high && nums[high] == nums[high-1]) high--;
                        low++;
                        high--;
                    }else if(nums[low]+nums[high]<sum){
                        low++;
                    }else{
                        high--;
                    }
                }
            }
        }
        return elementList;
    }

}
