// This program contains an implementation of the selection sort algorithm.

// TODO: document running time

import java.util.*; // for Arrays

public class SelectionSort {

    public static void main(String[] args) {
        // simple example
      int a[] = {12, 123, 1, 28, 183, 16};
      System.out.println("before : " + Arrays.toString(a));
      selectionSort(a);
      System.out.println("after  : " + Arrays.toString(a));

      // TODO: test selection sort algorithm
    }

    // selection sort algorithm
    public static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            // find index of next smallest element
            int smallest = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[smallest] > a[j]) {
                    smallest = j;
                }
            }
            // swap element at index i with next smallest element
            swap(a, i, smallest);
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
