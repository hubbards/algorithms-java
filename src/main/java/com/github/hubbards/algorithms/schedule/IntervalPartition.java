package com.github.hubbards.algorithms.schedule;

import java.util.*;

import static com.google.common.base.Preconditions.*;

/**
 * IntervalPartition represents a solution to the interval partition problem:
 * given a collection of interval requests, schedule all requests so that the
 * fewest number of resources are used.
 * <p>
 * A solution is found using a greedy algorithm where the requests are ordered
 * by start time.
 *
 * @author Spencer Hubbard
 */
public class IntervalPartition {
    // map from request to resource label
    private Map<IntervalRequest, String> resourceMap;

    // Greedy algorithm for solving the interval partition problem.
    private IntervalPartition(PriorityQueue<IntervalRequest> requests) {
        resourceMap = new HashMap<IntervalRequest, String>();

        Set<IntervalRequest> others = new HashSet<IntervalRequest>();
        while (!requests.isEmpty()) {
            IntervalRequest request = requests.poll();

            Set<String> used = new HashSet<String>();
            for (IntervalRequest key : resourceMap.keySet()) {
                used.add(key.getName());
            }

            for (IntervalRequest other : others) {
                if (request.getStart().compareTo(other.getFinish()) < 0) {
                    used.remove(resourceMap.get(other));
                }
            }

            String label = request.getName();
            if (!used.isEmpty()) {
                label = used.iterator().next();
            }

            resourceMap.put(request, label);
            others.add(request);
        }
    }

    /**
     * Counts the number of resources used in this solution.
     *
     * @return the number of resources used in this solution.
     */
    public int size() {
        return (int) resourceMap.values().stream().distinct().count();
    }

    /**
     * Checks if this solution has scheduled a given request.
     *
     * @param request the request to check for.
     *
     * @return <code>true</code> if this solution has scheduled the given
     * request, otherwise <code>false</code>.
     *
     * @throws NullPointerException if the given request is null.
     */
    public boolean contains(IntervalRequest request) {
        checkNotNull(request);

        return resourceMap.containsKey(request);
    }

    /**
     * Check if this solution has scheduled two given requests on the same
     * resource.
     *
     * @param request1 one request to check for.
     * @param request2 another request to check for.
     *
     * @return <code>true</code> if this solution has scheduled the two requests
     * on the same resource, otherwise <code>false</code>.
     *
     * @throws NullPointerException if either of the given requests are null.
     */
    public boolean sameResource(IntervalRequest request1, IntervalRequest request2) {
        checkNotNull(request1);
        checkNotNull(request2);

        if (resourceMap.containsKey(request1) && resourceMap.containsKey(request2)) {
            return resourceMap.get(request1).equals(resourceMap.get(request2));
        } else {
            return false;
        }
    }

    /**
     * Builder is a builder for an instance of the interval partition problem.
     */
    public static class Builder {
        private PriorityQueue<IntervalRequest> requests;

        /**
         * Constructs a new builder.
         */
        public Builder() {
            requests = new PriorityQueue<IntervalRequest>(
                    new Comparator<IntervalRequest>() {
                        @Override
                        public int compare(IntervalRequest r1, IntervalRequest r2) {
                            return r1.getStart().compareTo(r2.getStart());
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
        public IntervalPartition build() {
            return new IntervalPartition(requests);
        }
    }
}
