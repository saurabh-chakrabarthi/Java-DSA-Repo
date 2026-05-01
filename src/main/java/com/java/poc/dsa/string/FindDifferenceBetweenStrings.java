package com.java.poc.dsa.string;

public class FindDifferenceBetweenStrings {

    public static void main(String[] args) {
        int s_val = findStringSum("abc");
        int t_val = findStringSum("abcd");

        System.out.println((char)(t_val-s_val));
    }

    private static int findStringSum(String str){
        int sum = 0;
        char[] chars = str.toCharArray();
        for(char c : chars){
            sum+= c;
        }

        return sum;
    }
}
