package com.java.poc.java8.predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class IsCollectionEmpty {
    /*
    checks if collection is empty or not
     */
    public static void main(String[] args) {
        Predicate<Collection> p = c -> c.isEmpty();
        List<String> a = new ArrayList<>();
        a.add("hello");
        System.out.println(p.test(new ArrayList()));
        System.out.println(p.test(a));
    }
}
