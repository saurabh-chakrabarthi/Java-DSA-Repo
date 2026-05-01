package com.java.poc.dsa.stack;

import java.util.Stack;

public class ReverseStringUsingStack {
    public static void main(String[] args) {
        String str = "hello world !!!";
        System.out.println("Reversed String is : "+reverseString(str));
    }

    public static String reverseString(String str) {
        Stack<Character> stack = new Stack<>();
        for (char c : str.toCharArray()) {
            stack.push(c);
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }
}
