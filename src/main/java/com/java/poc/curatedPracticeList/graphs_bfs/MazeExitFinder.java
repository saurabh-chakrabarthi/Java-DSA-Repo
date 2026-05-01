package com.java.poc.curatedPracticeList.graphs_bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1926. Nearest Exit from Entrance in Maze
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+'). You are also given the entrance of the maze, where entrance = [entrancerow, entrancecol] denotes the row and column of the cell you are initially standing at.
 *
 * In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step outside the maze. Your goal is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at the border of the maze. The entrance does not count as an exit.
 *
 * Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: maze = [["+","+",".","+"],[".",".",".","+"],["+","+","+","."]], entrance = [1,2]
 * Output: 1
 * Explanation: There are 3 exits in this maze at [1,0], [0,2], and [2,3].
 * Initially, you are at the entrance cell [1,2].
 * - You can reach [1,0] by moving 2 steps left.
 * - You can reach [0,2] by moving 1 step up.
 * It is impossible to reach [2,3] from the entrance.
 * Thus, the nearest exit is [0,2], which is 1 step away.
 * Example 2:
 *
 *
 * Input: maze = [["+","+","+"],[".",".","."],["+","+","+"]], entrance = [1,0]
 * Output: 2
 * Explanation: There is 1 exit in this maze at [1,2].
 * [1,0] does not count as an exit since it is the entrance cell.
 * Initially, you are at the entrance cell [1,0].
 * - You can reach [1,2] by moving 2 steps right.
 * Thus, the nearest exit is [1,2], which is 2 steps away.
 * Example 3:
 *
 *
 * Input: maze = [[".","+"]], entrance = [0,0]
 * Output: -1
 * Explanation: There are no exits in this maze.
 *
 *
 * Constraints:
 *
 * maze.length == m
 * maze[i].length == n
 * 1 <= m, n <= 100
 * maze[i][j] is either '.' or '+'.
 * entrance.length == 2
 * 0 <= entrancerow < m
 * 0 <= entrancecol < n
 * entrance will always be an empty cell.
 */
public class MazeExitFinder {

    public int nearestExit(char[][] maze, int[] entrance) {
        int rows = maze.length, cols = maze[0].length, queueSize;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int x, y, steps = 0;

        queue.offer(entrance);
        visited[entrance[0]][entrance[1]] = true;

        while (!queue.isEmpty()) {
            queueSize = queue.size();
            steps++;

            for (int i = 0; i < queueSize; i++) {
                int[] curr = queue.poll();

                for (int[] dir : dirs) {
                    x = dir[0] + curr[0];
                    y = dir[1] + curr[1];

                    if (x < 0 || x >= rows || y < 0 || y >= cols) continue;
                    if (visited[x][y] || maze[x][y] == '+') continue;

                    if (x == 0 || x == rows - 1 || y == 0 || y == cols - 1) return steps;

                    queue.offer(new int[]{x, y});
                    visited[x][y] = true;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        MazeExitFinder finder = new MazeExitFinder();

        char[][] maze1 = {{'+','+','.','+'},{'.','.','.','+'},{'+','+','+','.'}};
        int[] entrance1 = {1, 2};
        assert finder.nearestExit(maze1, entrance1) == 1 : "Test case 1 failed";

        char[][] maze2 = {{'+','+','+'},{'.','.','.'},{'+','+','+'}};
        int[] entrance2 = {1, 0};
        assert finder.nearestExit(maze2, entrance2) == 2 : "Test case 2 failed";

        char[][] maze3 = {{'.','+'}};
        int[] entrance3 = {0, 0};
        assert finder.nearestExit(maze3, entrance3) == -1 : "Test case 3 failed";

        System.out.println("All test cases passed!");
    }
}
