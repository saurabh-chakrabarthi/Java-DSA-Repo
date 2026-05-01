package com.java.poc.dsa.string;

import java.util.Stack;

/**
 * 917. Reverse Only Letters
 * Easy
 * 1.9K
 * 64
 * Companies
 * Given a string s, reverse the string according to the following rules:
 *
 * All the characters that are not English letters remain in the same position.
 * All the English letters (lowercase or uppercase) should be reversed.
 * Return s after reversing it.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "ab-cd"
 * Output: "dc-ba"
 * Example 2:
 *
 * Input: s = "a-bC-dEf-ghIj"
 * Output: "j-Ih-gfE-dCba"
 * Example 3:
 *
 * Input: s = "Test1ng-Leet=code-Q!"
 * Output: "Qedo1ct-eeLg=ntse-T!"
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 100
 * s consists of characters with ASCII values in the range [33, 122].
 * s does not contain '\"' or '\\'.
 */
public class ReverseOnlyLetters {
    public static void main(String[] args) {
        String str = "ab-cd";
        System.out.println("Input String is : "+str);
        System.out.println("Reversed String is : "+reverseOnlyLetters(str));
    }
    private static String reverseOnlyLetters(String str) {
        Stack<Character> letters = new Stack<>();
        for(char c : str.toCharArray()){
            if(Character.isLetter(c)){
                letters.push(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        for(char c : str.toCharArray()){
            if(Character.isLetter(c)){
                sb.append(letters.pop());
            }else{
                sb.append(c);
            }
        }


           return sb.toString();
    }
}
