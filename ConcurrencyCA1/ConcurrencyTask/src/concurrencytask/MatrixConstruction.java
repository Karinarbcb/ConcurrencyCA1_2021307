/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrencytask;

public class MatrixConstruction {

    public static int[][] extractMatrixA(int[][] data) { // Extracts a submatrix from the initial matrix
        int[][] matrixA = new int[10][10];
        for (int i = 0; i < 10; i++) { // Loop go over 10 first rows
            System.arraycopy(data[i], 0, matrixA[i], 0, 10); // Copy elements from the i row of data into i row of matrixA
        }
        return matrixA; // returns a 2D integer array
    }

    public static int[][] extractMatrixB(int[][] data) {
        int[][] matrixB = new int[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(data[i + 10], 0, matrixB[i], 0, 10); // i+10 accesses the rows startinf from the 11th row in the matrix
        }
        return matrixB;
    }
    
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) { // Loop to start iterates in each row
            for (int num : row) { // Inside the row loop, starts another loop to iterates with each integer in the current row
                System.out.printf("%d ", num); // %d for an integer
            }
            System.out.println();
        }
    }
}