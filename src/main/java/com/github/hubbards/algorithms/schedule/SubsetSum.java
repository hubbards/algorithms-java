package com.github.hubbards.algorithms.schedule;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * SubsetSum represents a solution to the subset sum problem: given a collection
 * of weighted requests and a weight limit, schedule a subset of requests for
 * a single resource with maximum total weight that doesn't exceed the weight
 * limit. Note that the subset sum problem is a special case of the knapsack
 * problem.
 * <p>
 * A solution is found using a dynamic programming algorithm.
 *
 * @author Spencer Hubbard
 */
public class SubsetSum {
    private int weightLimit;
    private List<WeightedRequest> requests;

    // Memoized solutions to sub-problems.
    private int[][] memo;
    private int totalWeight;

    // Dynamic programming algorithm for subset sum problem.
    private SubsetSum(int weightLimit, List<WeightedRequest> requests) {
        this.weightLimit = weightLimit;
        this.requests = requests;

        memo = new int[this.requests.size()][this.weightLimit + 1];

        // NOTE unnecessary, default initialization
        for (int i = 0; i < memo.length; i++) {
            memo[i][0] = 0;
        }

        for (int limit = 1; limit <= this.weightLimit; limit++) {
            int weight = this.requests.get(0).getWeight();
            if (weight > limit) {
                memo[0][limit] = 0;
            } else {
                memo[0][limit] = weight;
            }
        }

        for (int i = 1; i < memo.length; i++) {
            int weight = this.requests.get(i).getWeight();
            for (int limit = 1; limit <= this.weightLimit; limit++) {
                if (weight > limit) {
                    memo[i][limit] = memo[i - 1][limit];
                } else {
                    memo[i][limit] = Math.max(
                            memo[i - 1][limit],
                            weight + memo[i - 1][limit - weight]
                    );
                }
            }
        }

        if (requests.size() > 0) {
            totalWeight = memo[memo.length - 1][this.weightLimit];
        } else {
            totalWeight = 0;
        }
    }

    // TODO document
    public int getTotalWeight() {
        return totalWeight;
    }

    /**
     * Builder is a builder for an instance of the subset sum problem.
     */
    public static class Builder {
        public static final int DEFAULT_WEIGHT_LIMIT = 0;

        private int weightLimit;
        private Set<WeightedRequest> requests;

        /**
         * Constructs a new builder with given weight limit.
         *
         * @param weightLimit the weight limit
         *
         * @throws IllegalArgumentException if weight limit is negative.
         */
        public Builder(int weightLimit) {
            checkArgument(weightLimit >= 0, "weight must be non-negative");

            this.weightLimit = weightLimit;
            requests = new HashSet<WeightedRequest>();
        }

        /**
         * Constructs a new builder with default weight limit.
         */
        public Builder() {
            this(DEFAULT_WEIGHT_LIMIT);
        }

        /**
         * Getter for weight limit.
         *
         * @return weight limit.
         */
        public int getWeightLimit() {
            return weightLimit;
        }

        /**
         * Setter for weight limit.
         *
         * @param weightLimit the weight limit
         *
         * @throws IllegalArgumentException if weight is negative.
         */
        public void setWeightLimit(int weightLimit) {
            checkArgument(weightLimit >= 0, "weight must be non-negative");

            this.weightLimit = weightLimit;
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
        public boolean hasRequest(WeightedRequest request) {
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
        public void addRequest(WeightedRequest request) {
            checkNotNull(request);
            checkArgument(!requests.contains(request), "request already added");

            requests.add(request);
        }

        /**
         * Builds a solution to the instance of the subset sum problem
         * represented by this builder.
         *
         * @return a solution to the instance of the subset sum problem.
         */
        public SubsetSum build() {
            return new SubsetSum(weightLimit, new ArrayList<>(requests));
        }
    }
}
