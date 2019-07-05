package com.github.hubbards.algorithms.schedule;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static com.google.common.base.Preconditions.*;

/**
 * DeadlineSchedule represents a solution to the minimum lateness problem: given
 * a collection of interval requests, schedule all requests for a single
 * resource so that the maximum lateness of any request is minimized.
 * <p>
 * A solution is found using a greedy algorithm where the requests are ordered
 * by deadline.
 *
 * @author Spencer Hubbard
 */
public class DeadlineSchedule {
    private Duration maxLateness;
    private Map<DeadlineRequest, Instant> startMap;

    // Greedy algorithm for the minimum lateness problem.
    private DeadlineSchedule(Instant start, PriorityQueue<DeadlineRequest> requests) {
        checkNotNull(start);
        checkNotNull(requests);

        maxLateness = null;
        startMap = new HashMap<DeadlineRequest, Instant>();

        while (!requests.isEmpty()) {
            DeadlineRequest request = requests.poll();

            startMap.put(request, start);

            Duration lateness = request.lateness(start);
            if (maxLateness == null || maxLateness.compareTo(lateness) < 0) {
                maxLateness = lateness;
            }

            start = request.finish(start);
        }
    }

    /**
     * Finds the maximum lateness of any request scheduled in this solution.
     *
     * @return the maximum lateness of any request or null if there are no
     * requests scheduled.
     */
    public Duration maxLateness() {
        return maxLateness;
    }

    /**
     * Checks if this solution has scheduled a given request.
     *
     * @param request the request to check for.
     *
     * @return <code>true</code> if this solution accepted the given request,
     * <code>false</code> otherwise.
     *
     * @throws NullPointerException if the given request is null.
     */
    public boolean contains(DeadlineRequest request) {
        checkNotNull(request);

        return startMap.containsKey(request);
    }

    /**
     * Finds the start time of a given request in this solution.
     *
     * @param request the request to find.
     *
     * @return the start time of the given request.
     *
     * @throws NullPointerException if the given request is null.
     * @throws IllegalArgumentException if the given request is not scheduled
     * in this solution.
     */
    public Instant start(DeadlineRequest request) {
        checkNotNull(request);
        checkArgument(startMap.containsKey(request), "request not scheduled");

        return startMap.get(request);
    }

    /**
     * Builder is a builder for an instance of the minimum lateness problem.
     */
    public static class Builder {
        public static final Instant DEFAULT_START = Instant.EPOCH;

        private Instant start;
        private PriorityQueue<DeadlineRequest> requests;

        /**
         * Constructs a new builder.
         */
        public Builder() {
            start = DEFAULT_START;
            requests = new PriorityQueue<DeadlineRequest>(
                    new Comparator<DeadlineRequest>() {
                        @Override
                        public int compare(DeadlineRequest r1, DeadlineRequest r2) {
                            return r1.getDeadline().compareTo(r2.getDeadline());
                        }
                    }
            );
        }

        /**
         * Getter for start time.
         *
         * @return start time of this builder.
         */
        public Instant getStart() {
            return start;
        }

        /**
         * Setter for start time.
         *
         * @param start the start time.
         *
         * @throws NullPointerException if the given start time is null.
         */
        public void setStart(Instant start) {
            checkNotNull(start);

            this.start = start;
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
        public boolean hasRequest(DeadlineRequest request) {
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
        public void addRequest(DeadlineRequest request) {
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
        public DeadlineSchedule build() {
            return new DeadlineSchedule(start, requests);
        }
    }
}
