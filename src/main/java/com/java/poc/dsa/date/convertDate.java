package com.java.poc.dsa.date;

import java.text.SimpleDateFormat;

public class convertDate {

    static String timeConversion(String s) {
        String time24 = null;
        try {
            //String now = new SimpleDateFormat("hh:mm:ss aa").format(s);
            //System.out.println("time in 12 hour format : " + now);
            SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            time24 = outFormat.format(inFormat.parse(s));
            System.out.println("time in 24 hour format : " + time24);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
        return time24;
    }

    public static void main(String[] args) {
        timeConversion("07:05:45PM");
    }
}
