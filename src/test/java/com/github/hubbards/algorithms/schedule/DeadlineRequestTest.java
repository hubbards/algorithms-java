package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.*;

/**
 * DeadlineRequestTest represents a simple test suite for {@link
 * DeadlineRequest}.
 *
 * @author Spencer Hubbard
 */
public class DeadlineRequestTest {
    @Test
    public void testGetName() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request should have given name",
                "name",
                request.getName()
        );
    }

    @Test
    public void testGetDuration() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request should have given duration",
                Duration.ofSeconds(1),
                request.getDuration()
        );
    }

    @Test
    public void testGetDeadline() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request should have given deadline",
                Instant.ofEpochSecond(1),
                request.getDeadline()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsNonPositiveDuration() {
        new DeadlineRequest(
                "name",
                Duration.ofSeconds(1).negated(),
                Instant.EPOCH
        );
    }

    @Test
    public void testFinish() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(
                "request should have finish equal to the sum of start and duration",
                Instant.ofEpochSecond(3),
                request.finish(Instant.ofEpochSecond(2))
        );
    }

    @Test
    public void testEarlinessBeforeDeadline() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request finishing before deadline should have positive earliness",
                Duration.ofSeconds(1),
                request.earliness(Instant.EPOCH)
        );
    }

    @Test
    public void testEarlinessAfterDeadline() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(
                "request finishing after deadline should have negative earliness",
                Duration.ofSeconds(1).negated(),
                request.earliness(Instant.EPOCH)
        );
    }

    @Test
    public void testEarlinessOnDeadline() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request finishing on deadline should have zero earliness",
                Duration.ZERO,
                request.earliness(Instant.EPOCH)
        );
    }

    @Test
    public void testLatenessBeforeDeadline() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request finishing before deadline should have negative lateness",
                Duration.ofSeconds(1).negated(),
                request.lateness(Instant.EPOCH)
        );
    }

    @Test
    public void testLatenessAfterDeadline() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(
                "request finishing after deadline should have positive lateness",
                Duration.ofSeconds(1),
                request.lateness(Instant.EPOCH)
        );
    }

    @Test
    public void testLatenessOnDeadline() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request finishing on deadline should have zero lateness",
                Duration.ZERO,
                request.lateness(Instant.EPOCH)
        );
    }

    @Test
    public void testSameNameEquals() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(1)
        );

        assertEquals(request1, request2);
    }

    // TODO: test equals, see https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-

    // TODO: test hashCode, see https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode--
}
