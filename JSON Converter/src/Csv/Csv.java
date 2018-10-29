package Csv;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Csv.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class Csv {

    ArrayList<CsvRow> data;
    /**
     * Constructs an objecct of type Csv.
     */
    public Csv() {
       data  = new ArrayList<CsvRow>();
       

    }
    /**
     * Returns the {bare_field_name} for this Csv.
     * @return the data
     */
    public ArrayList<CsvRow> getData() {
        return data;
    }
    /**
     * Sets the data for this Csv
     * @param data the data to set
     */
    public void setData(ArrayList<CsvRow> data) {
        this.data = data;
    }
    
    public String getDataAt(int x, int y) {
        return data.get(y).getValue(x);
    }
    
    public void AddRow(CsvRow a) {
        data.add(a);
    }
    
    public void print() {
        for(CsvRow r : data) {
            System.out.println(Arrays.asList(r.getData()));
        }
    }
    
    public String[][] getArray(){
        int rows = data.size();
        System.out.println("Rows= " + rows);
        int columns = data.get(0).getData().size();
        System.out.println("Columns= " + columns);

        String temp[][] = new String[columns][rows];
        for(int i = 0; i < columns; i++) {
            for(int j = 0; j < rows; j++) {
                temp[i][j] = getDataAt(i,j);
            }
        }
        return temp;
    }

}
