package com.java.poc.dsa.array_and_hashing;

public class PartitionArrayInto3EqualParts {

    public static void main(String[] args) {
        int[] arr = new int[] {0,2,1,-6,6,-7,9,1,2,0,1};
        System.out.println(canThreePartsEqualSum(arr));
    }

    public static boolean canThreePartsEqualSum(int[] arr) {
        int sum = 0;
        for(int n : arr){
            sum+=n;
        }
        if (sum % 3 != 0) {
            return false;
        }
        int average = sum / 3;
        int cnt = 0;
        int part = 0;

        for (int a : arr) {
            part += a;
            if (part == average) {
                ++cnt;
                part = 0;
            }
        }
        return cnt >= 3 && sum % 3 == 0;
    }
}
