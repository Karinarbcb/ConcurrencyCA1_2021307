/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrencytask;

/**
 *
 * @author karina
 */
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentMergeSort {
    private static final ForkJoinPool pool = new ForkJoinPool();  // Thread pool for parallel tasks
    public static void mergeSort(int[] array) { // Takes an array and range to sort
        pool.invoke(new MergeSortTask(array, 0, array.length - 1)); // Invokes parallel sorting task on the pool
    }
    private static class MergeSortTask extends RecursiveAction { // Parallels tasks without return values
        private static final int THRESHOLD = 1000; // Define to stop parallel process after reading all 200 elements
        private final int[] array;
        private final int low; // Chuncks to avoid overhead
        private final int high;
        
        MergeSortTask(int[] array, int low, int high) { // Working rang withing the array
            this.array = array;
            this.low = low;
            this.high = high;
        }
        
        @Override
        protected void compute() {
            if (high - low <= THRESHOLD) { // if is small enough, sort sequentially
                sequentialMergeSort(array, low, high); // Avoids overhead of parallelization for small chuncks
            } else {
                int mid = (low + high) >>> 1; // Finds middle index using unsigned right shift for safe average
                invokeAll(
                    new MergeSortTask(array, low, mid),
                    new MergeSortTask(array, mid + 1, high)
                );
                merge(array, low, mid, high);
            }
        }  
        private void sequentialMergeSort(int[] array, int low, int high) {
            if (low < high) {
                int mid = (low + high) >>> 1;
                sequentialMergeSort(array, low, mid);
                sequentialMergeSort(array, mid + 1, high);
                merge(array, low, mid, high);
            }
        }
        private void merge(int[] array, int low, int mid, int high) {
            int[] temp = new int[high - low + 1];
            int i = low, j = mid + 1, k = 0;
            
            while (i <= mid && j <= high) {
                if (array[i] >= array[j]) {
                    temp[k++] = array[i++];
                } else {
                    temp[k++] = array[j++];
                }
            }
            while (i <= mid) {
                temp[k++] = array[i++];
            }
            while (j <= high) {
                temp[k++] = array[j++];
            }
            System.arraycopy(temp, 0, array, low, temp.length);
        }
    }
}