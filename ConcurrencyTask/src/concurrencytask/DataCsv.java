/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concurrencytask;

/**
 *
 * @author karina
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataCsv {    // 
    private int[][] data;

    public DataCsv(String filename) throws FileNotFoundException {
        this.data = readDataCsv(filename);
    }
    public static int[][] readDataCsv(String filename) throws FileNotFoundException {
        List<int[]> rows = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) { 
                String[] values = scanner.nextLine().split(",");
                int[] row = new int[values.length];
                for (int j = 0; j < values.length; j++) {
                    row[j] = Integer.parseInt(values[j].trim());
                }
                rows.add(row);
            }
            int[][] matrix = new int[rows.size()][];
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
        return data;
    }
}


/*Reads a CSV file and returns its contents as a 2D double array
     * @param filename Path to the CSV file
     * @return 2D array of doubles from the CSV
     * @throws FileNotFoundException If file doesn't exist
     * @throws NumberFormatException If any value isn't a valid number*/