package com.java.poc.java8.lambda;

public class LambdaThread {
    public static void main(String[] args) {
        Runnable run = ()->{for(int i = 0;i<10;i++){
            System.out.println("Child Thread");}
        };
        run.run();

        for(int i=0;i<10;i++){
            System.out.println("Parent Thread");
        }
    }
}
