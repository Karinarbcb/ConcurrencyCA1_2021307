/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrencytask;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataCsv {    // Class to read file data and convert it into a 2D integer array
    private int[][] data; // It will hold the 2D integer array

    public DataCsv(String filename) throws FileNotFoundException { // If the file isn't found, this exception will be show
        this.data = readDataCsv(filename); // Read the file and assign the returned 2D array to data
    }
    public static int[][] readDataCsv(String filename) throws FileNotFoundException {
        List<int[]> rows = new ArrayList<>(); // Creating a list to store each row as an int [] array
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) { // Start while loop
                String[] values = scanner.nextLine().split(","); // Read next line from the file and splits the line into an array of String
                int[] row = new int[values.length]; // New int row with the same size to the number of values from splitting the lines
                for (int j = 0; j < values.length; j++) {
                    row[j] = Integer.parseInt(values[j].trim()); // Convert string into an integer
                }
                rows.add(row); // Add row array to the rows list
            }
            int[][] matrix = new int[rows.size()][]; // Create a new 2D integer w/ the number of rows equal to the rows list
            for (int i = 0; i < rows.size(); i++){ 
                matrix[i] = rows.get(i); // Get entire row
            }
            return matrix;
            
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found: " + filename);
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid format in file");
            throw e;
        }
    }
    public int[][] getData(){
        return data; // return data that was read and stored by the constructor
    }
}