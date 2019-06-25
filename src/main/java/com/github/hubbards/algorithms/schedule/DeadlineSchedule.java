package com.github.hubbards.algorithms.schedule;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO: document
 *
 * @author Spencer Hubbard
 */
public final class DeadlineSchedule {
    private DeadlineSchedule() {
        throw new AssertionError("static class");
    }

    /**
     * Greedy algorithm for deadline scheduling problem.
     *
     * @param requests deadline requests ordered by duration
     * @param start start time of the first request
     *
     * @return a map from request name to start time
     */
    public static Map<String, Instant> schedule(SortedSet<DeadlineRequest> requests, Instant start) {
        checkNotNull(requests);
        checkNotNull(start);

        Map<String, Instant> schedule = new HashMap<String, Instant>();

        for (DeadlineRequest request : requests) {
            schedule.put(request.getName(), start);
            start = start.plus(request.getDuration());
        }

        return schedule;
    }
}
