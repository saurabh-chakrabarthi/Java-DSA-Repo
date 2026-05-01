package com.java.poc.curatedPracticeList.blind75;

import java.util.HashMap;

/**
 * 2086. Minimum Number of Food Buckets to Feed the Hamsters
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given a 0-indexed string hamsters where hamsters[i] is either:
 *
 * 'H' indicating that there is a hamster at index i, or
 * '.' indicating that index i is empty.
 * You will add some number of food buckets at the empty indices in order to feed the hamsters. A hamster can be fed if there is at least one food bucket to its left or to its right. More formally, a hamster at index i can be fed if you place a food bucket at index i - 1 and/or at index i + 1.
 *
 * Return the minimum number of food buckets you should place at empty indices to feed all the hamsters or -1 if it is impossible to feed all of them.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: hamsters = "H..H"
 * Output: 2
 * Explanation: We place two food buckets at indices 1 and 2.
 * It can be shown that if we place only one food bucket, one of the hamsters will not be fed.
 * Example 2:
 *
 *
 * Input: hamsters = ".H.H."
 * Output: 1
 * Explanation: We place one food bucket at index 2.
 * Example 3:
 *
 *
 * Input: hamsters = ".HHH."
 * Output: -1
 * Explanation: If we place a food bucket at every empty index as shown, the hamster at index 2 will not be able to eat.
 *
 *
 * Constraints:
 *
 * 1 <= hamsters.length <= 105
 * hamsters[i] is either'H' or '.'.
 */
public class FeedHamsters {

    public static void main(String[] args) {
        System.out.println(minimumBuckets1("H..H"));
        System.out.println(minimumBuckets2(".H.H."));
        System.out.println(minimumBuckets3(".HHH."));
    }

    public static int minimumBuckets1(String hamsters) {
        int count=0;
        for(int i=0;i<hamsters.length();i++){
            if(hamsters.charAt(i)=='H'){
                if(i+1<hamsters.length() && hamsters.charAt(i+1)=='.'){
                    count++;
                    i+=2;
                    continue;
                }
                if(i-1>=0 && hamsters.charAt(i-1)=='.'){
                    count++;
                    continue;
                }
                return -1;
            }
        }
        return count;
    }

    public static int minimumBuckets2(String street) {
        HashMap<Integer,Boolean> map = new HashMap<>();
        int count = 0;
        for(int i=0;i<street.length();i++){
            if(street.charAt(i)=='H'){
                if(map.getOrDefault(i-1,false)) continue;

                if(i+1<street.length() && street.charAt(i+1)=='.'){
                    count++;
                    map.put(i+1,true);
                    continue;
                }

                if(i-1>=0 && street.charAt(i-1)=='.'){
                    count++;
                    continue;
                }

                return -1;
            }
        }
        return count;
    }

    public static int minimumBuckets3(String street) {
        int count=0;
        StringBuilder sb = new StringBuilder(street);
        for(int i=0;i<street.length();i++){
            if(sb.charAt(i)=='H'){
                if(i-1>=0 && sb.charAt(i-1)=='B') continue;
                if(i+1<sb.length() && sb.charAt(i+1)=='.'){
                    count++;
                    sb.setCharAt(i+1,'B');
                    continue;
                }
                if(i-1>=0 && sb.charAt(i-1)=='.'){
                    count++;
                    continue;
                }
                return -1;
            }
        }
        return count;
    }
}
