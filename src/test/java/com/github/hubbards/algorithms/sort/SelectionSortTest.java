package com.github.hubbards.algorithms.sort;

import org.junit.Test;

import static com.github.hubbards.algorithms.sort.SelectionSort.selectionSort;
import static org.junit.Assert.assertEquals;

public class SelectionSortTest {
    @Test
    public void testSelectionSort() {
        int[] a = {12, 123, 1, 28, 183, 16};

        selectionSort(a);

        assertEquals(6, a.length);
        assertEquals(1, a[0]);
        assertEquals(12, a[1]);
        assertEquals(16, a[2]);
        assertEquals(28, a[3]);
        assertEquals(123, a[4]);
        assertEquals(183, a[5]);
    }

    // TODO: add more tests
}
