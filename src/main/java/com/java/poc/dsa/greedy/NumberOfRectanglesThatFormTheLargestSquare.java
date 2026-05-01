package com.java.poc.dsa.greedy;

import java.util.HashMap;
import java.util.Map;

public class NumberOfRectanglesThatFormTheLargestSquare {
    public static void main(String[] args) {
        int[][] rectangles = new int[][]{{5,8}, {3,9}, {3,12}};
        System.out.println("Count number of good rectangles using HashMap : " + countGoodRectanglesUsingHashMap(rectangles));
        System.out.println("Count number of good rectangles without using HashMap: " + countGoodRectanglesWithoutUsingHashMap(rectangles));
    }

    public static int countGoodRectanglesUsingHashMap(int[][] rectangles) {
        Map<Integer, Integer> lengthMap = new HashMap<>();
        int largestSide = 0;
        for (int[] subArray : rectangles) {
            int shortestSide = Math.min(subArray[0], subArray[1]);
            if (lengthMap.containsKey(shortestSide)) {
                int count = lengthMap.get(shortestSide);
                lengthMap.put(shortestSide, count + 1);
            } else {
                lengthMap.put(shortestSide, 1);
            }
        }

        for (Integer i : lengthMap.keySet()) {
            if(i > largestSide){
                largestSide = i;
            }
        }
        return lengthMap.get(largestSide);
    }

    public static int countGoodRectanglesWithoutUsingHashMap(int[][] rectangles) {
        int count = 0;
        int max = 0;
        for (int[] subArray : rectangles) {
            int shortestSide = Math.min(subArray[0], subArray[1]);
            if (shortestSide > max) {
                count = 1;
                max = shortestSide;
            } else if(shortestSide == max){
                count ++;
            }
        }

        return count;
    }
}
