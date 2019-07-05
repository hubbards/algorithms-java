package com.github.hubbards.algorithms.schedule;

import java.util.*;

import static com.google.common.base.Preconditions.*;

/**
 * IntervalSchedule represents a solution to the (unweighted) interval
 * scheduling problem: given a collection of interval requests, schedule a
 * maximum number of requests for a single resource.
 * <p>
 * A solution is found using a greedy algorithm where the requests are ordered
 * by finish time.
 *
 * @author Spencer Hubbard
 */
public class IntervalSchedule {
    // set of accepted requests
    private Set<IntervalRequest> accepted;

    // Greedy algorithm for (unweighted) interval scheduling problem.
    private IntervalSchedule(PriorityQueue<IntervalRequest> requests) {
        accepted = new HashSet<IntervalRequest>();

        while (!requests.isEmpty()) {
            IntervalRequest request = requests.poll();
            accepted.add(request);
            while (!requests.isEmpty() && requests.peek().getStart().compareTo(request.getFinish()) < 0) {
                requests.poll();
            }
        }
    }

    /**
     * Counts the number of requests accepted by this solution.
     *
     * @return the number of requests accepted by this solution.
     */
    public int size() {
        return accepted.size();
    }

    /**
     * Checks if this solution accepted a given request.
     *
     * @param request the request to check for.
     *
     * @return <code>true</code> if this solution accepted the given request,
     * <code>false</code> otherwise.
     *
     * @throws NullPointerException if the given request is null.
     */
    public boolean contains(IntervalRequest request) {
        checkNotNull(request);

        return accepted.contains(request);
    }

    /**
     * Builder is a builder for an instance of the (unweighted) interval
     * scheduling problem.
     */
    public static class Builder {
        private PriorityQueue<IntervalRequest> requests;

        /**
         * Constructs a new builder.
         */
        public Builder() {
            requests = new PriorityQueue<>(
                    new Comparator<IntervalRequest>() {
                        @Override
                        public int compare(IntervalRequest r1, IntervalRequest r2) {
                            return r1.getFinish().compareTo(r2.getFinish());
                        }
                    }
            );
        }

        /**
         * Checks if this builder contains a given request.
         *
         * @param request the request to check for.
         *
         * @return <code>true</code> if this builder contains the given request,
         * <code>false</code> otherwise.
         *
         * @throws NullPointerException if the given request is null.
         */
        public boolean hasRequest(IntervalRequest request) {
            checkNotNull(request);

            return requests.contains(request);
        }

        /**
         * Adds a given request to this builder.
         *
         * @param request the request to add.
         *
         * @throws NullPointerException if the given request is null.
         * @throws IllegalArgumentException if the given request has already
         * been added to this builder.
         */
        public void addRequest(IntervalRequest request) {
            checkNotNull(request);
            checkArgument(!requests.contains(request), "request already added");

            requests.add(request);
        }

        /**
         * Builds a solution to the instance of the interval partition problem
         * represented by this builder.
         *
         * @return a solution to the instance of the interval partition problem.
         */
        public IntervalSchedule build() {
            return new IntervalSchedule(requests);
        }
    }
}
