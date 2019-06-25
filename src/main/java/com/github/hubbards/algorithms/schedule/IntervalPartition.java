package com.github.hubbards.algorithms.schedule;

import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO: document
 *
 * @author Spencer Hubbard
 */
public class IntervalPartition {
    private Map<IntervalRequest, String> resourceMap;

    // Greedy algorithm for solving the interval partition problem.
    private IntervalPartition(SortedSet<IntervalRequest> requests) {
        resourceMap = new HashMap<IntervalRequest, String>();

        Set<IntervalRequest> others = new HashSet<IntervalRequest>();
        for (IntervalRequest request : requests) {
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
     * TODO: document
     *
     * @return
     */
    public int size() {
        return (int) resourceMap.values().stream().distinct().count();
    }

    /**
     * TODO: document
     *
     * @param request
     *
     * @return
     */
    public boolean contains(IntervalRequest request) {
        return resourceMap.containsKey(request);
    }

    /**
     * TODO: document
     *
     * @param request1
     * @param request2
     *
     * @return
     */
    public boolean sameResource(IntervalRequest request1, IntervalRequest request2) {
        if (resourceMap.containsKey(request1) && resourceMap.containsKey(request2)) {
            return resourceMap.get(request1).equals(resourceMap.get(request2));
        } else {
            return false;
        }
    }

    /**
     * TODO: document
     */
    public static class Builder {
        private SortedSet<IntervalRequest> requests;

        /**
         * TODO: document
         */
        public Builder() {
            requests = new TreeSet<IntervalRequest>(
                    new Comparator<IntervalRequest>() {
                        @Override
                        public int compare(IntervalRequest r1, IntervalRequest r2) {
                            return r1.getStart().compareTo(r2.getStart());
                        }
                    }
            );
        }

        /**
         * TODO: document
         *
         * @param request
         */
        public void addRequest(IntervalRequest request) {
            checkNotNull(request);
            checkArgument(!requests.contains(request), "request already added");

            requests.add(request);
        }

        /**
         * TODO: document
         *
         * @return
         */
        public IntervalPartition build() {
            return new IntervalPartition(requests);
        }
    }
}
