package com.java.poc.dsa.math;

import static com.java.poc.dsa.math.GCD.gcd;

public class LCM {
  public static void main(String[] args) {
    int[] num = new int[]{2,4,8};
    lcm(num);
  }
  public static void lcm(int[] num){
    int lcm = num[0];
    for(int i=1;i<num.length;i++){
      lcm = (lcm * num[i]) / gcd(lcm, num[i]);
    }
    System.out.println("LCM of the given numbers is: " + lcm);
  }
}
