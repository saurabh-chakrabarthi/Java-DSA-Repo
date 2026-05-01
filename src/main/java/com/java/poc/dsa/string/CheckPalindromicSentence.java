package com.java.poc.dsa.string;

public class CheckPalindromicSentence {
    public static void main(String[] args) {
        String s = "Race  Car";
        System.out.println(" Is Statement a palindrome ? : "+isPalindrome(s));
        System.out.println(" Is Statement a palindrome ? : "+isPalindrome2(s));
    }
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length()-1;

        while (left < right){
            char c1 = s.charAt(left);
            char c2 = s.charAt(right);

            //if c1 is not valid then move forward
            if (!Character.isLetterOrDigit(c1)){
                left++;
            }
            // if c2 is not valid move backward
            else if (!Character.isLetterOrDigit(c2)){
                right--;
            }
            // otherwise compare
            else{
                if (Character.toLowerCase(c1) != Character.toLowerCase(c2)){
                    return false;
                }
                left++;
                right--;
            }
        }

        return true;
    }

    public static boolean isPalindrome2(String s) {


        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(Character c : chars){
            if(Character.isLetterOrDigit(c)){
                sb.append(Character.toLowerCase(c));
            }
        }
        System.out.println(sb.toString());
        int left = 0;
        int right = sb.toString().length()-1;

        while(left < right){
            if(sb.toString().charAt(left) == sb.toString().charAt(right)){
                left++;
                right--;
            }else{
                return false;
            }
        }

        return true;

    }
}
