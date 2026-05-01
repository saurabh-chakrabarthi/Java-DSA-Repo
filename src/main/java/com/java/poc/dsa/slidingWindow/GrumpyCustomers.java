package com.java.poc.dsa.slidingWindow;

/*
Leetcode 1052
 */
public class GrumpyCustomers {

    public static void main(String[] args) {
        int[] customers = new int[]{1,0,1,2,1,1,7,5};
        int[] grumpy = new int[]{0,1,0,1,0,1,0,1};
        int minutes = 3;
        System.out.println("Max satisfied customers : "+maxSatisfied(customers,grumpy,minutes));
    }

    public static int maxSatisfied(int[] customers, int[] grumpy, int minutes) {

        // find the number of satisfied customers without applying the secret technique
        int satisfiedCustomers = 0;
        for (int i = 0; i < customers.length; i++) {
            if (grumpy[i] == 0) {
                satisfiedCustomers += customers[i];
            }
        }

        int max = 0;
        int currSum = 0;
        int windowStart = 0;

        for (int windowEnd = 0; windowEnd < customers.length; windowEnd++) {
            if (grumpy[windowEnd] == 1) {
                currSum += customers[windowEnd];
            }

            if (windowEnd - windowStart + 1 >= minutes) {
                max = Math.max(max, currSum);
                if (grumpy[windowStart] == 1) {
                    currSum -= customers[windowStart];
                }
                windowStart++;
            }
        }

        return satisfiedCustomers + max;
    }
}
