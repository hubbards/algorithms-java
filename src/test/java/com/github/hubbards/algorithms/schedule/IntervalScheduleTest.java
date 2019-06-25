package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

/**
 * TODO: document
 *
 * @author Spencer Hubbard
 */
public class IntervalScheduleTest {
    @Test
    public void testNoRequest() {
        IntervalSchedule schedule = new IntervalSchedule.Builder().build();

        assertEquals(0, schedule.size());
    }

    @Test
    public void testOneRequest() {
        IntervalRequest request = new IntervalRequest(
                "name",
                Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(1)
        );

        IntervalSchedule.Builder builder = new IntervalSchedule.Builder();
        builder.addRequest(request);

        IntervalSchedule schedule = builder.build();

        assertEquals(1, schedule.size());
        assertTrue(schedule.contains(request));
    }

    @Test
    public void testOrderedByStartTime() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(5)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(2)
        );
        IntervalRequest request3 = new IntervalRequest(
                "name-3",
                Instant.ofEpochSecond(3),
                Instant.ofEpochSecond(4)
        );

        IntervalSchedule.Builder builder = new IntervalSchedule.Builder();
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);

        IntervalSchedule schedule = builder.build();

        assertEquals(2, schedule.size());
        assertFalse(schedule.contains(request1));
        assertTrue(schedule.contains(request2));
        assertTrue(schedule.contains(request3));
    }

    @Test
    public void testOrderedByDuration() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(3)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(4),
                Instant.ofEpochSecond(7)
        );
        IntervalRequest request3 = new IntervalRequest(
                "name-3",
                Instant.ofEpochSecond(2),
                Instant.ofEpochSecond(6)
        );

        IntervalSchedule.Builder builder = new IntervalSchedule.Builder();
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);

        IntervalSchedule schedule = builder.build();

        assertEquals(2, schedule.size());
        assertTrue(schedule.contains(request1));
        assertTrue(schedule.contains(request2));
        assertFalse(schedule.contains(request3));
    }

    @Test
    public void testOrderedByFinishTime() {
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

        IntervalSchedule.Builder builder = new IntervalSchedule.Builder();
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);

        IntervalSchedule schedule = builder.build();

        assertEquals(2, schedule.size());
        assertTrue(schedule.contains(request1));
        assertFalse(schedule.contains(request2));
        assertTrue(schedule.contains(request3));
    }
}
