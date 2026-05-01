package com.java.poc.dsa.string.substring;

public class SubStringOfSetThreeV2 {
    public static void main(String[] args) {
        String str = "abcabcabc";
        System.out.println("Number of good substrings : "+countGoodSubstrings(str));
    }

    public static int countGoodSubstrings(String s) {
        int result = 0;
        for(int i=1;i<s.length()-1;i++){
            if(isValid(s,i)){
                result++;
            }
        }
        return result;
    }

    private static boolean isValid(String s, int i){
        return s.charAt(i-1) != s.charAt(i) &&
                s.charAt(i+1) != s.charAt(i) &&
                s.charAt(i+1) != s.charAt(i-1);
    }
}
