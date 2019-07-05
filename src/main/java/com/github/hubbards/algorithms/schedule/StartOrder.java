package com.github.hubbards.algorithms.schedule;

import java.util.Comparator;

/**
 * StartOrder is an ordering on {@link IntervalRequest} by start time. Note
 * that this ordering is not consistent with equals.
 *
 * @author Spencer Hubbard
 */
public class StartOrder implements Comparator<IntervalRequest> {
    @Override
    public int compare(IntervalRequest request1, IntervalRequest request2) {
        return request1.getStart().compareTo(request2.getStart());
    }
}
