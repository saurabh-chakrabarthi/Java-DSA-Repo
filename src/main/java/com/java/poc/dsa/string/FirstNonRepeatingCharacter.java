package com.java.poc.dsa.string;

import java.util.LinkedHashMap;
import java.util.Map;

public class FirstNonRepeatingCharacter {
    public static void main(String[] args) {
        String str = "swiss";
        System.out.println("First non repeating character is : "+findFirstNonRepeatingCharacter(str));
    }

    private static Character findFirstNonRepeatingCharacter(String str) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        char[] chars = str.toCharArray();

        for(Character c : chars){
            if(map.containsKey(c)){
                int occurance = map.get(c);
                map.put(c,occurance+1);
            }else{
                map.put(c,1);
            }
        }
        System.out.println(map.toString());
        for(Character c : map.keySet()){
            if(map.get(c) == 1){
                return c;
            }
        }
        return null;
    }
}
