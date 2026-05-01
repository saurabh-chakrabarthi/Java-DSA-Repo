package com.java.poc.curatedPracticeList.array_string;

/**
 * 1071. Greatest Common Divisor of Strings
 * Easy
 * Topics
 * Companies
 * Hint
 * For two strings s and t, we say "t divides s" if and only if s = t + t + t + ... + t + t (i.e., t is concatenated with itself one or more times).
 *
 * Given two strings str1 and str2, return the largest string x such that x divides both str1 and str2.
 *
 *
 *
 * Example 1:
 *
 * Input: str1 = "ABCABC", str2 = "ABC"
 * Output: "ABC"
 * Example 2:
 *
 * Input: str1 = "ABABAB", str2 = "ABAB"
 * Output: "AB"
 * Example 3:
 *
 * Input: str1 = "LEET", str2 = "CODE"
 * Output: ""
 *
 *
 * Constraints:
 *
 * 1 <= str1.length, str2.length <= 1000
 * str1 and str2 consist of English uppercase letters.
 */
public class GreatestCommonDivisorOfStrings {

    public String gcdOfStrings(String firstString, String secondString) {
        if (!isConcatenationEqual(firstString, secondString)) {
            return "";
        }

        int commonGCD = computeGCD(firstString.length(), secondString.length());
        return firstString.substring(0, commonGCD);
    }

    private boolean isConcatenationEqual(String first, String second) {
        return (first + second).equals(second + first);
    }

    private int computeGCD(int num1, int num2) {
        return (num2 == 0) ? num1 : computeGCD(num2, num1 % num2);
    }

    public static void main(String[] args) {
        GreatestCommonDivisorOfStrings solution = new GreatestCommonDivisorOfStrings();

        assert solution.gcdOfStrings("ABCABC", "ABC").equals("ABC") : "Test case 1 failed";
        assert solution.gcdOfStrings("ABABAB", "ABAB").equals("AB") : "Test case 2 failed";
        assert solution.gcdOfStrings("LEET", "CODE").equals("") : "Test case 3 failed";

        System.out.println("All tests passed!");
    }
}
