/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrencytask;

/**
 *
 * @author karina
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class StandardDeviationCalculator  {

    private static class PartialResult {
        double sum; // Store the sum of elements
        int count; // Store the number of elements
        double sumSquares;
        PartialResult(double sum, int count, double sumSquares) { //Help to store sums and elemens counts while parallel computation
            this.sum = sum;
            this.count = count;
            this.sumSquares = sumSquares;
        }
        void add(PartialResult other) { // Combine results from subtasks
            this.sum += other.sum;
            this.count += other.count;
            this.sumSquares += other.sumSquares;
        }
    }
    private static class SumTask extends RecursiveTask<PartialResult> { // Compute the sum and count of segment of the data concurrency
        private static final int THRESHOLD = 200; //Elements for sequential process
        private final int[][] data; // Input matrix
        private final int start;  // Start row index
        private final int end;  // End row index
        SumTask(int[][] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected PartialResult compute() {
            if (end - start <= THRESHOLD) { 
                double sum = 0;
                int count = 0;
                double sumSquares = 0;
               for (int i = start; i < end; i++) {
                    for (int num : data[i]) {
                        sum += num;
                        sumSquares += num * num;
                        count++;
                    }
                }
                return new PartialResult(sum, count, sumSquares);
            } else {
            int mid = (start + end) >>> 1;
            SumTask left = new SumTask(data, start, mid);
            SumTask right = new SumTask(data, mid, end);
            left.fork();
            PartialResult rightResult = right.compute();
            PartialResult leftResult = left.join();
            leftResult.add(rightResult);
            return leftResult; // Return the combined result
        }
    }
    }
    public static double computeStandardDeviation(int[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            return 0.0;
        }
        ForkJoinPool pool = new ForkJoinPool();
        try {
            PartialResult result = pool.invoke(new SumTask(data, 0, data.length)); // Compu
            double variance = (result.sumSquares - (result.sum * result.sum) / result.count) / result.count;
            return Math.sqrt(variance);
        } finally {
            pool.shutdown();
        }
    }
}
