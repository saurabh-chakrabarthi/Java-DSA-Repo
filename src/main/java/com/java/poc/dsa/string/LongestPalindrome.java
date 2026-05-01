package com.java.poc.dsa.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestPalindrome {
    public static void main(String[] args) {
        String str = "bananas";
        System.out.println("length is : "+checkPalindromeUsingHashSet(str));
        System.out.println("length is : "+checkPalindromeUsingHashMap(str));
        System.out.println("length is : "+checkPalindromeUsingFrequencyArray(str));
    }

    private static int checkPalindromeUsingHashSet(String str) {
        Set<Character> set = new HashSet<>();
        int len = 0;
        for (char c : str.toCharArray()) {
            if (set.contains(c)) {
                len += 2;
                set.remove(c);
            }
            else set.add(c);
        }

        return set.size() > 0 ? len + 1 : len;
    }

    private static int checkPalindromeUsingHashMap(String str) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int len = 0;
        for (int i : map.values()) {
            len += (i % 2 == 0)? i : i - 1;
        }

        return len < str.length() ? len + 1 : len;
    }

    private static int checkPalindromeUsingFrequencyArray(String str) {
        int[] charCount = new int[128];
        for (char c : str.toCharArray()) {
            charCount[c]++;
        }

        int len = 0;
        for (int count : charCount) {
            len += 2 * (count / 2);
        }

        return len < str.length() ? len + 1 : len;
    }
}
