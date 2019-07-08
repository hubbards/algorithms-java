package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

/**
 * This class is a simple test suite for {@link IntervalRequest}.
 *
 * @author Spencer Hubbard
 */
public class IntervalRequestTest {
    @Test
    public void testGetName() {
        IntervalRequest request = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request should have given name",
                "name",
                request.getName()
        );
    }

    @Test
    public void testGetStart() {
        IntervalRequest request = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request should have given start time",
                Instant.EPOCH,
                request.getStart()
        );
    }

    @Test
    public void testGetFinish() {
        IntervalRequest request = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request should have given finish time",
                Instant.ofEpochSecond(1),
                request.getFinish()
        );
    }

    @Test
    public void testEqualsSameName() {
        IntervalRequest request1 = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name",
                Instant.ofEpochSecond(1),
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
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertNotEquals(
                "request with different name should not be equal",
                request1,
                request2
        );
    }

    // test equals contract
    // see https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#equals-java.lang.Object-

    @Test
    public void testEqualsIsReflexive() {
        IntervalRequest x = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(x, x);
    }

    @Test
    public void testEqualsIsSymmetric() {
        IntervalRequest x = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest y = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(x, y);
        assertEquals(y, x);
    }

    @Test
    public void testEqualsIsTransitive() {
        IntervalRequest x = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest y = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest z = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(x, y);
        assertEquals(y, z);
        assertEquals(x, z);
    }

    @Test
    public void testEqualsIsConsistent() {
        IntervalRequest x = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest y = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(x, y);
        assertEquals(x, y);
    }

    @Test
    public void testNotEqualsIsConsistent() {
        IntervalRequest x = new IntervalRequest(
                "name-1",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest y = new IntervalRequest(
                "name-2",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertNotEquals(x, y);
        assertNotEquals(x, y);
    }

    @Test
    public void testNotEqualsNull() {
        IntervalRequest x = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertNotEquals(null, x);
    }

    // test hashCode contract
    // see https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#hashCode--

    @Test
    public void testHashCodeIsConsistent() {
        IntervalRequest x = new IntervalRequest(
                "name",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(x.hashCode(), x.hashCode());
    }

    @Test
    public void testHashCodeIsConsistentWithEquals() {
        IntervalRequest x = new IntervalRequest(
                "name",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(2)
        );
        IntervalRequest y = new IntervalRequest(
                "name",
                Instant.ofEpochSecond(2),
                Instant.ofEpochSecond(3)
        );

        assertEquals(x, y);
        assertEquals(x.hashCode(), y.hashCode());
    }
}
