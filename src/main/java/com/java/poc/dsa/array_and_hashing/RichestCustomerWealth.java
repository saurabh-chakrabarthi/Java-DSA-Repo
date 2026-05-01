package com.java.poc.dsa.array_and_hashing;

public class RichestCustomerWealth {
    public static void main(String[] args) {
        int[][] wealth = { {1, 2} , { 4, 5} };
        System.out.println("max wealth is : "+maximumWealth(wealth));
    }

    public static int maximumWealth(int[][] accounts) {

        int maxWealth = 0;
        int localWealth = 0;
        for(int[] arr : accounts){
            for(int wealth : arr){
                localWealth +=wealth;
            }
            if(localWealth >= maxWealth){
                maxWealth = localWealth;
            }
            localWealth = 0;
        }
        return maxWealth;
    }
}
