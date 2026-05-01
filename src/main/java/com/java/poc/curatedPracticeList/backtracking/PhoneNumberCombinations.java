package com.java.poc.curatedPracticeList.backtracking;

import java.util.LinkedList;
import java.util.List;

/**
 * 17. Letter Combinations of a Phone Number
 * Solved
 * Medium
 * Topics
 * Companies
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
 *
 * A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 *
 *
 *
 *
 * Example 1:
 *
 * Input: digits = "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * Example 2:
 *
 * Input: digits = ""
 * Output: []
 * Example 3:
 *
 * Input: digits = "2"
 * Output: ["a","b","c"]
 *
 *
 * Constraints:
 *
 * 0 <= digits.length <= 4
 * digits[i] is a digit in the range ['2', '9'].
 */
public class PhoneNumberCombinations {

    public List<String> letterCombinations(String digits) {
        LinkedList<String> ans = new LinkedList<>();
        if (digits.isEmpty()) return ans;
        String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for (int i = 0; i < digits.length(); i++) {
            int x = Character.getNumericValue(digits.charAt(i));
            while (ans.peek().length() == i) {
                String t = ans.remove();
                for (char s : mapping[x].toCharArray())
                    ans.add(t + s);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        PhoneNumberCombinations combinations = new PhoneNumberCombinations();

        List<String> result1 = combinations.letterCombinations("23");
        List<String> expected1 = List.of("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf");
        assert result1.equals(expected1) : "Test case 1 failed";

        List<String> result2 = combinations.letterCombinations("");
        List<String> expected2 = List.of();
        assert result2.equals(expected2) : "Test case 2 failed";

        List<String> result3 = combinations.letterCombinations("2");
        List<String> expected3 = List.of("a", "b", "c");
        assert result3.equals(expected3) : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
