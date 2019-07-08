package com.github.hubbards.algorithms;

import org.junit.Test;


import static com.github.hubbards.algorithms.BinarySearch.binarySearchI;
import static com.github.hubbards.algorithms.BinarySearch.binarySearchR;
import static org.junit.Assert.assertEquals;

public class BinarySearchTest {
    @Test
    public void testBinarySearchINotFound() {
        int[] a = { 1, 2, 4, 5, 6, 7, 8, 9 };

        int i = binarySearchI(a, 3);

        assertEquals(-1, i);
    }

    @Test
    public void testBinarySearchIFound() {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        int i = binarySearchI(a, 3);

        assertEquals(2, i);
        assertEquals(3, a[i]);
    }

    @Test
    public void testBinarySearchIMin() {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        int i = binarySearchI(a, 1);

        assertEquals(0, i);
        assertEquals(1, a[i]);
    }

    @Test
    public void testBinarySearchIMax() {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        int i = binarySearchI(a, 9);

        assertEquals(8, i);
        assertEquals(9, a[i]);
    }

    @Test
    public void testBinarySearchRNotFound() {
        int[] a = { 1, 2, 4, 5, 6, 7, 8, 9 };

        int i = binarySearchR(a, 3);

        assertEquals(-1, i);
    }

    @Test
    public void testBinarySearchRFound() {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        int i = binarySearchR(a, 3);

        assertEquals(2, i);
        assertEquals(3, a[i]);
    }

    @Test
    public void testBinarySearchRMin() {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        int i = binarySearchR(a, 1);

        assertEquals(0, i);
        assertEquals(1, a[i]);
    }

    @Test
    public void testBinarySearchRMax() {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

        int i = binarySearchR(a, 9);

        assertEquals(8, i);
        assertEquals(9, a[i]);
    }
}
