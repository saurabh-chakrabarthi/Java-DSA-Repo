package com.java.poc.dsa.array_and_hashing;

import java.util.ArrayList;
import java.util.List;

public class FindDuplicatesInArrayWithoutExtraSpace {

    public static void main(String[] args) {

        int[] nums = new int[]{1,2,2,3,3,4,5};
        System.out.println("Duplicates in the array : "+findDuplicates(nums));
    }

    public static List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {

            int idx = Math.abs(nums[i]) - 1; // Get the mapped index for nums[i], using Math.abs only once

            if (nums[idx] < 0) // Check if nums[i] has been seen before, if true then add to the output list
            {
                res.add(idx + 1); // Need to add +1 as our range is 1-based
            }
            nums[idx] *= -1; // Unconditionally flip the sign, if it's positive, we want to mark it as negative. But if it's negative, we just recover previous value so that at the end our array is not modified
        }

        return res;
    }
}
