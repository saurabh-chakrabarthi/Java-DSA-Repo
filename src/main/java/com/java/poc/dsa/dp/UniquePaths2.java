package com.java.poc.dsa.dp;

/**
 * You are given an m x n integer array grid. There is a robot initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
 *
 * An obstacle and space are marked as 1 or 0 respectively in grid. A path that the robot takes cannot include any square that is an obstacle.
 *
 * Return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 *
 * The testcases are generated so that the answer will be less than or equal to 2 * 109.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * Output: 2
 * Explanation: There is one obstacle in the middle of the 3x3 grid above.
 * There are two ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down -> Down
 * 2. Down -> Down -> Right -> Right
 * Example 2:
 *
 *
 * Input: obstacleGrid = [[0,1],[0,0]]
 * Output: 1
 *
 *
 * Constraints:
 *
 * m == obstacleGrid.length
 * n == obstacleGrid[i].length
 * 1 <= m, n <= 100
 * obstacleGrid[i][j] is 0 or 1.
 */
public class UniquePaths2 {
    public static void main(String[] args) {
        // Test 1: No obstacles (should be same as Unique Paths)
        int[][] grid1 = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        System.out.println(findUniquePaths(grid1)); // Expected: 6

        // Test 2: One obstacle in the middle
        int[][] grid2 = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        System.out.println(findUniquePaths(grid2)); // Expected: 2

        // Test 3: Obstacle at start
        int[][] grid3 = {
                {1, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };
        System.out.println(findUniquePaths(grid3)); // Expected: 0

        // Test 4: Obstacle at end
        int[][] grid4 = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 1}
        };
        System.out.println(findUniquePaths(grid4)); // Expected: 0

        // Test 5: All blocked except start
        int[][] grid5 = {
                {0, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };
        System.out.println(findUniquePaths(grid5)); // Expected: 0
    }

    public static int findUniquePaths(int[][] obstacleGrid){
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;

        int[][] dp = new int[row][col];
        if(obstacleGrid[0][0] == 1){
            return 0;
        }
        dp[0][0] = 1;

        for(int i=1;i<row;i++) {
            if (obstacleGrid[i][0] == 0 && dp[i - 1][0] == 1) {
                dp[i][0] = 1;
            } else {
                dp[i][0] = 0;
            }
        }
        for(int j=1;j<col;j++){
            if(obstacleGrid[0][j] == 0 && dp[0][j-1] == 1){
                dp[0][j] = 1;
            }else{
                dp[0][j] = 0;
            }
        }

        for(int i=1;i<row;i++){
            for(int j=1;j<col;j++){
                if(obstacleGrid[i][j] == 1){
                    dp[i][j] = 0;
                }else{
                    dp[i][j] = dp[i-1][j]+dp[i][j-1];
                }
            }
        }
        return dp[row-1][col-1];
    }
}
