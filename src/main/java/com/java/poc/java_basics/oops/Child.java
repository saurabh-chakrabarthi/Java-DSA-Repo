package com.java.poc.java_basics.oops;

public class Child extends Parent{
    public Child(int id, String name) {
        super(id, name);
    }

    public Child() {

    }

    @Override
    public String color() {

        return "Yellow";
    }
}
