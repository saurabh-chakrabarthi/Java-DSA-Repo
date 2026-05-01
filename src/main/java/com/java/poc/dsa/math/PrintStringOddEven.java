package com.java.poc.dsa.math;

import java.util.Scanner;

public class PrintStringOddEven {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            char[] strArray = str.toCharArray();
            StringBuilder strEven = new StringBuilder();
            StringBuilder strOdd = new StringBuilder();

            for (int i = 0; i < strArray.length; i++) {
                if (i % 2 == 0) {
                    strEven.append(strArray[i]);
                } else {
                    strOdd.append(strArray[i]);
                }
            }

            System.out.println(strEven.toString() + " " + strOdd.toString());
        }
    }
}
