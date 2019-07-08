package com.github.hubbards.algorithms.sort;

import java.util.Arrays;

/**
 * This class contains an implementation of the merge sort algorithm. The
 * worst-case running time of the merge sort algorithm is
 * <em>O(n * log(n))</em>.
 *
 * @author Spencer Hubbard
 */
public final class MergeSort {
    private MergeSort() {
        throw new AssertionError("static class");
    }

    /**
     * Recursive merge sort algorithm.
     *
     * @param a array to sort
     */
    public static void mergeSort(int[] a) {
        if (a.length > 1) {
            // split array into two halves
            int[] l = Arrays.copyOfRange(a, 0, a.length / 2);
            int[] r = Arrays.copyOfRange(a, a.length / 2, a.length);
            // recursively sort two halves
            mergeSort(l);
            mergeSort(r);
            // merge sorted halves into sorted whole
            merge(a, l, r);
        }
    }

    // pre : a is empty; l and r are sorted
    // post: a contains l and r and is sorted
    private static void merge(int[] a, int[] l, int[] r) {
        int i = 0; // index into left array
        int j = 0; // index into right array
        for (int k = 0; k < a.length; k++) {
            if (j >= r.length || i < l.length && l[i] < r[j]) {
                // take from left
                a[k] = l[i];
                i++;
            } else {
                // take from right
                a[k] = r[j];
                j++;
            }
        }
    }
}
