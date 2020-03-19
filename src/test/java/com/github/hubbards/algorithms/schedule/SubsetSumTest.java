package com.github.hubbards.algorithms.schedule;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class is a simple test suite for {@link SubsetSum}.
 *
 * @author Spencer Hubbard
 */
public class SubsetSumTest {
    @Test
    public void testNoRequest() {
        SubsetSum sum = new SubsetSum.Builder().build();

        assertEquals(0, sum.getTotalWeight());
    }

    @Test
    public void testOneRequest() {
        int weightLimit = 2;
        WeightedRequest request = new WeightedRequest("name", 1);

        SubsetSum.Builder builder = new SubsetSum.Builder(weightLimit);
        builder.addRequest(request);

        SubsetSum sum = builder.build();

        assertEquals(1, sum.getTotalWeight());
    }

    @Test
    public void testTotalAtLimit() {
        int weightLimit = 6;
        WeightedRequest request1 = new WeightedRequest("name", 2);
        WeightedRequest request2 = new WeightedRequest("name", 3);
        WeightedRequest request3 = new WeightedRequest("name", 4);

        SubsetSum.Builder builder = new SubsetSum.Builder(weightLimit);
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);

        SubsetSum sum = builder.build();

        assertEquals(6, sum.getTotalWeight());
    }

    @Test
    public void testTotalUnderLimit() {
        int weightLimit = 6;
        WeightedRequest request1 = new WeightedRequest("name", 2);
        WeightedRequest request2 = new WeightedRequest("name", 2);
        WeightedRequest request3 = new WeightedRequest("name", 3);

        SubsetSum.Builder builder = new SubsetSum.Builder(weightLimit);
        builder.addRequest(request1);
        builder.addRequest(request2);
        builder.addRequest(request3);

        SubsetSum sum = builder.build();

        assertEquals(5, sum.getTotalWeight());
    }
}
