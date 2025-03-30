/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrencytask;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentMergeSort {
    private static final ForkJoinPool pool = new ForkJoinPool();  // Thread pool in parallel tasks
    public static void mergeSort(int[] array) { // Takes an array and range to sort
        pool.invoke(new MergeSortTask(array, 0, array.length - 1)); // Invokes parallel sorting task on the pool
    }
    private static class MergeSortTask extends RecursiveAction { // Parallels tasks without return values
        private static final int THRESHOLD = 1000; // Define to stop parallel process after reading all 1000 elements
        private final int[] array;
        private final int low; // Chuncks to avoid overhead
        private final int high;
        
        MergeSortTask(int[] array, int low, int high) { // Working rang withing the array
            this.array = array;
            this.low = low;
            this.high = high;
        }
        
        @Override
        protected void compute() { // Core logic for the merge algorithm
            if (high - low <= THRESHOLD) { // if is small enough, sort sequentially
                sequentialMergeSort(array, low, high); // Avoids overhead of parallelization for small chuncks
            } else {
                int mid = (low + high) >>> 1; // Finds middle index using unsigned right shift for safe average
                invokeAll(
                    new MergeSortTask(array, low, mid),
                    new MergeSortTask(array, mid + 1, high)
                );
                merge(array, low, mid, high); // Combines two sorted halves into one sorted segment
            }
        }  
        private void sequentialMergeSort(int[] array, int low, int high) {
            if (low < high) { 
                int mid = (low + high) >>> 1;  // Check if has more than one element
                sequentialMergeSort(array, low, mid);
                sequentialMergeSort(array, mid + 1, high);
                merge(array, low, mid, high);
            }
        }
        private void merge(int[] array, int low, int mid, int high) { // Create merge to combine two subarrays into a singles subarray
            int[] temparray  = new int[high - low + 1]; // Create temparray to hold merge result
            int i = low, j = mid + 1, k = 0;
            
            while (i <= mid && j <= high) { // Start loop
                if (array[i] >= array[j]) { // Comapre elements
                    temparray[k++] = array[i++]; // If array i is greater than or equal to array j, it copies array i into temparray 
                } else {// And increments both i and k
                    temparray[k++] = array[j++]; // Else, copies array j into tempo and increments both j and k
                }
            }
            while (i <= mid) {
                temparray[k++] = array[i++];
            }
            while (j <= high) {
                temparray[k++] = array[j++];
            }
            System.arraycopy(temparray, 0, array, low, temparray.length); // Arraycopy to copy the entire merged back into array
        }
    }
}