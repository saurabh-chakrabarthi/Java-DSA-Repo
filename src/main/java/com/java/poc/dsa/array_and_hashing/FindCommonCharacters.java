package com.java.poc.dsa.array_and_hashing;

import java.util.ArrayList;
import java.util.List;

/*
Leetcode 1002 : Find Common Characters

Given a string array words, return an array of all characters that show up in all strings within the words (including duplicates). You may return the answer in any order.

Example 1:

Input: words = ["bella","label","roller"]
Output: ["e","l","l"]
Example 2:

Input: words = ["cool","lock","cook"]
Output: ["c","o"]


Constraints:

1 <= words.length <= 100
1 <= words[i].length <= 100
words[i] consists of lowercase English letters.
 */
public class FindCommonCharacters {

    public static void main(String[] args) {
        String[] words = new String[]{"hello","mellow","hollow","pillow"};
        System.out.println(commonChars(words));
    }

    public static List<String> commonChars(String[] words) {
        List<String> result = new ArrayList<>();

        for (char c = 'a'; c <= 'z'; c++){
            int minCount = Integer.MAX_VALUE;
            for (String s : words){
                int wordCount = 0;
                for (char cur_c : s.toCharArray()) {
                    if (cur_c == c) {
                        wordCount++;
                    }
                }
                minCount = Math.min(minCount, wordCount);
            }

            for (int i = 0; i < minCount; i++) {
                result.add("" + c);
            }
        }

        return result;
    }
}
