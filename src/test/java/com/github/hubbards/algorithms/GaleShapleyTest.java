package com.github.hubbards.algorithms;

import org.junit.Test;

import static com.github.hubbards.algorithms.GaleShapley.stableMatching;
import static org.junit.Assert.assertEquals;

public class GaleShapleyTest {
    @Test
    public void testGaleShapley() {
        int[] p = { 0, 1, 2 };
        int[] q = { 1, 0, 2 };
        int[][] left = { p, q, p };
        int[][] right = { q, p, p };

        int[] match = stableMatching(left, right);

        assertEquals(3, match.length);
        assertEquals(0, match[0]);
        assertEquals(1, match[1]);
        assertEquals(2, match[2]);
    }

    // TODO: add more tests
}
