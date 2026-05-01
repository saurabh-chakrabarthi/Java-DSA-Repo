package com.java.poc.dsa.array_and_hashing;

public class MaxProductSubArray {

    /**
     * Given an integer array nums, find a subarray that has the largest product, and return the product.
     *
     * The test cases are generated so that the answer will fit in a 32-bit integer.
     *
     *
     *
     * Example 1:
     *
     * Input: nums = [2,3,-2,4]
     * Output: 6
     * Explanation: [2,3] has the largest product 6.
     * Example 2:
     *
     * Input: nums = [-2,0,-1]
     * Output: 0
     * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
     *
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 104
     * -10 <= nums[i] <= 10
     * The product of any subarray of nums is guaranteed to fit in a 32-bit integer.
     * @param args
     */
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,-4,0,8,-12};
        System.out.println("Max product of the array "+maxProduct(nums));
    }

    private static int maxProduct(int[] nums) {
        if(nums == null || nums.length == 0){
            return 0;
        }
        int max = nums[0];
        int min = nums[0];
        int ans = nums[0];
        for(int i=1;i<nums.length;i++){
            if(nums[i] >=0){
                max = Math.max(nums[i], max * nums[i]);
                min = Math.min(nums[i], min * nums[i]);
            }else{
                int tmp = max;
                max = Math.max(nums[i], min * nums[i]);
                min = Math.min(nums[i], tmp * nums[i]);
            }
            ans = Math.max(ans, max);
        }
        return ans;
    }
}
