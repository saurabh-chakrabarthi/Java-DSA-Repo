package com.java.poc.dsa.string;

public class TimeConversion {

    public static void main(String[] args) {

        String time1 = "12:01:00AM";    //Return 00:01:00
        String time2 = "07:05:45PM";    //Return 19:05:45

        convert(time1);
        convert(time2);
    }

    private static void convert(String time) {
        System.out.println("Input time is : " + time);
        StringBuilder sb = new StringBuilder();
        String[] strs = time.split(":");
        if (strs[2].contains("AM")) {
            if (strs[0].equals("12")) {
                sb.append("00");
            }else{
                sb.append(strs[0]);
            }
            sb.append(":");
            sb.append(strs[1]);
            sb.append(":");
            sb.append(strs[2], 0, 2);
        } else if (strs[2].contains("PM")) {
            if (strs[0].equals("12")) {
                sb.append("12");
            }else{
                int hour = Integer.parseInt(strs[0])+12;
                sb.append(hour);
            }
            sb.append(":");
            sb.append(strs[1]);
            sb.append(":");
            sb.append(strs[2], 0, 2);
        } else {
            System.out.println("Invalid input");
        }
        System.out.println("Output time is : "+sb.toString());
    }
}
