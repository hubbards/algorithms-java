package com.github.hubbards.algorithms.schedule;

import java.time.Duration;
import java.time.Instant;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * DeadlineRequest represents a request to use a resource for a duration of time
 * and deadline.
 * <p>
 * A request can be scheduled on a resource for an interval of time. A scheduled
 * request is considered late if the finish time is greater than the deadline.
 * <p>
 * Two requests can be scheduled on the same resource if the intervals they are
 * scheduled for don't overlap.
 *
 * @author Spencer Hubbard
 */
class DeadlineRequest {
    private String name;
    private Duration duration;
    private Instant deadline;

    /**
     * Constructs a new deadline request with a name, duration, and deadline.
     *
     * @param name name of the request.
     * @param duration duration of the request.
     * @param deadline deadline of the request.
     *
     * @throws NullPointerException if any parameter is null.
     * @throws IllegalArgumentException if duration is not positive.
     */
    public DeadlineRequest(String name, Duration duration, Instant deadline) {
        checkNotNull(name);
        checkNotNull(duration);
        checkNotNull(deadline);
        checkArgument(!duration.isNegative(), "duration must be positive");

        this.name = name;
        this.duration = duration;
        this.deadline = deadline;
    }

    /**
     * Getter for name.
     *
     * @return name of this request.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for duration.
     *
     * @return duration of this request.
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Getter for deadline.
     *
     * @return deadline of this request.
     */
    public Instant getDeadline() {
        return deadline;
    }

    /**
     * Computes finish time if this request is started at a given time.
     *
     * @param start time when this request is started.
     *
     * @return finish time of this request.
     *
     * @throws NullPointerException if given start time is null.
     */
    public Instant finish(Instant start) {
        checkNotNull(start);

        return start.plus(getDuration());
    }

    /**
     * Computes earliness if this request is started at a given time.
     *
     * @param start time when this request is started.
     *
     * @return earliness of this request. The result is negative if the request
     * is late, i.e., finished after the deadline.
     *
     * @throws NullPointerException if given time is null.
     */
    public Duration earliness(Instant start) {
        checkNotNull(start);

        return Duration.between(finish(start), getDeadline());
    }

    /**
     * Computes lateness if this request is started at a given time.
     *
     * @param start time when this request is started.
     *
     * @return lateness of this request. The result is negative if the request
     * is early, i.e., finished before the deadline.
     *
     * @throws NullPointerException if given time is null.
     */
    public Duration lateness(Instant start) {
        checkNotNull(start);

        return earliness(start).negated();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DeadlineRequest) {
            DeadlineRequest other = (DeadlineRequest) o;
            return name.equals(other.name);
        } else {
            return false;
        }
    }
}
