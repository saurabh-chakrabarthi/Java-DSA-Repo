package com.java.poc.dsa.sort;

import java.util.Arrays;

public class SelectionSort
{
    void sort(int arr[])
    {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }

    public static void main(String args[])
    {
        SelectionSort ob = new SelectionSort();
        int[] arr = new int[]{78, 34, 1, 3, 90, 34, -1, -4, 6, 55, 20, -65};
        System.out.println("Array elements before sorting: " + Arrays.toString(arr));
        ob.sort(arr);
        System.out.println("Array elements after sorting: " + Arrays.toString(arr));
    }
}
/* This code is contributed by Rajat Mishra*/
