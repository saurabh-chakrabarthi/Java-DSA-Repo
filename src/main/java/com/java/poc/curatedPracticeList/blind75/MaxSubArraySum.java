package com.java.poc.curatedPracticeList.blind75;

/**
 * 53. Maximum Subarray
 * Solved
 * Medium
 * Topics
 * Companies
 * Given an integer array nums, find the
 * subarray
 *  with the largest sum, and return its sum.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: The subarray [4,-1,2,1] has the largest sum 6.
 * Example 2:
 *
 * Input: nums = [1]
 * Output: 1
 * Explanation: The subarray [1] has the largest sum 1.
 * Example 3:
 *
 * Input: nums = [5,4,-1,7,8]
 * Output: 23
 * Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 */
public class MaxSubArraySum {

    public static void main(String[] args) {

        int[] arr = new int[]{-2,1,-3,4,-1,2,1,-5,4};

        System.out.println("Max Sub Array Sum is : "+maxSubArrayBruteForce(arr));
        System.out.println("Max Sub Array Sum is : "+maxSubArrayOptimised(arr));
        System.out.println("Max Sub Array Sum is : "+maxSubArrayKadane(arr));
        System.out.println("Max Sub Array Sum is : "+maxSubArray(arr));
    }

    public static int maxSubArrayBruteForce(int[] nums) {
        if(nums.length < 2){
            return nums[0];
        }

        int max = Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            for(int j=1;j<nums.length;j++){
                int sum = 0;
                for(int k=i;k<j;k++){
                    sum += nums[k];
                    max = Math.max(max,sum);
                }
            }
        }
        return max;
    }
    public static int maxSubArrayOptimised(int[] nums) {
        if(nums.length < 2){
            return nums[0];
        }

        int max = Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            int sum = 0;
            for(int j=i;j<nums.length;j++){
                sum += nums[j];
                max = Math.max(max,sum);
            }
        }
        return max;
    }

    public static int maxSubArrayKadane(int[] nums) {

        if(nums==null || nums.length==0)
            return 0;

        int currMax=nums[0], maxSoFar=nums[0];
        for(int i=1; i<nums.length; i++){
            currMax=Math.max(nums[i], currMax+nums[i]);
            maxSoFar=Math.max(currMax, maxSoFar);
        }
        return maxSoFar;
    }

    public static int maxSubArray(int[] nums) {
        if(nums.length<=1){
            return nums[0];
        }
        int max = Integer.MIN_VALUE;
        int sum=0;
        for (int num : nums) {
            sum += num;
            max = Math.max(max, sum);
            if (sum < 0) {
                sum = 0;
            }
        }
        return max;
    }

}
