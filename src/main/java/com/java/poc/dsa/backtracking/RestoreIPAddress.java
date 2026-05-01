package com.java.poc.dsa.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * Leetcode 93. Restore IP Addresses
 * A valid IP address consists of exactly four integers separated by single dots. Each integer is between 0 and 255 (inclusive) and cannot have leading zeros.
 *
 * For example, "0.1.2.201" and "192.168.1.1" are valid IP addresses, but "0.011.255.245", "192.168.1.312" and "192.168@1.1" are invalid IP addresses.
 * Given a string s containing only digits, return all possible valid IP addresses that can be formed by inserting dots into s. You are not allowed to reorder or remove any digits in s. You may return the valid IP addresses in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "25525511135"
 * Output: ["255.255.11.135","255.255.111.35"]
 * Example 2:
 *
 * Input: s = "0000"
 * Output: ["0.0.0.0"]
 * Example 3:
 *
 * Input: s = "101023"
 * Output: ["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 20
 * s consists of digits only.
 */
public class RestoreIPAddress {
    public static void main(String[] args) {
        String s = "25525511135";
        System.out.println(restoreIpAddresses(s));
        System.out.println(restoreIpAddressesBruteForce(s));
    }

    private static List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(String str, int start, List<String> parts, List<String> result) {
        if (parts.size() == 4 && start == str.length()) {
            result.add(String.join(".", parts));
            return;
        }
        if (parts.size() > 4) {
            return;
        }
        for (int len = 1; len <= 3; len++) {
            if (start + len > str.length()) {
                break;
            }
            String part = str.substring(start, start + len);
            if (isValid(part)) {
                parts.add(part);
                backtrack(str, start + len, parts, result);
                parts.remove(parts.size() - 1);
            }
        }
    }

    public static List<String> restoreIpAddressesBruteForce(String s) {
        List<String> result = new ArrayList<>();
        int n = s.length();

        for(int i=1;i<=4 && i<n;i++){
            for(int j=i+1;j<i+4 && j<n;j++){
                for(int k=j+1;k<j+4 && k<n;k++){
                    String part1 = s.substring(0,i);
                    String part2 = s.substring(i,j);
                    String part3 = s.substring(j,k);
                    String part4 = s.substring(k);

                    if (isValid(part1) && isValid(part2) && isValid(part3) && isValid(part4)) {
                        result.add(part1 + "." + part2 + "." + part3 + "." + part4);
                    }
                }
            }
        }
        return result;
    }

    private static boolean isValid(String part) {
        if (part.length() > 1 && part.startsWith("0")) {
            return false;
        }
        int value = Integer.parseInt(part);
        return value >= 0 && value <= 255;
    }
}
