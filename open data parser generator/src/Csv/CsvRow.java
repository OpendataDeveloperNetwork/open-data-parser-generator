package Csv;

import java.util.ArrayList;

/**
 * CsvRow.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class CsvRow {
    ArrayList<String> data;

    /**
     * Constructs an objecct of type CsvRow.
     */
    public CsvRow() {
        data = new ArrayList<String>();  
    }

    /**
     * Returns the {bare_field_name} for this CsvRow.
     * @return the data
     */
    public ArrayList<String> getData() {
        return data;
    }

    /**
     * Sets the data for this CsvRow
     * @param data the data to set
     */
    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    /**
     * @param x
     * @return
     */
    public String getValue(int x) {
        return data.get(x);
    }
    
    public void addData(String a) {
        data.add(a);
    }

}
