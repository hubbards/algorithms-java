package com.github.hubbards.algorithms;

/**
 * This class contains implementations of the binary search algorithm. The
 * worst-case running time of binary search algorithm is <em>O(log n)</em>.
 *
 * @author Spencer Hubbard
 */
public final class BinarySearch {
    private BinarySearch() {
        throw new AssertionError("static class");
    }

    /**
     * Iterative binary search algorithm. Searches for a given value in a given
     * array.
     * <p>
     * The given array is assumed to be sorted.
     *
     * @param a array to search
     * @param x value to search for
     *
     * @return the index of <code>x</code> in <code>a</code> or <code>-1</code>
     * if not found.
     */
    public static int binarySearchI(int[] a, int x) {
        int min = 0;
        int max = a.length - 1;
        while (min <= max) {
            int mid = (max - min - 1) / 2 + min; // FIXME
            if (a[mid] == x) {
                // target found
                return mid;
            } else if (a[mid] < x) {
                // search second half
                min = mid + 1;
            } else {
                // search first half
                max = mid - 1;
            }
        }
        // target not found
        return -1;
    }

    /**
     * Recursive binary search algorithm. Searches for a given value in a given
     * array.
     * <p>
     * The given array is assumed to be sorted.
     *
     * @param a array to search
     * @param x value to search for
     *
     * @return the index of <code>x</code> in <code>a</code> or <code>-1</code>
     * if not found.
     */
    public static int binarySearchR(int[] a, int x) {
        int min = 0;
        int max = a.length - 1;
        return binarySearchR(a, x, min, max);
    }

    // helper method
    private static int binarySearchR(int[] a, int x, int min, int max) {
        if (min > max) {
            // base case
            // target not found
            return -1;
        } else {
            // recursive case
            int mid = (max - min - 1) / 2 + min;
            if (a[mid] == x) {
                // target found
                return mid;
            } else if (a[mid] < x) {
                // search second half
                min = mid + 1;
                return binarySearchR(a, x, min, max);
            } else {
                // search first half
                max = mid - 1;
                return binarySearchR(a, x, min, max);
            }
        }
    }
}
