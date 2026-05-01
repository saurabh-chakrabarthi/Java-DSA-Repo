package com.java.poc.curatedPracticeList.blind75;

/**
 * 125. Valid Palindrome
 * Solved
 * Easy
 * Topics
 * Companies
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters and numbers.
 *
 * Given a string s, return true if it is a palindrome, or false otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "A man, a plan, a canal: Panama"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 * Example 2:
 *
 * Input: s = "race a car"
 * Output: false
 * Explanation: "raceacar" is not a palindrome.
 * Example 3:
 *
 * Input: s = " "
 * Output: true
 * Explanation: s is an empty string "" after removing non-alphanumeric characters.
 * Since an empty string reads the same forward and backward, it is a palindrome.
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 2 * 105
 * s consists only of printable ASCII characters.
 */
public class ValidPalindrome {

    public static void main(String[] args) {
        String str = "race a car";
        System.out.println("is "+str+" a palindrome ? : "+isPalindrome1(str));
        System.out.println("is "+str+" a palindrome ? : "+isPalindrome2(str));
    }

    private static boolean isPalindrome1(String str) {

        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (Character c : chars) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }

        int left = 0;
        int right = sb.length() - 1;

        while (left < right) {
            if (sb.charAt(left) == sb.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }

        return true;
    }

    private static boolean isPalindrome2(String str) {
        int i = 0;
        int j = str.length() - 1;
        while (i < j) {

            char start = str.charAt(i);
            char end = str.charAt(j);

            if (!Character.isLetterOrDigit(start)) {
                i++;
                continue;
            }

            if (!Character.isLetterOrDigit(end)) {
                j--;
                continue;
            }

            if (Character.toLowerCase(start) != Character.toLowerCase(end)) {
                return false;
            }

            i++;
            j--;
        }

        return true;
    }
}
