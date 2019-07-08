package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.*;

/**
 * This class is a simple test suite for {@link DeadlineRequest}.
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
    public void testEqualsSameName() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request with same name should be equal",
                request1,
                request2
        );
    }

    @Test
    public void testNotEqualsDifferentName() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-2",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertNotEquals(
                "request with different name should not be equal",
                request1,
                request2
        );
    }

    // test equals
    // see https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-

    @Test
    public void testEqualsIsReflexive() {
        DeadlineRequest x = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(x, x);
    }

    @Test
    public void testEqualsIsSymmetric() {
        DeadlineRequest x = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest y = new DeadlineRequest(
                "name",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(1)
        );

        assertEquals(x, y);
        assertEquals(y, x);
    }

    @Test
    public void testEqualsIsTransitive() {
        DeadlineRequest x = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest y = new DeadlineRequest(
                "name",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest z = new DeadlineRequest(
                "name",
                Duration.ofSeconds(3),
                Instant.ofEpochSecond(2)
        );

        assertEquals(x, y);
        assertEquals(y, z);
        assertEquals(x, z);
    }

    @Test
    public void testEqualsIsConsistent() {
        DeadlineRequest x = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest y = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(x, y);
        assertEquals(x, y);
    }

    @Test
    public void testNotEqualsIsConsistent() {
        DeadlineRequest x = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest y = new DeadlineRequest(
                "name-2",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertNotEquals(x, y);
    }

    @Test
    public void testNotEqualsNull() {
        DeadlineRequest x = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertNotEquals(null, x);
    }

    // test hashCode
    // see https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode--

    @Test
    public void testHashCodeIsConsistent() {
        DeadlineRequest x = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(x.hashCode(), x.hashCode());
    }

    @Test
    public void testHashCodeIsConsistentWithEquals() {
        DeadlineRequest x = new DeadlineRequest(
                "name",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest y = new DeadlineRequest(
                "name",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(2)
        );

        assertEquals(x, y);
        assertEquals(x.hashCode(), y.hashCode());
    }
}
