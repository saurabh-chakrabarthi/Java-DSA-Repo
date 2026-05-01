package com.java.poc.dsa.graph;

/**
 * Islands and Treasure
 * You are given a
 * m
 * ×
 * n
 * m×n 2D grid initialized with these three possible values:
 *
 * -1 - A water cell that can not be traversed.
 * 0 - A treasure chest.
 * INF - A land cell that can be traversed. We use the integer 2^31 - 1 = 2147483647 to represent INF.
 * Fill each land cell with the distance to its nearest treasure chest. If a land cell cannot reach a treasure chest then the value should remain INF.
 *
 * Assume the grid can only be traversed up, down, left, or right.
 *
 * Modify the grid in-place.
 *
 * Example 1:
 *
 * Input: [
 *   [2147483647,-1,0,2147483647],
 *   [2147483647,2147483647,2147483647,-1],
 *   [2147483647,-1,2147483647,-1],
 *   [0,-1,2147483647,2147483647]
 * ]
 *
 * Output: [
 *   [3,-1,0,1],
 *   [2,2,1,-1],
 *   [1,-1,2,-1],
 *   [0,-1,3,4]
 * ]
 * Example 2:
 *
 * Input: [
 *   [0,-1],
 *   [2147483647,2147483647]
 * ]
 *
 * Output: [
 *   [0,-1],
 *   [1,2]
 * ]
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * grid[i][j] is one of {-1, 0, 2147483647}
 */
public class WallsAndGate {

    public static void main(String[] args) {
        int INF = 2147483647;
        int[][] rooms = {
                {INF, -1, 0, INF},
                {INF, INF, INF, -1},
                {INF, -1, INF, -1},
                {0, -1, INF, INF}
        };
        wallsAndGates(rooms);
        for (int[] row : rooms) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private static void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0) return;
        int m = rooms.length;
        int n = rooms[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    dfs(rooms, i, j, 0);
                }
            }
        }
    }

    private static void dfs(int[][] rooms, int i, int j, int i1) {
        int m = rooms.length;
        int n = rooms[0].length;

        if (i < 0 || j < 0 || i >= m || j >= n || rooms[i][j] < i1) {
            return;
        }

        rooms[i][j] = i1;

        dfs(rooms, i + 1, j, i1 + 1);
        dfs(rooms, i - 1, j, i1 + 1);
        dfs(rooms, i, j + 1, i1 + 1);
        dfs(rooms, i, j - 1, i1 + 1);
    }
}
