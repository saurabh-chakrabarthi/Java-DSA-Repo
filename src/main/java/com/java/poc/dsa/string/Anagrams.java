package com.java.poc.dsa.string;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Anagrams {
    public static void main(String[] args) {
        String str1 = "tea";
        String str2 = "eat";
        check1(str1,str2);
        check2(str1,str2);
    }

    private static void check1(String str1, String str2) {
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);

        if(Arrays.equals(chars1, chars2)){
            System.out.println("The strings are Anagrams");
        } else{
            System.out.println("The strings are not Anagrams");
        }
    }

    private static void check2(String str1, String str2) {
        Set<Character> set = new HashSet();
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        Arrays.sort(chars1);
        for(char c : chars1){
            set.add(c);
        }
        Arrays.sort(chars2);
        for(char c : chars2){
            if(!set.isEmpty() && set.contains(c)){
                set.remove(c);
            }else{
                set.add(c);
            }
        }

        if(set.isEmpty()){
            System.out.println("The strings are Anagrams");
        } else{
            System.out.println("The strings are not Anagrams");
        }
    }
}
