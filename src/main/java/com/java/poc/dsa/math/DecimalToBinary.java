package com.java.poc.dsa.math;

import java.util.Stack;

public class DecimalToBinary {

    public static void main(String[] args) {

        int[] binary_array = new int [10000000];
        int num = 25;
        System.out.println("Input is : "+num);
        System.out.println(Integer.toBinaryString(num));
        System.out.println("***************************************");
        printBinary(binary_array,num);
        printBinary_stack(num);
    }

    private static void printBinary(int[] binary_array, int num) {

        int index = 0;
        while(num > 0){
            binary_array[index++] = num%2;
            num = num/2;
        }

        for(int i=index - 1; i >=0 ; i--){
            System.out.print(binary_array[i]);
        }
        System.out.println("\n***************************************");
    }

    private static void printBinary_stack(int num){

        Stack<Integer> stack = new Stack<>();
        while(num > 0){
            stack.push(num%2);
            num = num/2;
        }

        while (!(stack.isEmpty() ))
        {
            System.out.print(stack.pop());
        }
        System.out.println();
    }
}
