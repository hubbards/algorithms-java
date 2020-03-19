package com.github.hubbards.algorithms.schedule;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * WeightedIntervalSchedule represents a solution to the weighted interval
 * scheduling problem: given a collection of interval requests, schedule a
 * subset of requests for a single resource with maximum total weight.
 * <p>
 * A solution is found using a dynamic programming algorithm.
 *
 * @author Spencer Hubbard
 */
public class WeightedIntervalSchedule {
    private List<WeightedIntervalRequest> requests;

    // Memoized solutions to sub-problems.
    private int[] memo;
    private int totalWeight;

    // TODO: replace with bottom-up construction
    // Dynamic programming algorithm for weighted interval scheduling problem.
    private WeightedIntervalSchedule(List<WeightedIntervalRequest> requests) {
        this.requests = requests;

        memo = new int[requests.size()];
        for (int i = 0; i < memo.length; i++) {
            memo[i] = -1;
        }

        totalWeight = computeOpt(memo.length - 1);
    }

    // TODO: replace with bottom-up construction
    private int computeOpt(int i) {
        if (i < 0) {
            return 0;
        }

        if (memo[i] == -1) {
            WeightedIntervalRequest request = requests.get(i);
            int j = i - 1;
            while (j >= 0 && request.getStart().compareTo(requests.get(j).getFinish()) < 0) {
                j--;
            }
            memo[i] = Math.max(request.getWeight() + computeOpt(j), computeOpt(i - 1));
        }

        return memo[i];
    }

    // TODO: document
    public int totalWeight() {
        return totalWeight;
    }

    // TODO: document
    public int size() {
        // TODO: implement
        throw new RuntimeException("not implemented");
    }

    // TODO: document
    public boolean contains(WeightedIntervalRequest request) {
        checkNotNull(request);

        // TODO: implement
        throw new RuntimeException("not implemented");
    }

    /**
     * Builder is a builder for an instance of the weighted interval
     * scheduling problem.
     */
    public static class Builder {
        private PriorityQueue<WeightedIntervalRequest> requests;

        /**
         * Constructs a new builder.
         */
        public Builder() {
            requests = new PriorityQueue<WeightedIntervalRequest>(new FinishOrder());
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
        public boolean hasRequest(WeightedIntervalRequest request) {
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
        public void addRequest(WeightedIntervalRequest request) {
            checkNotNull(request);
            checkArgument(!requests.contains(request), "request already added");

            requests.add(request);
        }

        /**
         * Builds a solution to the instance of the weighted interval scheduling
         * problem represented by this builder.
         *
         * @return a solution to the instance of the weighted interval
         * scheduling problem.
         */
        public WeightedIntervalSchedule build() {
            return new WeightedIntervalSchedule(new ArrayList<>(requests));
        }
    }
}
