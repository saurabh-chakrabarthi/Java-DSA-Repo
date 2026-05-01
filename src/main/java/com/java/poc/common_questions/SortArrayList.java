package com.java.poc.common_questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SortArrayList {

    public static void main(String[] args) {
        // Create an ArrayList of Strings
        ArrayList<String> list = new ArrayList<>();
        list.add("Banana");
        list.add("Apple");
        list.add("Mango");
        list.add("Orange");

        // Sort the ArrayList
        Collections.sort(list);

        // Sort the ArrayList based on the length of the strings
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        // Print the sorted list
        System.out.println(list);  // Output: [Apple, Banana, Mango, Orange]
    }
}
