package com.java.poc.dsa.string;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * 451. Sort Characters By Frequency
 * Medium
 * 6.3K
 * 224
 * Companies
 * Given a string s, sort it in decreasing order based on the frequency of the characters. The frequency of a character is the number of times it appears in the string.
 *
 * Return the sorted string. If there are multiple answers, return any of them.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "tree"
 * Output: "eert"
 * Explanation: 'e' appears twice while 'r' and 't' both appear once.
 * So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
 * Example 2:
 *
 * Input: s = "cccaaa"
 * Output: "aaaccc"
 * Explanation: Both 'c' and 'a' appear three times, so both "cccaaa" and "aaaccc" are valid answers.
 * Note that "cacaca" is incorrect, as the same characters must be together.
 * Example 3:
 *
 * Input: s = "Aabb"
 * Output: "bbAa"
 * Explanation: "bbaA" is also a valid answer, but "Aabb" is incorrect.
 * Note that 'A' and 'a' are treated as two different characters.
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 5 * 105
 * s consists of uppercase and lowercase English letters and digits.
 */
public class SortCharacterByFrequency {

    public static void main(String[] args) {
        String str = "tree";
        System.out.println("The original String is : "+str);
        System.out.println("The sorted String is : "+frequencySort(str));
    }

    public static String frequencySort(String s) {
        Map<Character,Integer> charMap = new TreeMap<>();
        char[] chars = s.toCharArray();
        for(Character c : chars){
            charMap.put(c, charMap.getOrDefault(c,0)+1);
        }

        PriorityQueue<Character> maxHeap = new PriorityQueue<>((a, b) -> charMap.get(b) - charMap.get(a));
        maxHeap.addAll(charMap.keySet());

        StringBuilder sb = new StringBuilder();
        while(!maxHeap.isEmpty()){
            char current = maxHeap.remove();
            for(int i=0;i<charMap.get(current);i++){
                sb.append(current);
            }
        }

        return sb.toString();
    }
}
