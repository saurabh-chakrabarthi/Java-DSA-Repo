package com.java.poc.java8.streams;

import java.util.List;

public class UseFilters {
    public static void main(String[] args) {
        List<String> list = List.of("she","sells","sea","shells","on","the","seashore");
        //list.stream().filter(a->a.startsWith("se")).forEach(System.out::println);

        list.stream().filter(a->a.length() >=3).forEach(System.out::println);
    }
}
