package com.github.hubbards.algorithms.graph;

/**
 * Graph represents a directed or undirected graph.
 *
 * @author Spencer Hubbard
 */
public interface Graph {
    /**
     * Checks if this graph contains a given vertex.
     *
     * @param name the name of the given vertex.
     * @return <code>true</code> if this graph contains the given vertex,
     * otherwise <code>false</code>.
     */
    boolean containsVertex(String name);

    /**
     * Checks if this graph contains a given edge.
     *
     * @param tail the name of the tail of the given edge, if this graph is
     *             directed, otherwise the name of one end-point.
     * @param head the name of the head of the given edge, if this graph is
     *             directed, otherwise the name of the other end-point.
     * @return <code>true</code> if this graph contains the given edge,
     * otherwise <code>false</code>.
     */
    boolean containsEdge(String tail, String head);

    /**
     * Adds a given vertex to this graph.
     *
     * @param name the name of the given vertex.
     */
    void addVertex(String name);

    /**
     * Adds a given (directed) edge to this graph.
     *
     * @param tail the name of the tail of the given edge, if this graph is
     *             directed, otherwise the name of one end-point.
     * @param head the name of the head of the given edge, if this graph is
     *             directed, otherwise the name of the other end-point.
     */
    void addEdge(String tail, String head);
}
