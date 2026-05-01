package com.java.poc.dsa.stack;

import java.util.Stack;

public class LinuxOperation {
    public static void main(String[] args) {
        String str = "/../";
        System.out.println(simplifyPath(str));
    }

    private static String simplifyPath(String str) {
        String[] arr = str.split("/");
        Stack<String> stack = new Stack<>();
        for (String s : arr) {
            if (s.isEmpty() || s.equals(".")) {
                continue;
            } else if (s.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                stack.push(s);
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.insert(0, "/" + stack.pop());
        }
        return sb.isEmpty() ? "/" : sb.toString();
    }


}
