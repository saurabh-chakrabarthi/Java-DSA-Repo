package com.java.poc.dsa.string;

public class DefangIPAddr {
    public static void main(String[] args) {
        defangIPaddr("1.1.1.1");
    }

    public static String defangIPaddr(String address) {
        String[] subnet = address.split("\\.");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< subnet.length;i++){
            if(Integer.valueOf(subnet[i]) <=255){
                sb.append(subnet[i]);
            }
            if(i < subnet.length - 1){
                sb.append("[.]");
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
