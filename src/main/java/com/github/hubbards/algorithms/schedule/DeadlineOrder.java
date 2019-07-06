package com.github.hubbards.algorithms.schedule;

import java.util.Comparator;

/**
 * DeadlineOrder is an ordering on {@link DeadlineRequest} by deadline. Note
 * that this ordering is not consistent with equals.
 *
 * @author Spencer Hubbard
 */
class DeadlineOrder implements Comparator<DeadlineRequest> {
    @Override
    public int compare(DeadlineRequest request1, DeadlineRequest request2) {
        return request1.getDeadline().compareTo(request2.getDeadline());
    }
}
