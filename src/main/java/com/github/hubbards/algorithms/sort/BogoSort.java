package com.github.hubbards.algorithms.sort;

import java.util.Random;

/**
 * This class contains an implementation of the bogo sort algorithm. Don't use
 * it for sorting...
 *
 * @author Spencer Hubbard
 */
public final class BogoSort {
    public static final int MAX_SHUFFLES = 100;

    public BogoSort() {
        throw new AssertionError("static class");
    }

    /**
     * Bogo sort algorithm; either sorts the given array or shuffles it many
     * times.
     *
     * @param a array to sort
     */
    public static void bogoSort(int[] a) {
        int count = 0;
        while (!isSorted(a) && count < MAX_SHUFFLES) {
            shuffle(a);
            count++;
        }
    }

    /**
     * Check if a given array is sorted.
     *
     * @param a array to check
     *
     * @return <code>true</code> if <code>a</code> is sorted, otherwise
     * <code>false</code>.
     */
    public static boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // pre : a != null
    // post: shuffle a
    private static void shuffle(int[] a) {
        Random r = new Random();
        for (int i = 0; i < a.length - 1; i++) {
            // pick a random integer between i + 1 (inclusive) and
            // a.length - 1 (inclusive)
            int min = i + 1;
            int max = a.length - 1;
            int j = r.nextInt(max - min + 1) + min;
            // swap element at index i with element at index j
            swap(a, i, j);
        }
    }

    // pre : 0 <= i < a.length && 0 <= j < a.length
    // post: swap elements at indices i and j of a
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
