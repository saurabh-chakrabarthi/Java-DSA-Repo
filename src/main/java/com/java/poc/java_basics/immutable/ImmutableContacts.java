package com.java.poc.java_basics.immutable;

import java.util.Date;

public final class ImmutableContacts {

    private final String name;
    private final String phone;
    private final Date joiningDate;

    public ImmutableContacts(String name, String phone, Date joiningDate) {
        this.name = name;
        this.phone = phone;
        this.joiningDate = joiningDate;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Date getJoiningDate() {
        //return (Date) joiningDate.clone();
        return joiningDate;
    }

    @Override
    public String toString() {
        return "ImmutableContacts{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", joiningDate=" + joiningDate +
                '}';
    }
}
