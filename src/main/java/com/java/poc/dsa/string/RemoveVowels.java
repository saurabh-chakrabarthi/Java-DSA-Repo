package com.java.poc.dsa.string;

import java.util.ArrayList;
import java.util.List;

public class RemoveVowels {
    public static void main(String[] args) {
        String str = "Hello World! How are you doing ?";
        removeVowels(str);
    }

    private static void removeVowels(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        List<Character> listOfVowels = new ArrayList<Character>();
        listOfVowels.add('a');
        listOfVowels.add('e');
        listOfVowels.add('i');
        listOfVowels.add('o');
        listOfVowels.add('u');
        listOfVowels.add('A');
        listOfVowels.add('E');
        listOfVowels.add('I');
        listOfVowels.add('O');
        listOfVowels.add('U');

        for(char c : chars){
            if(!listOfVowels.contains(c)){
                sb.append(c);
            }
        }

        System.out.println(sb.toString());
    }

}
