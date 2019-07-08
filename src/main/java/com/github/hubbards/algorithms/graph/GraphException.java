package com.github.hubbards.algorithms.graph;

/**
 * The class <code>GraphException</code> is a <code>RuntimeException</code>
 * that indicates violation of precondition of a graph, e.g., loop or multiple
 * edge.
 *
 * @author Spencer Hubbard
 */
@SuppressWarnings("serial")
public class GraphException extends RuntimeException {
    /**
     * Construct graph exception object.
     */
    public GraphException() {
        super();
    }

    /**
     * Construct graph exception object with given error message.
     *
     * @param message the given message.
     */
    public GraphException(String message) {
        super(message);
    }
}
