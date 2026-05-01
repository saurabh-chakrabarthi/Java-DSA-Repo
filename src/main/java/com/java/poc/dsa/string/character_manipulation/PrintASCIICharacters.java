package com.java.poc.dsa.string.character_manipulation;

public class PrintASCIICharacters {
    public static void main(String[] args) {
        printASCIICharacters();
    }
    public static void printASCIICharacters() {
        for (int i = 0; i < 128; i++) {
            System.out.println("ASCII value of " + (char) i + " is: " + i);
        }
    }
}
