// This program contains an implementation of the bogo sort algorithm. Don't
// run this program...

import java.util.*; // for Random

public class BogoSort {

    public static void main(String[] args) {
        // TODO: test bogo sort algorithm
    }

    // bogo sort algorithm
    public static void bogoSort(int[] a) {
        while (!isSorted(a)) {
            shuffle(a);
        }
    }

    // post: return true if given array is sorted, false otherwise
    public static boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // pre : a != null
    // post: shuffle given array
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
    // post: swap elements of given array at given indices
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}