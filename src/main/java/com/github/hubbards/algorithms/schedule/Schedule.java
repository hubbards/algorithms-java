package com.github.hubbards.algorithms.schedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class contains some implementations of (greedy and dynamic programming)
 * algorithms for some scheduling problems.
 *
 * @author Spencer Hubbard
 */
public final class Schedule {
    private Schedule() {
        throw new AssertionError("static class");
    }

    /**
     * Iterative greedy algorithm for (unweighted) interval scheduling problem.
     *
     * @param start start times for requests, i.e., <code>start[i]</code> is the
     * start time of the <code>i</code>th request.
     * @param finish finish times for requests, i.e., <code>finish[i]</code> is
     * the finish time of the <code>i</code>th request.
     *
     * @return largest compatible subset of given requests.
     *
     * @throws IllegalArgumentException if <code>start</code> and
     * <code>finish</code> have different length
     */
    public static boolean[] intervalScheduleI(float[] start, float[] finish) {
        validateRequests(start, finish);

        // order requests by earliest finish time
        List<Integer> requests = makeOrderedRequests(finish);

        // schedule requests
        boolean[] schedule = new boolean[requests.size()];
        int i = 0;
        while (i < requests.size()) {
            // schedule request
            schedule[requests.get(i)] = true;
            // find compatible request with next earliest finish time
            int j = i + 1;
            while (j < requests.size() && start[requests.get(j)] < finish[requests.get(i)]) {
                // requests are not compatible
                schedule[requests.get(j)] = false;
                j++;
            }
            i = j;
        }

        return schedule;
    }

    /**
     * Recursive greedy algorithm for (unweighted) interval scheduling problem.
     *
     * @param start start times for requests, i.e., <code>start[i]</code> is the
     * start time of the <code>i</code>th request
     * @param finish finish times for requests, i.e., <code>finish[i]</code> is
     * the finish time of the <code>i</code>th request. Moreover, the requests
     * are assumed to be ordered by increasing finish time
     *
     * @return largest compatible subset of given requests
     *
     * @throws IllegalArgumentException if <code>start</code> and
     * <code>finish</code> have different length
     */
    public static boolean[] intervalScheduleR(float[] start, float[] finish) {
        validateRequests(start, finish);

        // order requests by earliest finish time
        List<Integer> requests = makeOrderedRequests(finish);

        // schedule requests
        boolean[] schedule = new boolean[requests.size()];
        schedule[0] = true;
        intervalScheduleR(start, finish, requests, schedule, 0, 1);

        return schedule;
    }

    // Helper method.
    private static void intervalScheduleR(float[] start, float[] finish, List<Integer> requests, boolean[] schedule, int i, int j) {
        if (j < requests.size()) {
            // more requests to check
            if (finish[requests.get(i)] <= start[requests.get(j)]) {
                // requests are compatable
                schedule[requests.get(j)] = true;
                intervalScheduleR(start, finish, requests, schedule, j, j + 1);
            } else {
                // requests are not compatable
                schedule[requests.get(j)] = false;
                intervalScheduleR(start, finish, requests, schedule, i, j + 1);
            }
        }
    }

    /**
     * Dynamic programming algorithm for (weighted) interval scheduling
     * problem.
     *
     * @param start start times for requests, i.e., <code>start[i]</code> is the
     * start time of request <code>i</code>
     * @param finish finish times for requests, i.e., <code>finish[i]</code> is
     * the finish time of request <code>i</code>
     * @param weight weight for requests, i.e., <code>weight[i]</code> is the
     * weight / payoff for scheduling request <code>i</code>
     *
     * @return compatible subset of given requests with maximum weight
     */
    public static boolean[] intervalSchedule(float[] start, float[] finish, float[] weight) {
        // TODO: implement
        throw new RuntimeException("method not implemented");
    }

    /**
     * Greedy algorithm for solving the interval partition problem: schedule all
     * requests so that the fewest number of resources are used.
     *
     * @param start array of start times. <code>start[i]</code> is the start
     * time of request <code>i</code>
     * @param finish array of finish times. <code>finish[i]</code> is the finish
     * time of request <code>i</code>
     *
     * @return resource assignment for all requests where the value at index
     * <code>i</code> is the resource assigned to request <code>i</code>
     *
     * @throws IllegalArgumentException if <code>start</code> and
     * <code>finish</code> have different length
     */
    public static int[] intervalPartition(float[] start, float[] finish) {
        validateRequests(start, finish);

        // order requests by start time
        List<Integer> requests = makeOrderedRequests(start);

        // partition requests
        int[] partition = new int[requests.size()];
        for (int i = 0; i < requests.size(); i++) {
            // assign resource to next request
            int k = 0;
            for (int j = 0; j < i; j++) {
                if (start[requests.get(i)] <= finish[requests.get(j)] && k == partition[requests.get(j)]) {
                    k++;
                }
            }
            partition[requests.get(i)] = k;
        }

        return partition;
    }

    /**
     * Greedy algorithm for minimum lateness problem.
     *
     * @param deadline deadlines for requests, i.e., <code>deadline[i]</code> is
     * the deadline for the <code>i</code>th request.
     * @param processing processing time for requests, i.e.,
     * <code>processing[i]</code> is the time to process the <code>i</code>th
     * request.
     *
     * @return start times for schedule with minimum (maximum) lateness.
     */
    public static float[] minLateness(float[] deadline, float[] processing) {
        // TODO: sort requests by earliest deadline
        int n = deadline.length;
        float[] start = new float[n];
        // request 0 starts at 0
        start[0] = 0;
        for (int i = 1; i < n; i++) {
            // request i starts when request i - 1 finishes
            start[i] = start[i - 1] + processing[i - 1];
        }
        return start;
    }

    /*
     * Helper method. Finds index of request with maximum lateness for a given
     * schedule.
     */
    public static int maxLateness(float[] deadline, float[] processing, float[] start) {
        int n = start.length;
        int i = 0;
        float max = start[0] + processing[0] - deadline[0];
        for (int j = 1; j < n; j++) {
            // lateness for request j
            float tmp = start[j] + processing[j] - deadline[j];
            if (max < tmp) {
                // lateness for request i < lateness for request j
                i = j;
                max = tmp;
            }
        }
        return i;
    }

    // Validate start and finish arrays have same length
    private static void validateRequests(float[] start, float[] finish) {
        if (start.length != finish.length) {
            throw new IllegalArgumentException();
        }
    }

    /*
     * Helper method. Makes a list of requests numbered 0 to size - 1 and
     * ordered by the given array of times for each request.
     */
    private static List<Integer> makeOrderedRequests(float[] times) {
        List<Integer> requests = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            requests.add(i);
        }
        requests.sort(new OrderByTimes(times));
        return requests;
    }

    // Helper class for ordering requests by a given array of times.
    private static class OrderByTimes implements Comparator<Integer> {
        private float[] times;

        public OrderByTimes(float[] times) {
            this.times = times;
        }

        @Override
        public int compare(Integer i, Integer j) {
            if (0 <= Math.min(i, j) && Math.max(i, j) < times.length) {
                return Float.compare(times[i], times[j]);
            } else {
                throw new AssertionError();
            }
        }
    }
}
