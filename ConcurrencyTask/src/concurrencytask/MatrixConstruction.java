/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrencytask;

/**
 *
 * @author karina
 */

public class MatrixConstruction {
    
    /**
     * Extracts a sub-matrix from the source matrix
     * @param data
     * @return The extracted sub-matrix
     * @throws IllegalArgumentException If invalid row range
     */
    
    public static int[][] extractMatrixA(int[][] data) {
        int[][] matrixA = new int[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(data[i], 0, matrixA[i], 0, 10);
        }
        return matrixA;
    }

    public static int[][] extractMatrixB(int[][] data) {
        int[][] matrixB = new int[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(data[i + 10], 0, matrixB[i], 0, 10);
        }
        return matrixB;
    }
    
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.printf("%d ", num);
            }
            System.out.println();
        }
    }
}