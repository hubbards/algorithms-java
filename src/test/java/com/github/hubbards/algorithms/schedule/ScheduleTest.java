package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import static com.github.hubbards.algorithms.schedule.Schedule.intervalPartition;
import static com.github.hubbards.algorithms.schedule.Schedule.intervalScheduleI;
import static com.github.hubbards.algorithms.schedule.Schedule.intervalScheduleR;
import static com.github.hubbards.algorithms.schedule.Schedule.maxLateness;
import static com.github.hubbards.algorithms.schedule.Schedule.minLateness;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ScheduleTest {
    @Test
    public void testIntervalSchedulingOnNoRequests() {
        boolean[] schedule = intervalScheduleI(new float[0], new float[0]);

        // schedule should be empty
        assertEquals(0, schedule.length);
    }

    @Test
    public void testIntervalSchedulingOnSingleRequest() {
        float[] start = {0};
        float[] finish = {1};

        boolean[] schedule = intervalScheduleI(start, finish);

        // schedule should be single request
        assertEquals(1, schedule.length);
        assertTrue(schedule[0]);
    }

    @Test
    public void testIntervalSchedulingOnRequestsOrderedByStartTime() {
        float[] start = { 0, 1, 3 };
        float[] finish = { 5, 2, 4 };

        boolean[] schedule = intervalScheduleI(start, finish);

        // schedule should be requests 1 and 2
        assertEquals(3, schedule.length);
        assertFalse(schedule[0]);
        assertTrue(schedule[1]);
        assertTrue(schedule[2]);
    }

    @Test
    public void testIntervalSchedulingOnRequestsOrderedByDuration() {
        float[] start = { 0, 4, 2 };
        float[] finish = { 3, 8, 5 };

        boolean[] schedule = intervalScheduleI(start, finish);

        // schedule should be requests 0 and 1
        assertEquals(3, schedule.length);
        assertTrue(schedule[0]);
        assertTrue(schedule[1]);
        assertFalse(schedule[2]);
    }

    @Test
    public void testIntervalSchedulingOnRequestsOrderedByFinishTime() {
        float[] start = { 0, 1, 3 };
        float[] finish = { 2, 4, 5 };

        boolean[] schedule = intervalScheduleI(start, finish);

        // schedule should be requests 0 and 2
        assertEquals(3, schedule.length);
        assertTrue(schedule[0]);
        assertFalse(schedule[1]);
        assertTrue(schedule[2]);
    }

    @Test
    public void testIntervalPartitionOnNoRequests() {
        int[] partition = intervalPartition(new float[0], new float[0]);

        // partition should be empty
        assertEquals(0, partition.length);
    }

    @Test
    public void testIntervalPartitionOnSingleRequest() {
        float[] start = { 0 };
        float[] finish = { 1 };

        int[] partition = intervalPartition(start, finish);

        // partition should be single resource and request
        assertEquals(1, partition.length);
    }

    @Test
    public void testIntervalPartitionOnIncompatibleRequests() {
        float[] start = { 0, 1, 2 };
        float[] finish = { 3, 4, 5 };

        int[] partition = intervalPartition(start, finish);

        // schedule all requests on different resources
        assertEquals(3, partition.length);
        assertNotEquals(partition[1], partition[0]);
        assertNotEquals(partition[2], partition[0]);
        assertNotEquals(partition[2], partition[1]);
    }

    @Test
    public void testIntervalPartitionOnCompatibleRequests() {
        float[] start = { 0, 2, 4 };
        float[] finish = { 1, 3, 5 };

        int[] partition = intervalPartition(start, finish);

        // schedule all requests on the same resource
        assertEquals(3, partition.length);
        assertEquals(partition[1], partition[0]);
        assertEquals(partition[2], partition[0]);
    }

    @Test
    public void testIntervalPartitionOnMixedRequests() {
        float[] start = { 0, 1, 3 };
        float[] finish = { 2, 4, 5 };

        int[] partition = intervalPartition(start, finish);

        // schedule requests 0 and 2 on one resource and request 1 on another
        assertEquals(3, partition.length);
        assertNotEquals(partition[1], partition[0]);
        assertEquals(partition[2], partition[0]);
    }

    // TODO: convert to unit tests
    public static void main(String[] args) {
        // example of (unweighted) interval scheduling problem
        float[] start = { 1, 3, 0, 4, 3, 5, 6, 8 };
        float[] finish = { 4, 5, 6, 7, 8, 9, 10, 11 };
        boolean[] solutionI = intervalScheduleI(start, finish);
        boolean[] solutionR = intervalScheduleR(start, finish);

        // print schedule
        System.out.println("Iterative Solution for Unweighted Interval Scheduling Example");
        printIntervalSchedule(start, finish, solutionI);
        System.out.println("Recursive Solution for Unweighted Interval Scheduling Example");
        printIntervalSchedule(start, finish, solutionR);

        // TODO: example of (weighted) interval scheduling problem

        // example of minimum lateness problem
        float[] processing = { 3, 2, 1, 4, 3, 2 };
        float[] deadline = { 6, 8, 9, 9, 14, 15 };
        start = minLateness(deadline, processing);

        // print schedule
        System.out.println("Solution for Minimum Lateness Example");
        printMinLateness(deadline, processing, start);
    }

    // Helper method. Prints a given schedule to standard output.
    private static void printIntervalSchedule(float[] start, float[] finish, boolean[] schedule) {
        int n = start.length;
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.printf("Start: %4.1f Finish: %4.1f Schedule: ", start[i], finish[i]);
            if (schedule[i]) {
                // request i is contained in schedule
                System.out.println("X");
            } else {
                // request i is not contained in schedule
                System.out.println(" ");
            }
        }
        System.out.println();
    }

    // Helper method. Prints a given schedule to standard output.
    private static void printMinLateness(float[] deadline, float[] processing, float[] start) {
        int n = deadline.length;
        int i = maxLateness(deadline, processing, start);
        System.out.println();
        for (int j = 0; j < n; j++) {
            float finish = start[j] + processing[j];
            float lateness = start[j] + processing[j] - deadline[j];
            System.out.printf("Start: %4.1f Finish: %4.1f Lateness: %4.1f", start[j], finish, lateness);
            if (j == i) {
                System.out.println(" <- max lateness");
            } else {
                System.out.println();
            }
        }
        System.out.println();
    }
}
