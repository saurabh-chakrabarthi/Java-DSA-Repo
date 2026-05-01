package com.java.poc.dedupe;

import java.util.Arrays;
import java.util.*;

public class SetDiff {
    public static void main(String[] args) {
            // Initialize set1 and set2
            Set<Integer> followers = new HashSet<>(Arrays.asList(4,5));
            Set<Integer> following = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

            // Create a copy of set2
            Set<Integer> differenceSet = new HashSet<>(following);

            // Remove all elements in set1 from the copy of set2
            differenceSet.removeAll(followers);

            // Print the elements
            System.out.println("Items in set2 that don't exist in set1: " + differenceSet);

    }
}
