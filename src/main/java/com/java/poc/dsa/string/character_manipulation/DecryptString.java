package com.java.poc.dsa.string.character_manipulation;

/**
 * Leetcode Problem 1309: Decrypt String from Alphabet to Integer Mapping
 * You are given a string s formed by digits and '#'. We want to map s to English lowercase characters as follows:
 *
 * Characters ('a' to 'i') are represented by ('1' to '9') respectively.
 * Characters ('j' to 'z') are represented by ('10#' to '26#') respectively.
 * Return the string formed after mapping.
 *
 * The test cases are generated so that a unique mapping will always exist.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "10#11#12"
 * Output: "jkab"
 * Explanation: "j" -> "10#" , "k" -> "11#" , "a" -> "1" , "b" -> "2".
 * Example 2:
 *
 * Input: s = "1326#"
 * Output: "acz"
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s consists of digits and the '#' letter.
 * s will be a valid string such that mapping is always possible.
 */
public class DecryptString {
    public static void main(String[] args) {
        String str = "10#11#12"; // jkab
        System.out.println(decryptString(str)); // Output: "jkab"
    }

    private static String decryptString(String str) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            if (i + 2 < str.length() && str.charAt(i + 2) == '#') {
                int num = Integer.parseInt(str.substring(i, i + 2));
                result.append((char) ('a' + num - 1));
                i += 3; // Move past the two digits and the '#'
            } else {
                int num = str.charAt(i) - '0';
                result.append((char) ('a' + num - 1));
                i++; // Move to the next character
            }
        }
        return result.toString();
    }
}
