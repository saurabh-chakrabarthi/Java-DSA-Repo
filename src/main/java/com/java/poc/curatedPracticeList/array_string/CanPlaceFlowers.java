package com.java.poc.curatedPracticeList.array_string;

/**
 * 605. Can Place Flowers
 * Solved
 * Easy
 * Topics
 * Companies
 * You have a long flowerbed in which some of the plots are planted, and some are not. However, flowers cannot be planted in adjacent plots.
 *
 * Given an integer array flowerbed containing 0's and 1's, where 0 means empty and 1 means not empty, and an integer n, return true if n new flowers can be planted in the flowerbed without violating the no-adjacent-flowers rule and false otherwise.
 *
 *
 *
 * Example 1:
 *
 * Input: flowerbed = [1,0,0,0,1], n = 1
 * Output: true
 * Example 2:
 *
 * Input: flowerbed = [1,0,0,0,1], n = 2
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= flowerbed.length <= 2 * 104
 * flowerbed[i] is 0 or 1.
 * There are no two adjacent flowers in flowerbed.
 * 0 <= n <= flowerbed.length
 */
public class CanPlaceFlowers {

    public boolean canPlaceFlowers(int[] garden, int numFlowers) {
        if (numFlowers == 0) return true;

        int availableSpots = 0;
        int index = 0;
        while (index < garden.length) {
            if (garden[index] == 0
                    && (index == 0 || garden[index - 1] == 0)
                    && (index == garden.length - 1 || garden[index + 1] == 0)) {
                garden[index] = 1;  // plant a flower
                availableSpots++;
                index += 2;  // skip next plot
            } else {
                index++;
            }
        }

        return availableSpots >= numFlowers;
    }

    public static void main(String[] args) {
        CanPlaceFlowers arrangement = new CanPlaceFlowers();

        assert arrangement.canPlaceFlowers(new int[]{1, 0, 0, 0, 1}, 1) == true : "Test case 1 failed";
        assert arrangement.canPlaceFlowers(new int[]{1, 0, 0, 0, 1}, 2) == false : "Test case 2 failed";

        System.out.println("All tests passed!");
    }

}
