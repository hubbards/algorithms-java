package com.github.hubbards.algorithms.schedule;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import java.util.*;

import static com.google.common.base.Preconditions.*;

/**
 * TODO: document
 *
 * @author Spencer Hubbard
 */
public class IntervalSchedule {
    private Set<IntervalRequest> accepted;

    // Iterative greedy algorithm for (unweighted) interval scheduling problem.
    private IntervalSchedule(SortedSet<IntervalRequest> requests) {
        accepted = new HashSet<IntervalRequest>();

        PeekingIterator<IntervalRequest> itr = Iterators.peekingIterator(requests.iterator());
        while (itr.hasNext()) {
            IntervalRequest request = itr.next();
            accepted.add(request);
            while (itr.hasNext() && itr.peek().getStart().compareTo(request.getFinish()) < 0) {
                itr.next();
            }
        }
    }

    /**
     * TODO: document
     *
     * @return
     */
    public int size() {
        return accepted.size();
    }

    /**
     * TODO: document
     *
     * @param request
     *
     * @return
     */
    public boolean contains(IntervalRequest request) {
        return accepted.contains(request);
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
                            return r1.getFinish().compareTo(r2.getFinish());
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
        public IntervalSchedule build() {
            return new IntervalSchedule(requests);
        }
    }
}
