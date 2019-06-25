package com.github.hubbards.algorithms.schedule;

import java.time.Duration;
import java.time.Instant;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO: document
 *
 * @author Spencer Hubbard
 */
public class DeadlineRequest implements Comparable<DeadlineRequest> {
    private String name;
    private Duration duration;
    private Instant deadline;

    /**
     * Constructs a new deadline request with a name, duration, and deadline.
     *
     * @param name name of the request
     * @param duration duration of the request
     * @param deadline deadline of the request
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
     * @return name of this request
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for duration.
     *
     * @return duration of this request
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Getter for deadline of this request.
     *
     * @return deadline of this request
     */
    public Instant getDeadline() {
        return deadline;
    }

    /**
     * Computes earliness if this request is completed at a given finish time.
     *
     * @param finish time when this request is completed
     *
     * @return earliness of this request. The result is negative if the request
     * is late, i.e., finished after the deadline.
     */
    public Duration earliness(Instant finish) {
        return Duration.between(deadline, finish);
    }

    /**
     * Computes lateness if this request is completed at a given finish time.
     *
     * @param finish time when this request is completed
     *
     * @return lateness of this request. The result is negative if the request
     * is early, i.e., finished before the deadline.
     */
    public Duration lateness(Instant finish) {
        return earliness(finish).negated();
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

    @Override
    public int compareTo(DeadlineRequest other) {
        return duration.compareTo(other.duration);
    }
}
