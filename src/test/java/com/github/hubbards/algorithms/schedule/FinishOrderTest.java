package com.github.hubbards.algorithms.schedule;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

/**
 * This class is a simple test suite for {@link FinishOrder}.
 *
 * @author Spencer Hubbard
 */
public class FinishOrderTest {
    private FinishOrder order;

    @Before
    public void setup() {
        order = new FinishOrder();
    }

    @Test
    public void testCompareSameStartAndEarlierFinish() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request with earlier finish time should be less than",
                -1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareEarlierStartAndFinish() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request with earlier finish time should be less than",
                -1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareLaterStartAndEarlierFinish() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(2)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.EPOCH,
                Instant.ofEpochSecond(3)
        );

        assertEquals(
                "request with earlier finish time should be less than",
                -1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareSameStartAndFinish() {
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

        assertEquals(
                "request with same finish time should be equal to",
                0,
                order.compare(request1, request2)
        );
    }

    @Test
    public void testCompareEarlierStartAndSameFinish() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request with same finish time should be equal to",
                0,
                order.compare(request1, request2)
        );
    }

    @Test
    public void testCompareLaterStartAndSameFinish() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(2)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request with same finish time should be equal to",
                0,
                order.compare(request1, request2)
        );
    }

    @Test
    public void testCompareSameStartAndLaterFinish() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(
                "request with later finish time should be greater than",
                1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareEarlierStartAndLaterFinish() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.EPOCH,
                Instant.ofEpochSecond(3)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request with later finish time should be greater than",
                1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    @Test
    public void testCompareLaterStartAndFinish() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(3)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                "request with later finish time should be greater than",
                1,
                Integer.signum(order.compare(request1, request2))
        );
    }

    // test compare contract
    // see https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html#compare-T-T-

    @Test
    public void testCompareFlipSign() {
        IntervalRequest x = new IntervalRequest(
                "x",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest y = new IntervalRequest(
                "y",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );

        assertEquals(
                Integer.signum(order.compare(x, y)),
                -1 * Integer.signum(order.compare(y, x))
        );
    }

    @Test
    public void testCompareIsTransitive() {
        IntervalRequest x = new IntervalRequest(
                "x",
                Instant.EPOCH,
                Instant.ofEpochSecond(3)
        );
        IntervalRequest y = new IntervalRequest(
                "y",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );
        IntervalRequest z = new IntervalRequest(
                "z",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertTrue(order.compare(x, y) > 0);
        assertTrue(order.compare(y, z) > 0);
        assertTrue(order.compare(x, z) > 0);
    }

    @Test
    public void testCompareEqualToBothLessThan() {
        IntervalRequest x = new IntervalRequest(
                "x",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest y = new IntervalRequest(
                "y",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );
        IntervalRequest z = new IntervalRequest(
                "z",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );

        assertEquals(0, order.compare(x, y));
        assertEquals(
                Integer.signum(order.compare(x, z)),
                Integer.signum(order.compare(y, z))
        );
    }

    @Test
    public void testCompareEqualToBothGreaterThan() {
        IntervalRequest x = new IntervalRequest(
                "x",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );
        IntervalRequest y = new IntervalRequest(
                "y",
                Instant.EPOCH,
                Instant.ofEpochSecond(2)
        );
        IntervalRequest z = new IntervalRequest(
                "z",
                Instant.EPOCH,
                Instant.ofEpochSecond(1)
        );

        assertEquals(0, order.compare(x, y));
        assertEquals(
                Integer.signum(order.compare(x, z)),
                Integer.signum(order.compare(y, z))
        );
    }
}
