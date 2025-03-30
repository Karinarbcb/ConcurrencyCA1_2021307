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

public class MatrixMultiplier {

    public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) { // It takes two matrices 10x10 to returns their products                
        int[][] result = new int[10][10];  // Creates matrix to store multiplication result        
        ForkJoinPool pool = new ForkJoinPool(); // Create a pool of worker threads for parallel computation
        pool.invoke(new MultiplicationTask(matrixA, matrixB, result, 0, 10)); // Create and invoke a task to process all 10 rows
        return result;
    }
    
    private static class MultiplicationTask extends RecursiveAction {  // Recursive task for parallel matrix multiplication
        private static final int THRESHOLD = 10; // Process 10 rows at a time
        private final int[][] matrixA; // Input matrix A
        private final int[][] matrixB; // Input matrix B
        private final int[][] result;
        private final int startRow; // Range of rows to process
        private final int endRow;
        
        MultiplicationTask(int[][] matrixA, int[][] matrixB, int[][] result, int startRow, int endRow) { // Contructor that initializes matrices and row range
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.result = result;
            this.startRow = startRow;
            this.endRow = endRow;
        }
        
        @Override
        protected void compute() {  // Defines the parallel computation logic
            if (endRow - startRow <= THRESHOLD) {  
                for (int i = startRow; i < endRow; i++) {
                     for (int j = 0; j < 10; j++) {  // Processes rows in current range
                    int sum = 0; // Local variable for accumulation
                    for (int k = 0; k < 10; k++) { // Processes all columns
                        sum += matrixA[i][k] * matrixB[k][j];
                    }
                        result[i][j] = sum;  // Compute dot product of row i of A and column j of B
                    }
                }
            } else {
                int mid = (startRow + endRow) >>> 1; // Splitting rows
                invokeAll( // Run tasks in parallel
                    new MultiplicationTask(matrixA, matrixB, result, startRow, mid), // Creating tasks for first and second half of rows
                    new MultiplicationTask(matrixA, matrixB, result, mid, endRow)
                );
            }
        }
    }
}