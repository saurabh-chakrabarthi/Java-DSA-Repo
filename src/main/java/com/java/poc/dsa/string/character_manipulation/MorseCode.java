package com.java.poc.dsa.string.character_manipulation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MorseCode {
    public static void main(String[] args) {
        String[] words = new String[]{"gin", "zen", "gig", "msg"};
        System.out.println(uniqueMorseRepresentations(words));
    }

    public static int uniqueMorseRepresentations(String[] words) {
        String[] morseCodes = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};

        char[] alphabets = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        HashSet<String> set = new HashSet<>();
        StringBuilder sb = null;
        Map<Character, String> map = new HashMap<Character, String>();
        for (int i = 0; i<26;i++) {
                map.put(alphabets[i], morseCodes[i]);
        }
        System.out.println(map.toString());
        for (String str : words) {
            sb = new StringBuilder();
            char[] chars = str.toCharArray();
            for (char c : chars) {
                String code = map.get(c);
                sb.append(code);
            }
            set.add(sb.toString());
            System.out.println(set.toString());
        }
        return set.size();
    }
}
