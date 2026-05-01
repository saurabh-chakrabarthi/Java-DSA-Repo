package com.java.poc.dsa.math;

public class primeNumbers {
  public static void main(String[] args) {
    int num = 3;
    calculatePrime(num);
  }

  private static void calculatePrime(int num){
    int count = 0;
    for(int i=2;i<=num;i++){
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
