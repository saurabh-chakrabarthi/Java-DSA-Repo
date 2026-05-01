package com.java.poc.dsa.basics;

import java.util.*;

public class BasicCodeSnippets {
    public static void main(String[] args) {
        //.length is a field, not a method, so no parentheses.
        int[] arr = {1, 2, 3, 4};
        System.out.println(arr.length);  // 4

        String[] names = {"Alice", "Bob"};
        System.out.println(names.length);  // 2

        // 2D arrays
        int[][] matrix = new int[3][4];
        System.out.println(matrix.length);     // 3 (rows)
        System.out.println(matrix[0].length);

        //Used for String objects only
        String str = "Hello World";
        System.out.println(str.length());  // 11

        String empty = "";
        System.out.println(empty.length());  // 0

        // Common in problems
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
        }

        // Used for collection classess
        List<Integer> list = new ArrayList<>();
        list.add(1);
        System.out.println(list.size());  // 1

        // Sets
        Set<String> set = new HashSet<>();
        set.add("hello");
        System.out.println(set.size());  // 1

        // Maps
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 1);
        System.out.println(map.size());  // 1

        // Stacks, Queues, etc.
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();
    }
}
