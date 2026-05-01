package com.java.poc.curatedPracticeList.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * 39. Combination Sum
 * Medium
 * Topics
 * Companies
 * Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
 *
 * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the
 * frequency
 *  of at least one of the chosen numbers is different.
 *
 * The test cases are generated such that the number of unique combinations that sum up to target is less than 150 combinations for the given input.
 *
 *
 *
 * Example 1:
 *
 * Input: candidates = [2,3,6,7], target = 7
 * Output: [[2,2,3],[7]]
 * Explanation:
 * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
 * 7 is a candidate, and 7 = 7.
 * These are the only two combinations.
 * Example 2:
 *
 * Input: candidates = [2,3,5], target = 8
 * Output: [[2,2,2,2],[2,3,3],[3,5]]
 * Example 3:
 *
 * Input: candidates = [2], target = 1
 * Output: []
 *
 *
 * Constraints:
 *
 * 1 <= candidates.length <= 30
 * 2 <= candidates[i] <= 40
 * All elements of candidates are distinct.
 * 1 <= target <= 40
 */
public class CombinationSumFinder {

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        combination(ans, new ArrayList<>(), k, 1, n);
        return ans;
    }

    private void combination(List<List<Integer>> ans, List<Integer> comb, int k, int start, int n) {
        if (comb.size() == k && n == 0) {
            List<Integer> li = new ArrayList<>(comb);
            ans.add(li);
            return;
        }
        for (int i = start; i <= 9; i++) {
            comb.add(i);
            combination(ans, comb, k, i + 1, n - i);
            comb.remove(comb.size() - 1);
        }
    }

    public static void main(String[] args) {
        CombinationSumFinder finder = new CombinationSumFinder();

        List<List<Integer>> result1 = finder.combinationSum3(3, 7);
        List<List<Integer>> expected1 = List.of(List.of(1, 2, 4));
        assert result1.equals(expected1) : "Test case 1 failed";

        List<List<Integer>> result2 = finder.combinationSum3(3, 9);
        List<List<Integer>> expected2 = List.of(List.of(1, 2, 6), List.of(1, 3, 5), List.of(2, 3, 4));
        assert result2.equals(expected2) : "Test case 2 failed";

        List<List<Integer>> result3 = finder.combinationSum3(4, 1);
        List<List<Integer>> expected3 = List.of();
        assert result3.equals(expected3) : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
