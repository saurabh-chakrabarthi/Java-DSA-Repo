package com.java.poc.dsa.greedy;

import java.util.Stack;

public class BalancedSubStrings {
    public static void main(String[] args) {
        String str = "LLRRLLRRRRLL";
        System.out.println("Number of balanced substrings via method 1 : " + countBalancedSubStrings(str));
        System.out.println("Number of balanced substrings via method 2 : " + balancedStringSplit(str));
    }

    private static int countBalancedSubStrings(String str) {
        Stack<Character> stack = new Stack<>();
        int count = 0;

        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (stack.empty() || stack.peek() == c) {
                stack.push(c);
            } else {
                stack.pop();
                if (stack.empty()) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int balancedStringSplit(String s) {
        int sum=0;
        int count=0;
        for(int i=0;i<s.length();i++){
            //Subtract 1 when L is discovered
            if(s.charAt(i)=='L'){
                sum=sum-1;
            }//Add 1 when R is discovered
            else{
                sum=sum+1;
            }
            //The Sum becomes 0 whenever equal number of L and R are detected
            if(sum==0){
                ++count;
            }
        }
        return count;
    }
}
