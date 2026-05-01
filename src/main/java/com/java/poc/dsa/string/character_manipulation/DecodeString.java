package com.java.poc.dsa.string.character_manipulation;

/**
 * Leetcode Problem 394: Decode String
 * Given an encoded string, return its decoded string.
 *
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 *
 * You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].
 *
 * The test cases are generated so that the length of the output will never exceed 105.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "3[a]2[bc]"
 * Output: "aaabcbc"
 * Example 2:
 *
 * Input: s = "3[a2[c]]"
 * Output: "accaccacc"
 * Example 3:
 *
 * Input: s = "2[abc]3[cd]ef"
 * Output: "abcabccdcdcdef"
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 30
 * s consists of lowercase English letters, digits, and square brackets '[]'.
 * s is guaranteed to be a valid input.
 * All the integers in s are in the range [1, 300].
 */
public class DecodeString {
    public static void main(String[] args) {
        String str = "3[a2[c]]"; // accaccacc
        System.out.println(decodeString(str)); // Output: "accaccacc"
    }

    private static String decodeString(String str) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            char ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                int count = 0;
                while (Character.isDigit(str.charAt(i))) {
                    count = count * 10 + (str.charAt(i) - '0');
                    i++;
                }
                i++; // skip the opening bracket '['
                int start = i;
                int bracketCount = 1;
                while (bracketCount > 0) {
                    if (str.charAt(i) == '[') bracketCount++;
                    else if (str.charAt(i) == ']') bracketCount--;
                    i++;
                }
                String decodedString = decodeString(str.substring(start, i - 1));
                for (int j = 0; j < count; j++) {
                    result.append(decodedString);
                }
            } else {
                result.append(ch);
                i++;
            }
        }
        return result.toString();
    }
}
