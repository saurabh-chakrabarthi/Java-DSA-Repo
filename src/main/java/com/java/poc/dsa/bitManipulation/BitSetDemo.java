package com.java.poc.dsa.bitManipulation;

import java.util.BitSet;

public class BitSetDemo {

    public static void main(String args[]) {
        BitSet bits1 = new BitSet(16);
        BitSet bits2 = new BitSet(16);

        and_or_xor(bits1,bits2);

        printBits();
    }

    private static void and_or_xor(BitSet bits1, BitSet bits2 ){
        // set some bits
        for(int i = 0; i < 16; i++) {
            if((i % 2) == 0) bits1.set(i);
            if((i % 5) != 0) bits2.set(i);
        }

        System.out.println("Initial pattern in bits1: ");
        System.out.println(bits1);
        System.out.println("\nInitial pattern in bits2: ");
        System.out.println(bits2);

        // AND bits
        bits2.and(bits1);
        System.out.println("\nbits2 AND bits1: ");
        System.out.println(bits2);

        // OR bits
        bits2.or(bits1);
        System.out.println("\nbits2 OR bits1: ");
        System.out.println(bits2);

        // XOR bits
        bits2.xor(bits1);
        System.out.println("\nbits2 XOR bits1: ");
        System.out.println(bits2);
    }

    private static void printBits(){
        // Constructors of BitSet class
        BitSet bs1 = new BitSet();
        BitSet bs2 = new BitSet(6);

        /* set is BitSet class method
           expalined in next articles */
        bs1.set(0);
        bs1.set(0);
        bs1.set(1);
        bs1.set(1);


        // assign values to bs2
        bs2.set(2);
        bs2.set(2);
        bs2.set(8);
        bs2.set(8);
        bs2.set(1);
        bs2.set(1);

        // Printing the 2 Bitsets
        System.out.println("bs1  : " + bs1);
        System.out.println("bs2  : " + bs2);
    }
}