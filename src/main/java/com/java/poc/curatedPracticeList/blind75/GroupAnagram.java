package com.java.poc.curatedPracticeList.blind75;

import java.util.*;

/**
 * 49. Group Anagrams
 * Solved
 * Medium
 * Topics
 * Companies
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
 *
 *
 *
 * Example 1:
 *
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * Example 2:
 *
 * Input: strs = [""]
 * Output: [[""]]
 * Example 3:
 *
 * Input: strs = ["a"]
 * Output: [["a"]]
 *
 *
 * Constraints:
 *
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] consists of lowercase English letters.
 */
public class GroupAnagram {

    public static void main(String[] args) {
       String[] strs = new String[]{"eat","tea","tan","ate","nat","bat"};
        System.out.println(groupAnagrams(strs));
    }
    public static List<List<String>> groupAnagrams(String[] strs) {
        List<String> strList = null;
        List<List<String>> finalList = new ArrayList<>();
        Map<String, List<String>> strMap = new HashMap<>();
        StringBuilder sb = null;
        for(String str : strs){
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            sb = new StringBuilder();

            for(Character c : chars){
                sb.append(c.toString());
            }

            if(strMap.containsKey(sb.toString())){
                strMap.get(sb.toString()).add(str);
            }else{
                strList = new ArrayList<>();
                strList.add(str);

                strMap.put(sb.toString(),strList);
            }
        }

        for(String key : strMap.keySet()){
            finalList.add(strMap.get(key));
        }

        return finalList;
    }

}
