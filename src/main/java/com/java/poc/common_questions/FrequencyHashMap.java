package com.java.poc.common_questions;

import java.util.HashMap;

public class FrequencyHashMap {
    public static void main(String[] args) {
        // Create a HashMap to store character frequencies
        HashMap<Character, Integer> frequencyMap = new HashMap<>();
        String str = "programming";
        // Iterate through the string and update the frequencies
        for (char c : str.toCharArray()) {
            if (frequencyMap.containsKey(c)) {
                frequencyMap.put(c, frequencyMap.get(c) + 1);
            } else {
                frequencyMap.put(c, 1);
            }
        }
        System.out.println("Character Frequencies:"+frequencyMap);
    }
}
