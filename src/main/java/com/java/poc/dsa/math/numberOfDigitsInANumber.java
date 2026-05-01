package com.java.poc.dsa.math;

public class numberOfDigitsInANumber {
  public static void main(String[] args) {

    findDigits(100);
  }

  private static void findDigits(Integer num){
    int count = 1;
    count = count * num/10;
  }
}
