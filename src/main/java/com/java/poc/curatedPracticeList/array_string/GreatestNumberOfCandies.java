package com.java.poc.curatedPracticeList.array_string;

import java.util.ArrayList;
import java.util.List;

/**
 * 1431. Kids With the Greatest Number of Candies
 * Solved
 * Easy
 * Topics
 * Companies
 * Hint
 * There are n kids with candies. You are given an integer array candies, where each candies[i] represents the number of candies the ith kid has, and an integer extraCandies, denoting the number of extra candies that you have.
 *
 * Return a boolean array result of length n, where result[i] is true if, after giving the ith kid all the extraCandies, they will have the greatest number of candies among all the kids, or false otherwise.
 *
 * Note that multiple kids can have the greatest number of candies.
 *
 *
 *
 * Example 1:
 *
 * Input: candies = [2,3,5,1,3], extraCandies = 3
 * Output: [true,true,true,false,true]
 * Explanation: If you give all extraCandies to:
 * - Kid 1, they will have 2 + 3 = 5 candies, which is the greatest among the kids.
 * - Kid 2, they will have 3 + 3 = 6 candies, which is the greatest among the kids.
 * - Kid 3, they will have 5 + 3 = 8 candies, which is the greatest among the kids.
 * - Kid 4, they will have 1 + 3 = 4 candies, which is not the greatest among the kids.
 * - Kid 5, they will have 3 + 3 = 6 candies, which is the greatest among the kids.
 * Example 2:
 *
 * Input: candies = [4,2,1,1,2], extraCandies = 1
 * Output: [true,false,false,false,false]
 * Explanation: There is only 1 extra candy.
 * Kid 1 will always have the greatest number of candies, even if a different kid is given the extra candy.
 * Example 3:
 *
 * Input: candies = [12,1,12], extraCandies = 10
 * Output: [true,false,true]
 *
 *
 * Constraints:
 *
 * n == candies.length
 * 2 <= n <= 100
 * 1 <= candies[i] <= 100
 * 1 <= extraCandies <= 50
 */
public class GreatestNumberOfCandies {

    public List<Boolean> kidsWithCandies(int[] kidsCandies, int additionalCandies) {

        List<Boolean> results = new ArrayList<>(kidsCandies.length);

        int highestCandies = 0;

        for (int candyCount : kidsCandies) {
            highestCandies = Math.max(candyCount, highestCandies);
        }

        int threshold = highestCandies - additionalCandies;

        for (int candyCount : kidsCandies) {
            results.add(candyCount >= threshold);
        }

        return results;
    }

    public static void main(String[] args) {

        GreatestNumberOfCandies solution = new GreatestNumberOfCandies();

        List<Boolean> result1 = solution.kidsWithCandies(new int[]{2, 3, 5, 1, 3}, 3);
        assert result1.equals(List.of(true, true, true, false, true)) : "Test case 1 failed";

        List<Boolean> result2 = solution.kidsWithCandies(new int[]{4, 2, 1, 1, 2}, 1);
        assert result2.equals(List.of(true, false, false, false, false)) : "Test case 2 failed";

        List<Boolean> result3 = solution.kidsWithCandies(new int[]{12, 1, 12}, 10);
        assert result3.equals(List.of(true, false, true)) : "Test case 3 failed";

        System.out.println("All tests passed!");
    }
}
