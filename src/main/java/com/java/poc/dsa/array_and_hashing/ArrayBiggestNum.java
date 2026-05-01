package com.java.poc.dsa.array_and_hashing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArrayBiggestNum {
  public static void main(String[] args) {
    int[] nums = new int[]{11,21,31};
    biggestNum(nums);
  }

  private static void biggestNum(int[] nums){
    List<String> strList = new ArrayList<>();
    for(int i : nums){
      strList.add(String.valueOf(i));
    }

    Collections.sort(strList, new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        String xy = o1+o2;
        String yx = o2+o1;
        if(xy.compareTo(yx) > 0){
          return -1;
        } else{
          return 1;
        }
      }

      @Override
      public boolean equals(Object obj) {
        return false;
      }
    });

    System.out.println("Biggest Number Is : ");
    for(String str : strList){
      System.out.print(str);
    }
    System.out.println("\n");
  }
}
