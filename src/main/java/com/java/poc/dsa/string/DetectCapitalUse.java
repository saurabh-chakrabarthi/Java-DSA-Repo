package com.java.poc.dsa.string;

/**
 * 520. Detect Capital
 * Easy
 * 3.1K
 * 440
 * Companies
 * We define the usage of capitals in a word to be right when one of the following cases holds:
 *
 * All letters in this word are capitals, like "USA".
 * All letters in this word are not capitals, like "leetcode".
 * Only the first letter in this word is capital, like "Google".
 * Given a string word, return true if the usage of capitals in it is right.
 *
 *
 *
 * Example 1:
 *
 * Input: word = "USA"
 * Output: true
 * Example 2:
 *
 * Input: word = "FlaG"
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= word.length <= 100
 * word consists of lowercase and uppercase English letters.
 */
public class DetectCapitalUse {

    public static void main(String[] args) {
        String str = "Spiderman";
        System.out.println(" Is the String Capital ? "+detectCapitalUse(str));
    }

    public static boolean detectCapitalUse(String word) {
        int numberOfCaps = 0;
        for(int i = 0; i< word.length(); i++){
            if(Character.isUpperCase(word.charAt(i))){
                numberOfCaps ++;
            }
        }
        if(numberOfCaps == 0 || numberOfCaps == word.length()){
            return true;
        }
        if(numberOfCaps == 1 && Character.isUpperCase(word.charAt(0))){
            return true;
        }
        return false;
    }
}
