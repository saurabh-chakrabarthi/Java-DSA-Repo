package com.java.poc.dsa.string;

public class PrintStaircase {

    public static void main(String[] args) {

        printStaircase(6);
    }

    private static void printStaircase(int n) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if ((i + j) > n) {
                    System.out.print("#");
                } else {
                    System.out.print("");
                }

            }
            System.out.println();

        }
    }
}
