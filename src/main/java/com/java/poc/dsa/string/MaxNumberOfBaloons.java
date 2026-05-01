package com.java.poc.dsa.string;

import java.util.HashMap;
import java.util.Map;

/**
 * 1189. Maximum Number of Balloons
 * Easy
 * 1.3K
 * 77
 * Companies
 * Given a string text, you want to use the characters of text to form as many instances of the word "balloon" as possible.
 * <p>
 * You can use each character in text at most once. Return the maximum number of instances that can be formed.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * <p>
 * Input: text = "nlaebolko"
 * Output: 1
 * Example 2:
 * <p>
 * <p>
 * <p>
 * Input: text = "loonbalxballpoon"
 * Output: 2
 * Example 3:
 * <p>
 * Input: text = "leetcode"
 * Output: 0
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= text.length <= 104
 * text consists of lower case English letters only.
 */
public class MaxNumberOfBaloons {
    public static void main(String[] args) {
        String text = "loonbalxballpoon";
        System.out.println("Max number of Baloons : " + maxNumberOfBalloons(text));
    }

    public static int maxNumberOfBalloons(String text) {
        Map<Character, Integer> map = new HashMap<>();
        char[] chars = text.toCharArray();
        for (Character c : chars) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        return (Math.min(map.getOrDefault('b', 0), Math.min(map.getOrDefault('a', 0), Math.min(map.getOrDefault('l', 0) / 2, Math.min(map.getOrDefault('o', 0) / 2, map.getOrDefault('n', 0))))));
    }
}
