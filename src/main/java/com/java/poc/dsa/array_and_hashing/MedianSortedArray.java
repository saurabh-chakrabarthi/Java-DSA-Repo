package com.java.poc.dsa.array_and_hashing;

import java.util.Arrays;

public class MedianSortedArray {
    public static void main(String[] args) {
        int nums1[] = new int[]{1,2};
        int nums2[] = new int[]{3};
        findMedianSortedArrays(nums1,nums2);
    }
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] merged = new int[nums1.length + nums2.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while(i < nums1.length && j < nums2.length){
            if(nums1[i]<nums2[j]){
                merged[k] = nums1[i];
                i++;
                k++;
            }else{
                merged[k] = nums2[j];
                j++;
                k++;
            }
        }

        while(i<nums1.length){
            merged[k] = nums1[i];
            i++;
            k++;
        }

        while(j<nums2.length){
            merged[k] = nums2[j];
            j++;
            k++;
        }
        System.out.println("merged array : "+ Arrays.toString(merged));
        int mergedLength = merged.length;
        if((mergedLength)%2 == 0){
            return ( merged[mergedLength/2-1] + merged[mergedLength/2] )/2.0;
        }
        return merged[mergedLength/2];
    }
}
