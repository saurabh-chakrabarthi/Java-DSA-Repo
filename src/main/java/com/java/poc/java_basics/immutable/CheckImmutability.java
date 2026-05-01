package com.java.poc.java_basics.immutable;

import java.util.Date;

public class CheckImmutability {

    public static void main(String[] args) {
        ImmutableContacts ic = new ImmutableContacts("Saurabh","12345", new Date());
        System.out.println(ic);

        ImmutableContacts ic1 = new ImmutableContacts("Saurabh123","66666", new Date());
        System.out.println(ic1);

    }

}
