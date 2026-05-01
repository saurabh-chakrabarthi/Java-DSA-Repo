package com.java.poc.common_patterns;

public class JaggedArrays {
    public static void main(String[] args) {
        // Declare and initialize a jagged 2D array
        int[][] jaggedArray = {
                {1, 2, 3},       // Row 0 has 3 elements
                {4, 5},          // Row 1 has 2 elements
                {6, 7, 8, 9}     // Row 2 has 4 elements
        };

        // Loop through the jagged array
        for (int i = 0; i < jaggedArray.length; i++) {
            System.out.print("Row " + i + ": ");
            for (int j = 0; j < jaggedArray[i].length; j++) {
                System.out.print(jaggedArray[i][j] + " ");
            }
            System.out.println(); // Move to the next line after each row
        }
    }
}
