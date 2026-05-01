package com.java.poc.dsa.array_and_hashing;

/**
 * 957. Prison Cells After N Days
 * Medium
 * Topics
 * Companies
 * There are 8 prison cells in a row and each cell is either occupied or vacant.
 *
 * Each day, whether the cell is occupied or vacant changes according to the following rules:
 *
 * If a cell has two adjacent neighbors that are both occupied or both vacant, then the cell becomes occupied.
 * Otherwise, it becomes vacant.
 * Note that because the prison is a row, the first and the last cells in the row can't have two adjacent neighbors.
 *
 * You are given an integer array cells where cells[i] == 1 if the ith cell is occupied and cells[i] == 0 if the ith cell is vacant, and you are given an integer n.
 *
 * Return the state of the prison after n days (i.e., n such changes described above).
 *
 *
 *
 * Example 1:
 *
 * Input: cells = [0,1,0,1,1,0,0,1], n = 7
 * Output: [0,0,1,1,0,0,0,0]
 * Explanation: The following table summarizes the state of the prison on each day:
 * Day 0: [0, 1, 0, 1, 1, 0, 0, 1]
 * Day 1: [0, 1, 1, 0, 0, 0, 0, 0]
 * Day 2: [0, 0, 0, 0, 1, 1, 1, 0]
 * Day 3: [0, 1, 1, 0, 0, 1, 0, 0]
 * Day 4: [0, 0, 0, 0, 0, 1, 0, 0]
 * Day 5: [0, 1, 1, 1, 0, 1, 0, 0]
 * Day 6: [0, 0, 1, 0, 1, 1, 0, 0]
 * Day 7: [0, 0, 1, 1, 0, 0, 0, 0]
 * Example 2:
 *
 * Input: cells = [1,0,0,1,0,0,1,0], n = 1000000000
 * Output: [0,0,1,1,1,1,1,0]
 *
 *
 * Constraints:
 *
 * cells.length == 8
 * cells[i] is either 0 or 1.
 * 1 <= n <= 109
 */
public class Active_Inactive_Cells_After_N_Days {

    public static void main(String[] args) {
        int[] states = new int[]{1, 0, 0, 0, 1, 0, 0,};
        int days = 2;
        cellCompete(states, days);
    }

    /// <summary>
    /// Method to get the state pf the cells after given number of days.
    /// </summary>
    /// <param name="states">current state of the cells</param>
    /// <param name="days">number of days</param>
    /// <returns>state of cells after given number of days</returns>
    static int[] cellCompete(int[] states, int days) {
        // if the number of cells is not equals to 8
        // or number of days is less than 1
        if (states.length != 8 || days < 1) {
            return states;
        }

        // local variables
        int index, previousValue, nextValue;

        // loop for each day
        for (int i = 0; i < days; i++) {
            // index of current cell
            index = 0;
            // value of the previous cell
            previousValue = 0;
            // value of the next cell
            nextValue = 0;

            // loop for each cell in the array
            while (index < states.length) {
                // if the current index is not last index of the given array,
                // set the value of nextValue, else it would remain 0.
                if (index < states.length - 1) {
                    nextValue = states[index + 1];
                }

                // if nextValue is same as previousValue
                if (nextValue == previousValue) {
                    // save the current index value for next iteration of loop
                    previousValue = states[index];
                    // set current index value
                    states[index] = 0;
                } else {
                    // save the current index value for next iteration of loop
                    previousValue = states[index];
                    // set current index value
                    states[index] = 1;
                }

                // next item in the loop
                index++;
            }
        }

        // return states array
        return states;
    }
}
