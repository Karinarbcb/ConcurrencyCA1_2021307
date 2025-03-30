/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package concurrencytask;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 *
 * @author karina
 */

public class ConcurrencyTask {
   public static void main(String[] args) {
        try { //to handle erros
            System.out.println("Starting program !");
            System.out.println("Reading file...");
            DataCsv dataCsv = new DataCsv("data.csv"); //To read "data.csv", creates DataCsv object
            int[][] data = dataCsv.getData(); // used get method to load Csv into an int array data
            System.out.println("Values ready to use");
            
            // Task 1: Standard Deviation Calculation
            System.out.println("Calculating standard deviation");
            double stdDev = StandardDeviationCalculator.computeStandardDeviation(data); //calculate standard deviation of all values in data
            System.out.printf("Standard Deviation: %.2f%n", stdDev); // 2 decimal places
            
            // Task 2: Matrix Multiplication
            int[][] matrixA = MatrixConstruction.extractMatrixA(data); //get first 10 rows
            int[][] matrixB = MatrixConstruction.extractMatrixB(data); // gets next 10 rows
            int[][] resultMatrix = MatrixMultiplier.multiplyMatrices(matrixA, matrixB);
            System.out.println("Matrix Multiplication Completed");
            MatrixConstruction.printMatrix(resultMatrix); //print resulting matrix
            
            // Task 3: Merge Sort
            System.out.println("Sorting data using concurrent merge sort..."); // Print message
            int[] flattenedData = Arrays.stream(data).flatMapToInt(Arrays::stream).toArray(); // Converts 2D data into 1D array
            ConcurrentMergeSort.mergeSort(flattenedData); // Sorts the flattened array using concurrent merge sort
            System.out.println("Sort data in descending order:");
            for (int i = 0; i < Math.min(200, flattenedData.length); i++) { //Prints all 200 sorted numbers
                System.out.printf("%d ", flattenedData[i]); // Print with space separating
            }
            System.out.println(); // Next line
            System.out.println("Program finished");
            
        } catch (FileNotFoundException e) {  // Catch file if not found
            System.err.println("Error: data.csv not found");
        } catch (Exception e) {  // Catch another exception
            System.err.println("Execution error: " + e.getMessage());
            e.printStackTrace(); //Provides detailed error info for debugging
        }
    }
}