package com.java.poc.dsa.math;

public class CheckPrimes {

    public static void main(String[] args) {

        int num = 100;
        isPrime(num);
    }

    private static void isPrime(int num) {
        int count = 0;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if(num%i == 0){
                count ++;
            }
        }

        if(count > 1){
            System.out.println(num+" is not a prime");
        }else{
            System.out.println(num+" is a prime");
        }
    }
}
