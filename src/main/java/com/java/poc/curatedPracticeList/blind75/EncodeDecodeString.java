package com.java.poc.curatedPracticeList.blind75;

import java.util.ArrayList;
import java.util.List;

/**
 * 659 · Encode and Decode Strings
 * Algorithms
 * Medium
 * Accepted Rate
 * 64%
 *
 * Description
 * Solution60
 * Notes
 * Discuss99+
 * Leaderboard
 * Record
 * Description
 * Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and is decoded back to the original list of strings.
 *
 * Please implement encode and decode
 *
 * Because the string may contain any of the 256 legal ASCII characters, your algorithm must be able to handle any character that may appear
 *
 * Do not rely on any libraries, the purpose of this problem is to implement the "encode" and "decode" algorithms on your own
 *
 * Example
 * Example1
 *
 * Input: ["lint","code","love","you"]
 * Output: ["lint","code","love","you"]
 * Explanation:
 * One possible encode method is: "lint:;code:;love:;you"
 * Example2
 *
 * Input: ["we", "say", ":", "yes"]
 * Output: ["we", "say", ":", "yes"]
 * Explanation:
 * One possible encode method is: "we:;say:;:::;yes"
 */
public class EncodeDecodeString {

    public static void main(String[] args) {
        List<String> strList = new ArrayList<>();
        strList.add("lint");
        strList.add("code");
        strList.add("love");
        strList.add("you");

        System.out.println("Encoded list is : "+encode(strList));

        System.out.println("Decoded list is :"+decode(encode(strList)));

    }

    public static String encode(List<String> strs) {
        StringBuilder encodedString = new StringBuilder();
        for (String str : strs) {
            encodedString.append(str.length()).append("#").append(str);
        }
        return encodedString.toString();
    }

    public static List<String> decode(String str) {
        List<String> list = new ArrayList<>();
        int i=0;
        while(i<str.length()){
            int delimiter = str.indexOf('#',i);
            int size = Integer.parseInt(str.substring(i,delimiter));
            i = delimiter+size+1;
            list.add(str.substring(delimiter+1,i));
        }
        return list;
    }

}
