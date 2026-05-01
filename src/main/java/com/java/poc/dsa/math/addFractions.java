package com.java.poc.dsa.math;

public class addFractions {

  public static void main(String[] args) {
    int[] num = new int[]{1,1,1};
    int[] den = new int[]{2,2,2};
    add(num,den);
  }

  public static void add(int[] num, int[] den){
    int denom = 1;
    int nom = 0;
    for (int j : den) {
      denom = denom * j;
    }
    for(int i=0;i<num.length;i++){
      nom = nom + i*(denom/num[i]);
    }
    System.out.println("answer : "+nom+"/"+denom);
  }
}
