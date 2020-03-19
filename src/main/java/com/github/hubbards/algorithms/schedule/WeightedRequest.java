package com.github.hubbards.algorithms.schedule;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * WeightedRequest represents an weighted request to use a resource.
 *
 * @author Spencer Hubbard
 */
public class WeightedRequest implements Weighted {
    private String name;
    private int weight;

    /**
     * Constructs a new weighted request with a name and weight.
     *
     * @param name name of the request.
     * @param weight weight of the request.
     *
     * @throws NullPointerException if any parameter is null.
     * @throws IllegalArgumentException if weight is negative.
     */
    public WeightedRequest(String name, int weight) {
        checkNotNull(name);
        checkArgument(weight >= 0, "weight must be non-negative");

        this.name = name;
        this.weight = weight;
    }

    /**
     * Getter for name.
     *
     * @return name of this request.
     */
    public String getName() {
        return name;
    }

    @Override
    public int getWeight() {
        return weight;
    }
}
