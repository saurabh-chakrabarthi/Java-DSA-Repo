package com.java.poc.dsa.stack;

import java.util.Stack;

public class LongestValidParentheses {
    public static void main(String[] args) {
        String str = "(()";
        System.out.println(longestValidParentheses(str));
    }

    public static int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int ans = 0;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '('){
                stack.push(i);
            }
            else {
                stack.pop();
                if (stack.isEmpty()){
                    stack.push(i);
                }
                else{
                    ans = Math.max(ans, i - stack.peek());
                }
            }
        return ans;
    }
}
