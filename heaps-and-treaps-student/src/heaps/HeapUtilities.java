/*
 * Copyright 2023 Marc Liberatore.
 */

package heaps;

import java.util.Arrays;
import java.util.Random;

public class HeapUtilities {
    /**
     * Returns true iff the subtree of a starting at index i is a max-heap.
     * 
     * @param a an array representing a mostly-complete tree, possibly a heap
     * @param i an index into that array representing a subtree rooted at i
     * @return true iff the subtree of a starting at index i is a max-heap
     */
    static boolean isHeap(double[] a, int i) {
        // Base case
        if (i >= a.length)
            return true;

        // Find children and compare
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < a.length && a[left] > a[i])
            return false;

        if (right < a.length && a[right] > a[i])
            return false;

        // Recurse
        return isHeap(a, left) && isHeap(a, right);
    }

    /**
     * Perform the heap siftdown operation on index i of the array a.
     * 
     * This method assumes the subtrees of i are already valid max-heaps.
     * 
     * This operation is bounded by n (exclusive)! In a regular heap,
     * n = a.length, but in some cases (for example, heapsort), you will
     * want to stop the sifting at a particular position in the array.
     * siftDown should stop before n, in other words, it should not
     * sift down into any index great than (n-1).
     * 
     * @param a the array being sifted
     * @param i the index of the element to sift down
     * @param n the bound on the array (that is, where to stop sifting)
     */
    static void siftDown(double[] a, int i, int n) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root and exists
        if (left < n && a[left] > a[largest])
            largest = left;

        // If right child is larger than largest so far and exists
        if (right < n && a[right] > a[largest])
            largest = right;

        // If largest is not root, swap the two and recurse
        if (largest != i) {
            double temp = a[i];
            a[i] = a[largest];
            a[largest] = temp;

            siftDown(a, largest, n);
        }
    }

    /**
     * Heapify the array a in-place in linear time as a max-heap.
     * 
     * @param a an array of values
     */
    static void heapify(double[] a) {
        int n = a.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(a, i, n);
        }
    }

    /**
     * Heapsort the array a in-place, resulting in the elements of
     * a being in ascending order.
     * 
     * @param a
     */
    static void heapSort(double[] a) {
        // Build heap
        heapify(a);

        // One by one extract an element from heap
        for (int i = a.length - 1; i > 0; i--) {
            // Move current root to end
            double temp = a[0];
            a[0] = a[i];
            a[i] = temp;

            // Call max heapify on the reduced heap
            siftDown(a, 0, i);
        }
    }

    public static void main(String[] args) {
        Random r = new Random(0);
        int length = 15;
        double[] l = new double[length];
        for (int i = 0; i < length; i++) {
            l[i] = r.nextInt(20);
        }
        System.out.println(Arrays.toString(l));

        heapify(l);

        System.out.println(Arrays.toString(l));

        heapSort(l);

        System.out.println(Arrays.toString(l));
    }
}
