package com.java.poc.dsa.math;

public class Millionaire {
    static double amount = 0.01;
    static double targetAmount = 1000000.0;
    static double totalAmount = 0;
    static int days = 0;

    public static void main(String[] args) {

        calculateDate();
    }

    static void calculateDate() {
        if (totalAmount <= targetAmount) {
            amount = amount * 2;
            totalAmount = amount;
            days++;
            System.out.println("Day " + days + " , amount " + amount);
            calculateDate();
        } else {
            System.out.println("You will become a millionaire in " + days + " days !");
        }
    }
}
