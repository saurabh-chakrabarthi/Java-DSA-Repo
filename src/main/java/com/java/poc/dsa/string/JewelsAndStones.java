package com.java.poc.dsa.string;

import java.util.HashMap;
import java.util.Map;

public class JewelsAndStones {

    public static void main(String[] args) {
        String jewels = "a";
        String stones = "aabbcc";
        System.out.println(numJewelsInStones(jewels,stones));
    }
    public static int numJewelsInStones(String jewels, String stones) {
        char[] chars = stones.toCharArray();
        Map<Character,Integer> charMap = new HashMap<>();
        int sum = 0;

        for(char c : chars){
            if(charMap.containsKey(c)){
                int occurance = charMap.get(c);
                charMap.put(c,occurance+1);
            }else{
                charMap.put(c,1);
            }
        }

        char[] chars_jewels = jewels.toCharArray();
        for(char c : chars_jewels){
            if(charMap.containsKey(c)){
                sum = sum + charMap.get(c);
            }
        }

        return sum;

    }
}
