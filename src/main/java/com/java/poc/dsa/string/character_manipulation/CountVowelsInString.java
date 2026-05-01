package com.java.poc.dsa.string.character_manipulation;

import java.util.Locale;

public class CountVowelsInString {
    public static void main(String[] args) {
        String str = "Comic books are fun";    //7 vowels
        calculateVowels(str);
    }

    private static void calculateVowels(String str) {
        int count = 0;
        char[] charArr = str.toLowerCase(Locale.ROOT).toCharArray();
        for(char c : charArr){
            if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
                count ++;
            }
        }
        System.out.println("The string : "+str+" has "+count+" vowels");
    }
}
