package com.github.hubbards.algorithms.schedule;

import java.time.Instant;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * WeightedIntervalRequest represents an weighted request to use a resource for
 * an interval of time.
 *
 * Two requests are compatible if their intervals don't overlap. Compatible
 * requests can be scheduled on the same resource.
 *
 * @author Spencer Hubbard
 */
class WeightedIntervalRequest extends IntervalRequest {
    private final int weight;

    /**
     * Constructs a new weighted interval request with a name, start time,
     * finish time, and weight.
     *
     * @param name name of the request.
     * @param start start time of the request.
     * @param finish finish time of the request.
     * @param weight weight of the request.
     *
     * @throws NullPointerException if any parameter is null.
     * @throws IllegalArgumentException if start time is greater than or equal
     * to finish time or weight is negative.
     */
    public WeightedIntervalRequest(String name, Instant start, Instant finish, int weight) {
        super(name, start, finish);

        checkArgument(weight >= 0, "weight must be non-negative");

        this.weight = weight;
    }

    /**
     * Getter for weight.
     *
     * @return weight of this request.
     */
    public int getWeight() {
        return weight;
    }
}
