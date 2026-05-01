package com.java.poc.dsa.math;

import java.util.ArrayList;
import java.util.List;

public class factorsOfNumber {
  public static void main(String[] args) {
    int num = 9;
    calculate_factors(num);
  }

  private static void calculate_factors(int num){
    List<Integer> listOfFactors = new ArrayList<>();
    for(int i=1; i<=num; i++){
      if(num%i ==0){
        listOfFactors.add(i);
      }
    }
    System.out.println("The factors of "+num+" are : "+listOfFactors.toString());
  }
}
