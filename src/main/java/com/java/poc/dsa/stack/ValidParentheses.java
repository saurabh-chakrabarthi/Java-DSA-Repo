package com.java.poc.dsa.stack;

import java.util.Stack;

/**
 * 20. Valid Parentheses
 * Solved
 * Easy
 * Topics
 * Companies
 * Hint
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 *
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Every close bracket has a corresponding open bracket of the same type.
 *
 *
 * Example 1:
 *
 * Input: s = "()"
 *
 * Output: true
 *
 * Example 2:
 *
 * Input: s = "()[]{}"
 *
 * Output: true
 *
 * Example 3:
 *
 * Input: s = "(]"
 *
 * Output: false
 *
 * Example 4:
 *
 * Input: s = "([])"
 *
 * Output: true
 *
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 104
 * s consists of parentheses only '()[]{}'.
 */
public class ValidParentheses {
    public static void main(String[] args) {
        String str = "{[]}";
        System.out.println(isValid(str));
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if ((c == ')' && (stack.isEmpty() || stack.pop() != '('))
                    || (c == '}' && (stack.isEmpty() || stack.pop() != '{'))
                    || (c == ']' && (stack.isEmpty() || stack.pop() != '['))) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
