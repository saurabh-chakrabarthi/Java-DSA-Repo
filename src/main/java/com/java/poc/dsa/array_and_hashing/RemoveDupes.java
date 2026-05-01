package com.java.poc.dsa.array_and_hashing;

import java.util.*;

public class RemoveDupes {

    public static void main(String[] args) {

        int[] nums = new int[]{3, 7, 7, 8, 9, 0, 0, 8, 3, 4, 1, 2, 4, 5, 1, 2, 5, 5, 6, 6};
        //System.out.println("The length of unique elements is : " + removeDuplicates(nums));
        removeDupes(nums);
        removeDuplicatesTreeSet(nums);
    }

    private static int removeDuplicates(int[] nums) {
        int j = 0;
        int i = 1;
        System.out.println("Original Array : " + Arrays.toString(nums));
        Arrays.sort(nums);
        System.out.println("Sorted Array : " + Arrays.toString(nums));
        if (nums.length > 1) {
            while (i < nums.length) {
                if (nums[i] != nums[j]) {
                    j++;
                    nums[j] = nums[i];
                }
                i++;
            }
            System.out.println("After Operation on Array : " + Arrays.toString(nums));
        }
        return j + 1;
    }

    private static void removeDupes(int[] nums) {

        int index = 0;
        System.out.println("Original Array : " + Arrays.toString(nums));
        HashSet<Integer> integerHashSet = new HashSet<>();
        for (int i : nums) {
            integerHashSet.add(i);
        }

        int[] tmpArr = new int[integerHashSet.size()];

        for (Integer i : integerHashSet) {
            tmpArr[index] = i;
            index++;
        }
        System.out.println("Deduped Array : " + Arrays.toString(tmpArr));
        Arrays.sort(nums);
        System.out.println("Sorted Array : " + Arrays.toString(tmpArr));
    }

    public static int removeDuplicatesTreeSet(int[] nums) {
        Arrays.sort(nums);
        Set<Integer> set = new TreeSet<>();
        for (int n : nums) {
            set.add(n);
        }

        int count = set.size();
        List<Integer> list = new ArrayList<Integer>(set);
        for (int i = 0; i < count; i++) {
            nums[i] = list.get(i);
        }
        System.out.println("After Operation on Array : " + Arrays.toString(nums));
        return count;

    }

}
