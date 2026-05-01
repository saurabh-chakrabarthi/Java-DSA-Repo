package com.java.poc.java_basics.oops;

public class Parent {

    int id;
    String name;

    public Parent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Parent() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String color(){
        //System.out.println("Black");
        return "Black";
    }
}
