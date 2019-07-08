package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

/**
 * This class is a simple test suite for {@link IntervalPartition}.
 *
 * @author Spencer Hubbard
 */
public class IntervalPartitionTest {
    @Test
    public void testNoRequest() {
        IntervalPartition partition = new IntervalPartition.Builder().build();

        assertEquals(0, partition.size());
    }

    @Test
    public void testOneRequest() {
        IntervalRequest request = new IntervalRequest(
                "name",
                Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(1)
        );

        IntervalPartition.Builder builder = new IntervalPartition.Builder();
        builder.addRequest(request);

        IntervalPartition partition = builder.build();

        assertEquals(1, partition.size());

        assertTrue(partition.contains(request));
    }

    @Test
    public void testAllIncompatibleRequests() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(3)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(4)
        );
        IntervalRequest request3 = new IntervalRequest(
                "name-3",
                Instant.ofEpochSecond(2),
                Instant.ofEpochSecond(5)
        );

        IntervalPartition.Builder builder = new IntervalPartition.Builder();
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);

        IntervalPartition partition = builder.build();

        assertEquals(3, partition.size());

        assertTrue(partition.contains(request1));
        assertTrue(partition.contains(request2));
        assertTrue(partition.contains(request3));

        assertFalse(partition.sameResource(request1, request2));
        assertFalse(partition.sameResource(request1, request3));
        assertFalse(partition.sameResource(request2, request3));
    }

    @Test
    public void testAllCompatibleRequests() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(1)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(2),
                Instant.ofEpochSecond(3)
        );
        IntervalRequest request3 = new IntervalRequest(
                "name-3",
                Instant.ofEpochSecond(4),
                Instant.ofEpochSecond(5)
        );

        IntervalPartition.Builder builder = new IntervalPartition.Builder();
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);

        IntervalPartition partition = builder.build();

        assertEquals(1, partition.size());

        assertTrue(partition.contains(request1));
        assertTrue(partition.contains(request2));
        assertTrue(partition.contains(request3));

        assertTrue(partition.sameResource(request1, request2));
        assertTrue(partition.sameResource(request1, request3));
        assertTrue(partition.sameResource(request2, request3));
    }

    @Test
    public void testMixedRequests() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(2)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(4)
        );
        IntervalRequest request3 = new IntervalRequest(
                "name-3",
                Instant.ofEpochSecond(3),
                Instant.ofEpochSecond(5)
        );

        IntervalPartition.Builder builder = new IntervalPartition.Builder();
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);

        IntervalPartition partition = builder.build();

        assertEquals(2, partition.size());

        assertTrue(partition.contains(request1));
        assertTrue(partition.contains(request2));
        assertTrue(partition.contains(request3));

        assertFalse(partition.sameResource(request1, request2));
        assertTrue(partition.sameResource(request1, request3));
        assertFalse(partition.sameResource(request2, request3));
    }
}
