package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.*;

/**
 * This class represents a simple test suite for {@link DeadlineSchedule}.
 *
 * @author Spencer Hubbard
 */
public class DeadlineScheduleTest {
    @Test
    public void testNoRequests() {
        DeadlineSchedule schedule = new DeadlineSchedule.Builder().build();

        assertNull(schedule.maxLateness());
    }

    @Test
    public void testOneRequest() {
        DeadlineRequest request = new DeadlineRequest(
                "name",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(1)
        );

        DeadlineSchedule.Builder builder = new DeadlineSchedule.Builder();
        builder.setStart(Instant.EPOCH);
        builder.addRequest(request);

        DeadlineSchedule schedule = builder.build();

        assertEquals(Duration.ofSeconds(1), schedule.maxLateness());
    }

    @Test
    public void testOrderedByDuration() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name1",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(3)
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name2",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(2)
        );

        DeadlineSchedule.Builder builder = new DeadlineSchedule.Builder();
        builder.setStart(Instant.EPOCH);
        builder.addRequest(request1);
        builder.addRequest(request2);

        DeadlineSchedule schedule = builder.build();

        assertEquals(Duration.ZERO, schedule.maxLateness());
        assertEquals(Instant.ofEpochSecond(2), schedule.start(request1));
        assertEquals(Instant.EPOCH, schedule.start(request2));
    }

    @Test
    public void testLargeInstance() {
        DeadlineRequest request1 = new DeadlineRequest(
                "name1",
                Duration.ofSeconds(3),
                Instant.ofEpochSecond(6)
        );
        DeadlineRequest request2 = new DeadlineRequest(
                "name2",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(8)
        );
        DeadlineRequest request3 = new DeadlineRequest(
                "name3",
                Duration.ofSeconds(1),
                Instant.ofEpochSecond(9)
        );
        DeadlineRequest request4 = new DeadlineRequest(
                "name4",
                Duration.ofSeconds(4),
                Instant.ofEpochSecond(9)
        );
        DeadlineRequest request5 = new DeadlineRequest(
                "name5",
                Duration.ofSeconds(3),
                Instant.ofEpochSecond(14)
        );
        DeadlineRequest request6 = new DeadlineRequest(
                "name6",
                Duration.ofSeconds(2),
                Instant.ofEpochSecond(15)
        );

        DeadlineSchedule.Builder builder = new DeadlineSchedule.Builder();
        builder.setStart(Instant.EPOCH);
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);
        builder.addRequest(request4);
        builder.addRequest(request5);
        builder.addRequest(request6);

        DeadlineSchedule schedule = builder.build();

        assertEquals(Duration.ofSeconds(1), schedule.maxLateness());
    }
}
