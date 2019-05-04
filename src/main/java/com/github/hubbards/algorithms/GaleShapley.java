package com.github.hubbards.algorithms;

import java.util.Queue;
import java.util.LinkedList;

/**
 * This program implements the Gale-Shapley algorithm for the stable matching
 * problem.
 *
 * @author Spencer Hubbard
 */
public final class GaleShapley {
    private GaleShapley() {
        throw new AssertionError("static class");
    }

    /*
     * Pseudocode:
     *
     * Initially all men and women are free
     * While there are free men who haven't proposed to every woman yet
     *   Let m be such a man
     *   Let w be the next highest-ranked woman in m's preference list
     *   If w is free
     *     m and w become engaged
     *   Else w is engaged with some man m'
     *     If w prefers m to m'
     *       m' becomes free
     *       m and w become engaged
     *     Else w prefers m' to m
     *       m remains free
     *     Endif
     *   Endif
     * Endwhile
     * Return the engaged pairs
     */

    /**
     * Gale-Shapley algorithm for the stable matching problem. The time
     * complexity of this algorithm is linear.
     *
     * @param left  preference lists for left, i.e., <code>left[i][j]</code> is
     *              the rank of right <code>j</code> in left <code>i</code>'s
     *              preference list.
     * @param right preference lists for right, i.e., <code>right[i][j]</code>
     *              is the rank of left <code>j</code> in right <code>i</code>'s
     *              preference list.
     * @return stable matching for given instance of stable matching problem.
     * @throws IllegalArgumentException if <code>left</code> and
     *                                  <code>right</right> are not the
     *                                  preference lists for an instance of the
     *                                  stable matching problem.
     */
    public static int[] stableMatching(int[][] left, int[][] right) {
        if (!isInstance(left, right)) {
            throw new IllegalArgumentException();
        }

        int n = left.length;
        // match[i] is left index matched with right i
        int[] match = new int[n];
        // next[i] is next highest rank for right i to choose
        int[] next = new int[n];
        // queue for right indexes that aren't matched yet
        Queue<Integer> queue = new LinkedList<Integer>();

        // initialize
        for (int i = 0; i < n; i++) {
            match[i] = -1;
            // next[i] is auto-initialized to 0
            queue.add(i);
            // set left[i][j] to the right index with rank j in left i's
            // preference list
            left[i] = computeInverse(left[i]);
        }

        // find stable matching
        while (!queue.isEmpty()) {
            int i = queue.remove();
            if (next[i] < n) {
                int j = left[i][next[i]]; // right j is ranked next by left i
                next[i]++;
                if (match[j] == -1) {
                    // right j has not been matched
                    match[j] = i;
                } else if (right[j][i] < right[j][match[j]]) {
                    // replace current match for right j with left i
                    queue.add(match[j]);
                    match[j] = i;
                } else {
                    // left i not matched with right j
                    queue.add(i);
                }
            }
        }

        return match;
    }

    /*
     * Helper method. Checks if given arrays are preference lists for an
     * instance of the the stable matching problem.
     */
    private static boolean isInstance(int[][] left, int[][] right) {
        int n = left.length;
        if (right.length != n) {
            // left and right are not same length
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (!isPreferenceList(left[i], n) || !isPreferenceList(right[i], n)) {
                // left or right is not a preference list of n things
                return false;
            }
        }
        return true;
    }

    /*
     * Helper method. Checks if a given array is a preference list for a given
     * number of things.
     */
    private static boolean isPreferenceList(int[] preference, int n) {
        if (preference.length != n) {
            // doesn't rank n things
            return false;
        }
        boolean[] seen = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (preference[i] < 0 || n <= preference[i] || seen[preference[i]]) {
                // not a valid rank or rank is a tie
                return false;
            }
            seen[preference[i]] = true;
        }
        return true;
    }

    // Helper method. Computes the inverse of a given preference list.
    private static int[] computeInverse(int[] preference) {
        int[] inverse = new int[preference.length];
        for (int i = 0; i < preference.length; i++) {
            // permutation[i] == j iff inverse[j] == i
            inverse[preference[i]] = i;
        }
        return inverse;
    }
}
