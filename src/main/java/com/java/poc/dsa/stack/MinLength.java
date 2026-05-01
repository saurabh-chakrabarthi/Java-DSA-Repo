package com.java.poc.dsa.stack;

import java.util.Stack;

public class MinLength {
    public static void main(String[] args) {
        String str = "ABCDRT";
        System.out.println("Min Size is : "+minLength(str));
    }

    public static int minLength(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for(char c : chars){
            if(stack.size() > 0 && ((c == 'B' && stack.peek() == 'A')
                    || (c == 'D' && stack.peek() == 'C'))){
                stack.pop();
            }else{
                stack.push(c);
            }
        }
        System.out.println(stack);
        return stack.size();
    }
}
