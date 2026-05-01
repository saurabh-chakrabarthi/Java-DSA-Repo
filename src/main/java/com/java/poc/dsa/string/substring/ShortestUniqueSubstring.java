package com.java.poc.dsa.string.substring;

import java.util.HashMap;
import java.util.Map;

public class ShortestUniqueSubstring {
    public static void main(String[] args) {
        System.out.println(findShortestUniqueSubstring("abaaba")); // Output: 2
        System.out.println(findShortestUniqueSubstring("zyzyzyz")); // Output: 5
    }

    private static int findShortestUniqueSubstring(String s) {
        int n = s.length();
        for (int len = 1; len <= n; len++) {
            Map<String, Integer> freq = new HashMap<>();
            // Sliding window to count substrings of length 'len'
            for (int i = 0; i <= n - len; i++) {
                String substr = s.substring(i, i + len);
                freq.put(substr, freq.getOrDefault(substr, 0) + 1);
            }
            // Check for a unique substring
            for (int count : freq.values()) {
                if (count == 1) {
                    return len;
                }
            }
        }
        return n;
    }
}
