package com.java.poc.common_patterns;

public class TwoDimensionArrays {
    public static void main(String[] args) {
        // Declare and initialize a 2D array
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // Get the number of rows
        int rows = matrix.length; // This gives the number of rows (3 in this case)

        // Get the number of columns in the first row
        int cols = matrix[0].length; // This gives the number of columns in the first row (3 in this case)

        // Sum of each row
        for (int i = 0; i < rows; i++) {
            int rowSum = 0;
            for (int j = 0; j < cols; j++) {
                rowSum += matrix[i][j]; // Access each element in the row
            }
            System.out.println("Sum of row " + i + " = " + rowSum);
        }

        // Sum of each column
        for (int j = 0; j < cols; j++) {
            int colSum = 0;
            for (int i = 0; i < rows; i++) {
                colSum += matrix[i][j]; // Access each element in the column
            }
            System.out.println("Sum of column " + j + " = " + colSum);
        }
    }
}
