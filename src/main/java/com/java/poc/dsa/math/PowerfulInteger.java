package com.java.poc.dsa.math;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PowerfulInteger {

    private static List<Integer> powerfulIntegers(int x, int y, int bound) {
        double sum = 0;
        List<Integer> output = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                sum = Math.pow(x, i) + Math.pow(y, j);
                if (sum <= bound && !output.contains((int) sum)) {
                    output.add((int) sum);
                }
            }
        }
        output.sort(Comparator.comparing(Integer::valueOf));
        return output;
    }

    public static void main(String[] args) {
        System.out.println(powerfulIntegers(3, 5, 15));
    }
}
