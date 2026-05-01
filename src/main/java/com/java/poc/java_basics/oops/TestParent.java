package com.java.poc.java_basics.oops;

public class TestParent {

    public static void main(String[] args) {
        Parent p = new Parent();
        System.out.println(p.color());

        Parent p2 = new Child();
        System.out.println(p2.color());
    }
}
