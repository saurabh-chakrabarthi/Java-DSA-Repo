package com.java.poc.dsa.array_and_hashing;

public class PrintMaxSumSubarray {
    public static void main(String[] args) {

        int[] arr = new int[]{-2,1,3};
        maxSubArray(arr);
    }

    public static void maxSubArray(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum=0;
        int start=0;
        int end=0;
        int tmp=0;
        for (int i=0;i<nums.length;i++) {
            sum += nums[i];

            if (sum < 0) {
                sum = 0;
                tmp=i+1;
            }

            if(sum>max){
                max=sum;
                start=tmp;
                end=i;
            }
        }
        System.out.println("Max Sub Array Sum is : "+max);
        System.out.println("Max Sub Array is : ["+start+","+end+"]");
    }
}
