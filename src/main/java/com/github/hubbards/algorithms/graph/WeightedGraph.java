package com.github.hubbards.algorithms.graph;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * WeightedGraph represents a directed or undirected graph where each edge has
 * a weight (or cost).
 *
 * @author Spencer Hubbard
 */
public abstract class WeightedGraph implements Graph {
    // Default cost of an edge.
    protected static final double DEFAULT_COST = 0;

    /**
     * Adds an edge to this graph with given end-points and cost.
     *
     * @param tail the name of the tail of the given edge, if this graph is
     *             directed, otherwise the name of one end-point.
     * @param head the name of the head of the given edge, if this graph is
     *             directed, otherwise the name of the other end-point.
     * @param cost the cost of the given edge.
     */
    public abstract void addWeightedEdge(String tail, String head, double cost);

    @Override
    public void addEdge(String tail, String head) {
        addWeightedEdge(tail, head, DEFAULT_COST);
    }
}
