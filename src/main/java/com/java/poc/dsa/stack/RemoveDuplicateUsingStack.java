package com.java.poc.dsa.stack;

import java.util.Stack;

public class RemoveDuplicateUsingStack {
    public static void main(String[] args) {
        String str = "abbacd";
        System.out.println("DeDuped string : "+removeDupes(str));

    }

    private static String removeDupes(String str) {
        Stack<Character> stack = new Stack<>();
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c :chars){
            if(!stack.isEmpty() && stack.peek() == c){
                stack.pop();
            }else{
                stack.push(c);
            }
        }

        for(char c : stack){
            sb.append(c);
        }

        return sb.toString();
    }
}
