package com.java.poc.dsa.greedy;

/**
 * LeetCode 1518. Water Bottles
 * Easy
 * Companies
 * Given numBottles water bottles, you can exchange numExchange empty water bottles for one full water bottle.
 * The operation of drinking a full water bottle turns it into an empty bottle.
 * Return the maximum number of water bottles you can drink.
 *
 */
public class MaximunWaterBottlesToDrink {
    public static void main(String[] args) {
        int numBottles = 3;
        int numExchange = 4;
        System.out.println("Number of water bottles which you can drink : "+numWaterBottles(numBottles,numExchange));
    }

    public static int numWaterBottles(int numBottles, int numExchange) {
        int drink = numBottles;
        int numFull;

        while(numBottles >= numExchange) {
            numFull = numBottles/numExchange;
            drink = drink+ numFull;
            numBottles = numFull+(numBottles%numExchange);
        }
        return drink;
    }
}
