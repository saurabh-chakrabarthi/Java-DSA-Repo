package com.java.poc.dsa.slidingWindow;

public class SlidingWindowTechnique {

    public static void main(String[] args) {
        int[] nums = new int[]{1,0,1,2,1,1,7,5};
        System.out.println("Subarray sum : "+fixedSizeWindow(nums,3));
        System.out.println("Subarray sum : "+variableSizeWindow(nums,3));
    }

    public static int fixedSizeWindow(int[] nums, int x){
        int count = 0;
        int sum = 0;
        int maxSum = 0;

        for (int num : nums) {
            sum += num;
            count++;
            if (maxSum < sum) {
                maxSum = sum;
            }
            if (count >= x) {
                break;
            }
        }
        return maxSum;
    }

    public static int variableSizeWindow(int[] nums, int x){
        int windowStart = 0;
        int sum = 0;
        int maxSum = 0;

        for (int windowEnd = 0; windowEnd < nums.length; windowEnd++) {
            sum += nums[windowEnd]; // add the next element
            // slide the window, we don't need to slide if we've not hit the required window size of 'k'
            if (windowEnd >= x - 1) {
                if (maxSum < sum) {
                    maxSum = sum;
                }
                sum -= nums[windowStart]; // subtract the element going out
                windowStart++; // slide the window ahead
            }
        }
        return maxSum;
    }
}
