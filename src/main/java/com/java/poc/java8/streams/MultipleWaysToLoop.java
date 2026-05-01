package com.java.poc.java8.streams;


import java.util.List;

public class MultipleWaysToLoop {
    public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3);
        /*System.out.println("******************** 1 **************************");
        for(int i : list){
            System.out.println(i);
        }*/
        //System.out.println("******************** 2**************************");
        //list.stream().forEach(System.out::println);
        //System.out.println("******************** 3 **************************");
        //list.stream().forEach(x -> System.out.println(x));
        //System.out.println("******************** 4 **************************");
        //list.forEach(System.out::println);
        System.out.println("******************** 5 **************************");
        list.forEach(MultipleWaysToLoop::print);
        System.out.println("******************** 6 **************************");
        list.stream().forEach(MultipleWaysToLoop::print);
        System.out.println("******************** 7 **************************");
        list.parallelStream().forEach(MultipleWaysToLoop::print);
    }

    public static void print(int n){
        System.out.println(n);
    }
}
