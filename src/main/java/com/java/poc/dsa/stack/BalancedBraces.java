package com.java.poc.dsa.stack;

import java.util.Stack;

public class BalancedBraces {

    public static void main(String[] args) {
        System.out.println(isBraceBalanced("((()(())))"));
        System.out.println(isBraceBalanced("({[{]})"));
    }

    public static boolean isBraceBalanced(String braces) {
        Stack<Character> stack = new Stack<Character>();

        for (char c : braces.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if ((c == ')' && (stack.isEmpty() || stack.pop() != '(')) ||
                    (c == ']' && (stack.isEmpty() || stack.pop() != '[')) ||
                    (c == '}' && (stack.isEmpty() || stack.pop() != '{'))) {
                return false;
            }
        }

        return stack.isEmpty();
    }
}
