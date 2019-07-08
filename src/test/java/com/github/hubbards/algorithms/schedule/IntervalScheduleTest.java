package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

/**
 * This class is a simple test suite for {@link IntervalSchedule}.
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

    @Test
    public void testLargeInstance() {
        IntervalRequest request1 = new IntervalRequest(
                "name-1",
                Instant.ofEpochSecond(1),
                Instant.ofEpochSecond(4)
        );
        IntervalRequest request2 = new IntervalRequest(
                "name-2",
                Instant.ofEpochSecond(3),
                Instant.ofEpochSecond(5)
        );
        IntervalRequest request3 = new IntervalRequest(
                "name-3",
                Instant.ofEpochSecond(0),
                Instant.ofEpochSecond(6)
        );
        IntervalRequest request4 = new IntervalRequest(
                "name-4",
                Instant.ofEpochSecond(4),
                Instant.ofEpochSecond(7)
        );
        IntervalRequest request5 = new IntervalRequest(
                "name-5",
                Instant.ofEpochSecond(3),
                Instant.ofEpochSecond(8)
        );
        IntervalRequest request6 = new IntervalRequest(
                "name-6",
                Instant.ofEpochSecond(5),
                Instant.ofEpochSecond(9)
        );
        IntervalRequest request7 = new IntervalRequest(
                "name-7",
                Instant.ofEpochSecond(6),
                Instant.ofEpochSecond(10)
        );
        IntervalRequest request8 = new IntervalRequest(
                "name-8",
                Instant.ofEpochSecond(8),
                Instant.ofEpochSecond(11)
        );

        IntervalSchedule.Builder builder = new IntervalSchedule.Builder();
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);
        builder.addRequest(request4);
        builder.addRequest(request5);
        builder.addRequest(request6);
        builder.addRequest(request7);
        builder.addRequest(request8);

        IntervalSchedule schedule = builder.build();

        assertEquals(3, schedule.size());

        assertTrue(schedule.contains(request1));
        assertTrue(schedule.contains(request4));
        assertTrue(schedule.contains(request8));

        assertFalse(schedule.contains(request2));
        assertFalse(schedule.contains(request3));
        assertFalse(schedule.contains(request5));
        assertFalse(schedule.contains(request6));
        assertFalse(schedule.contains(request7));
    }
}
