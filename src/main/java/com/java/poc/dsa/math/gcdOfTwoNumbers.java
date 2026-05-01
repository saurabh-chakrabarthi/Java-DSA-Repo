package com.java.poc.dsa.math;

public class gcdOfTwoNumbers {
  public static void main(String[] args) {
    int a = 36;
    int b = 600;
    System.out.println("GCD of " + a + ", " + b + " is : " + gcd(a, b));
  }

  private static int gcd(int a, int b) {
    if (b == 0) {
      return a;
    } else {
      return gcd(b, a % b);
    }
  }
}
