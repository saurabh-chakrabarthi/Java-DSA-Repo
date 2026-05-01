package com.java.poc.dsa.math;

public class PowerOfNumber {
  public static void main(String[] args) {
    int x= 16;
    int y = 4;
    power(x,y);
    powerOfNum(x,y);
  }

  private static void power(int x, int y){
    int pow = 1;
    while(pow<x){
      pow = pow * y;
    }
     if(pow ==x){
      System.out.println(x+" is a power of "+y);
    }else{
      System.out.println(x+" is not a power of "+y);
    }
  }

  private static void powerOfNum(int x, int y){
    int pow = x;
    while(x>0 && y>0 && x%y == 0){
      x = x / y;
    }
    if(x ==1){
      System.out.println(pow+" is a power of "+y);
    }else{
      System.out.println(pow+" is not a power of "+y);
    }
  }
}
