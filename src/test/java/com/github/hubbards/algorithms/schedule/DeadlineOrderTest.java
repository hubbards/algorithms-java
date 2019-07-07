package com.github.hubbards.algorithms.schedule;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.*;

/**
 * This class is a simple test suite for {@link DeadlineOrder}.
 *
 * @author Spencer Hubbard
 */
public class DeadlineOrderTest {
    DeadlineOrder order;

    @Before
    public void setup() {
        order = new DeadlineOrder();
    }

    @Test
    public void testCompareSameDurationEarlierDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request with earlier deadline should be less than",
                -1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareShorterDurationEarlierDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request with earlier deadline should be less than",
                -1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareLongerDurationEarlierDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(2),
                Instant.EPOCH
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request with earlier deadline should be less than",
                -1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareSameDurationAndDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(
                "request with same deadline should be equal to",
                0,
                order.compare(request1, request2)
        );
    }

    @Test
    public void testCompareShorterDurationAndSameDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(2),
                Instant.EPOCH
        );

        assertEquals(
                "request with same deadline should be equal to",
                0,
                order.compare(request1, request2)
        );
    }

    @Test
    public void testCompareLongerDurationAndSameDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(2),
                Instant.EPOCH
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(
                "request with same deadline should be equal to",
                0,
                order.compare(request1, request2)
        );
    }

    @Test
    public void testCompareSameDurationLaterDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(
                "request with later deadline should be greater than",
                1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareShorterDurationLaterDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(2),
                Instant.EPOCH
        );

        assertEquals(
                "request with later deadline should be greater than",
                1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareLongerDurationLaterDeadline() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name-1",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(
                "request with later deadline should be greater than",
                1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    // test compare contract
    // see https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#compare-T-T-

    @Test
    public void testCompareFlipSign() {
        DeadlineRequest x = new DeadlineRequest(
                "x",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest y = new DeadlineRequest(
                "y",
                Duration.ofSeconds(2),
                Instant.EPOCH
        );

        assertEquals(
                Integer.signum(order.compare(x, y)),
                -1 * Integer.signum(order.compare(y, x))
        );
    }

    @Test
    public void testCompareIsTransitive() {
        DeadlineRequest x = new DeadlineRequest(
                "x",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(2)
        );
        DeadlineRequest y = new DeadlineRequest(
                "y",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest z = new DeadlineRequest(
                "z",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertTrue(order.compare(x, y) > 0);
        assertTrue(order.compare(y, z) > 0);
        assertTrue(order.compare(x, z) > 0);
    }

    @Test
    public void testCompareEqualToBothLessThan() {
        DeadlineRequest x = new DeadlineRequest(
                "x",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest y = new DeadlineRequest(
                "y",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );
        DeadlineRequest z = new DeadlineRequest(
                "z",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );

        assertEquals(0, order.compare(x, y));
        assertEquals(
                Integer.signum(order.compare(x, z)),
                Integer.signum(order.compare(y, z))
        );
    }

    @Test
    public void testCompareEqualToBothGreaterThan() {
        DeadlineRequest x = new DeadlineRequest(
                "x",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest y = new DeadlineRequest(
                "y",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(1)
        );
        DeadlineRequest z = new DeadlineRequest(
                "z",
                Duration.ofSeconds(1),
                Instant.EPOCH
        );

        assertEquals(0, order.compare(x, y));
        assertEquals(
                Integer.signum(order.compare(x, z)),
                Integer.signum(order.compare(y, z))
        );
    }
}
