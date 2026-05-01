package com.java.poc.dsa.string.character_manipulation;

public class StringToInteger {
    public static void main(String[] args) {
        String str = "1234";
        convertToInt(str);
    }

    private static void convertToInt(String str) {
        System.out.println("String to be converted : "+str);
        char[] chars = str.toCharArray();
        int sum = 0;
        for(char c : chars){
            int num = (int) c - '0';
            sum = (sum * 10) + num;
        }
        System.out.println("converted integer is : "+sum);
    }
}
