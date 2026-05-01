package com.java.poc.dsa.string;

import java.util.Arrays;

public class LongestCommonPrefix {
    public static void main(String[] args) {
        String[] strs = {""};
        checkLongestCommonPrefix(strs);
    }

    private static void checkLongestCommonPrefix(String[] strs) {
        System.out.println("unsorted array is : " + Arrays.toString(strs));
        Arrays.sort(strs);
        System.out.println("sorted array is : " + Arrays.toString(strs));
        StringBuilder sb = new StringBuilder();
        int target = Math.min(strs[0].length(), strs[strs.length - 1].length());
        for (int j = 0; j < target; j++) {
            if (strs[0].charAt(j) == strs[strs.length - 1].charAt(j)) {
                sb.append(strs[0].charAt(j));
            } else {
                break;
            }
        }

        System.out.println("longest common prefix is : " + sb.toString());
    }
}
