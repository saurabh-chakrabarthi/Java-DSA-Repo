package com.java.poc.curatedPracticeList.array_string;

/**
 * 345. Reverse Vowels of a String
 * Solved
 * Easy
 * Topics
 * Companies
 * Given a string s, reverse only all the vowels in the string and return it.
 *
 * The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both lower and upper cases, more than once.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "IceCreAm"
 *
 * Output: "AceCreIm"
 *
 * Explanation:
 *
 * The vowels in s are ['I', 'e', 'e', 'A']. On reversing the vowels, s becomes "AceCreIm".
 *
 * Example 2:
 *
 * Input: s = "leetcode"
 *
 * Output: "leotcede"
 *
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 3 * 105
 * s consist of printable ASCII characters.
 */
public class ReverseVowelsOfString {

    private boolean isVowel(char ch) {
        ch = Character.toLowerCase(ch);
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    public String reverseVowelsInString(String s) {
        if (s == null || s.isEmpty()) return s;

        char[] chars = s.toCharArray();
        int left = 0, right = s.length() - 1;

        while (left < right) {
            while (left < right && !isVowel(chars[left])) {
                left++;
            }

            while (left < right && !isVowel(chars[right])) {
                right--;
            }

            if (left < right) {
                char tempChar = chars[left];
                chars[left] = chars[right];
                chars[right] = tempChar;
                left++;
                right--;
            }
        }

        return new String(chars);
    }

    public static void main(String[] args) {

        ReverseVowelsOfString reverser = new ReverseVowelsOfString();

        assert reverser.reverseVowelsInString("hello").equals("holle") : "Test case 1 failed";
        assert reverser.reverseVowelsInString("leetcode").equals("leotcede") : "Test case 2 failed";

        System.out.println("All tests passed!");
    }


}
