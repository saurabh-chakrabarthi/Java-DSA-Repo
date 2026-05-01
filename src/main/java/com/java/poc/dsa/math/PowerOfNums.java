package com.java.poc.dsa.math;

public class PowerOfNums {

    public static void main(String[] args) {
        System.out.println(myPow(2,3));
    }

    public static double myPow(double x, int n) {

        if(n < 0){
            return (1/x) * myPow((1/x), -(n+1));
        }
        if(n == 0){
            return 1;
        }
        double val = myPow(x, n/2);
        if(n%2 == 0){

            return val * val;
        }
        return val * val * x;
    }
}
