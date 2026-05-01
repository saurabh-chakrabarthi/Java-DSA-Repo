package com.java.poc.dsa.intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Meeting Schedule
 * Solved
 * Given an array of meeting time interval objects consisting of start and end times [[start_1,end_1],[start_2,end_2],...] (start_i < end_i), determine if a person could add all meetings to their schedule without any conflicts.
 *
 * Example 1:
 *
 * Input: intervals = [(0,30),(5,10),(15,20)]
 *
 * Output: false
 * Explanation:
 *
 * (0,30) and (5,10) will conflict
 * (0,30) and (15,20) will conflict
 * Example 2:
 *
 * Input: intervals = [(5,8),(9,15)]
 *
 * Output: true
 * Note:
 *
 * (0,8),(8,10) is not considered a conflict at 8
 * Constraints:
 *
 * 0 <= intervals.length <= 500
 * 0 <= intervals[i].start < intervals[i].end <= 1,000,000
 */
public class ScheduleMeetings {

    public static class Interval {
      public int start, end;
      public Interval(int start, int end) {
          this.start = start;
          this.end = end;
      }

        @Override
        public String toString() {
            return "Interval{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    public static void main(String[] args) {
        Interval interval1 = new Interval(0,30);
        Interval interval2 = new Interval(5,10);
        Interval interval3 = new Interval(15,20);
        List<Interval> intervals = new ArrayList<>();
        intervals.add(interval1);
        intervals.add(interval2);
        intervals.add(interval3);

        System.out.println(canAttendMeetings(intervals));
        System.out.println(canAttendMeetings2(intervals));
    }

    public static boolean canAttendMeetings(List<Interval> intervals) {
        System.out.println("before sorting : "+intervals);
        Collections.sort(intervals, (a, b) -> a.start - b.start);
        System.out.println("after sorting : "+intervals);
        for (int i = 0; i < intervals.size()-1; i++) {
            if (intervals.get(i).end > intervals.get(i + 1).start) {
                return false;
            }
        }
        return true;
    }

    public static boolean canAttendMeetings2(List<Interval> intervals) {
        int length = intervals.size();
        if (intervals.isEmpty() || length == 1) return true;
        // Write your code here

        int[] start = new int[length];
        int[] end = new int[length];
        for (int i = 0; i < length; i++) {
            start[i] = intervals.get(i).start;
            end[i] = intervals.get(i).end;
        }

        Arrays.sort(start);
        Arrays.sort(end);

        int j = 0;
        while (j + 1 < length) {
            if (end[j] > start[j + 1]) return false;
            j++;
        }

        return true;
    }
}
