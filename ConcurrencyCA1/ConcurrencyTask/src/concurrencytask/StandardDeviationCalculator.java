/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrencytask;

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
        void add(PartialResult other) { // Combine add with PartialResults
            this.sum += other.sum;
            this.count += other.count;
            this.sumSquares += other.sumSquares;
        }
    }
    private static class SumTask extends RecursiveTask<PartialResult> { // Compute the sum, count and sumSquares of segment of the data concurrency
        private static final int THRESHOLD = 200; //Elements for sequential process set to 200
        private final int[][] data; // Input matrix
        private final int start;  // Start row index
        private final int end;  // End row index
        SumTask(int[][] data, int start, int end) { // Constructor for SumTask
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected PartialResult compute() { // Logic core to process the rows sequentially or split the work into subtasks
            if (end - start <= THRESHOLD) { // Check if the rows is less than or equal to the threshold
                double sum = 0; // 
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
            SumTask left = new SumTask(data, start, mid); // Process the first half of the range
            SumTask right = new SumTask(data, mid, end);  // Process second half of the range
            left.fork();
            PartialResult rightResult = right.compute(); // Allow work stealing and reduce overhead by balancing the workload
            PartialResult leftResult = left.join(); // Wait to the left task complete and retrieves result
            leftResult.add(rightResult); // Combine the results
            return leftResult; // Return the merge combined result
        }
    }
    }
    public static double computeStandardDeviation(int[][] data) { // Takes a 2D integer array as input and returns a double
        if (data == null || data.length == 0 || data[0].length == 0) { // Check if the data is null, has no rows , or has empty rows
            return 0.0;
        }
        ForkJoinPool pool = new ForkJoinPool(); // To execute the parallel sumtask
        try {
            PartialResult result = pool.invoke(new SumTask(data, 0, data.length)); // Compu
            double variance = (result.sumSquares - (result.sum * result.sum) / result.count) / result.count; // Calculate variance
            return Math.sqrt(variance); 
        } finally { // Ensure that the ForkJoinPool is shut down after the computation
            pool.shutdown();
        }
    }
}
