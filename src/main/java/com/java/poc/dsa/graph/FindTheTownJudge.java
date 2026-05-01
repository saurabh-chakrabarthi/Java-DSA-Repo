package com.java.poc.dsa.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * In a town, there are n people labeled from 1 to n. There is a rumor that one of these people is secretly the town judge.
 *
 * If the town judge exists, then:
 *
 * The town judge trusts nobody.
 * Everybody (except for the town judge) trusts the town judge.
 * There is exactly one person that satisfies properties 1 and 2.
 * You are given an array trust where trust[i] = [ai, bi] representing that the person labeled ai trusts the person labeled bi. If a trust relationship does not exist in trust array, then such a trust relationship does not exist.
 *
 * Return the label of the town judge if the town judge exists and can be identified, or return -1 otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 2, trust = [[1,2]]
 * Output: 2
 * Example 2:
 *
 * Input: n = 3, trust = [[1,3],[2,3]]
 * Output: 3
 * Example 3:
 *
 * Input: n = 3, trust = [[1,3],[2,3],[3,1]]
 * Output: -1
 *
 *
 * Constraints:
 *
 * 1 <= n <= 1000
 * 0 <= trust.length <= 104
 * trust[i].length == 2
 * All the pairs of trust are unique.
 * ai != bi
 * 1 <= ai, bi <= n
 */
public class FindTheTownJudge {
    public static void main(String[] args) {
        int n = 3;
        int[][] trust = {{1, 3}, {2, 3}};
        System.out.println("The town judge is person no. "+findJudge(n, trust));
        System.out.println("The town judge is person no. "+findJudgeOptimized(n, trust));
    }

    private static int findJudge(int n, int[][] trust) {
        Map<Integer, Integer> in = new HashMap<>();
        Map<Integer, Integer> out = new HashMap<>();

        for (int[] t : trust) {
            out.put(t[0], out.getOrDefault(t[0], 0) + 1);
            in.put(t[1], in.getOrDefault(t[1], 0) + 1);
        }

        for (int i = 1; i <= n; i++) {
            int inCount = in.getOrDefault(i, 0);
            int outCount = out.getOrDefault(i, 0);
            if (inCount == n - 1 && outCount == 0) {
                return i;
            }
        }

        return -1;
    }

    private static int findJudgeOptimized(int n, int[][] trust) {
        int[] trustScores = new int[n + 1];

        for (int[] t : trust) {
            trustScores[t[0]]--; // Person t[0] trusts someone, decrease their score
            trustScores[t[1]]++; // Person t[1] is trusted by someone, increase their score
        }

        for (int i = 1; i <= n; i++) {
            if (trustScores[i] == n - 1) {
                return i; // The town judge is the one with a score of n-1
            }
        }

        return -1; // No town judge found
    }
}
