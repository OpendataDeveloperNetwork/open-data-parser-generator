package Csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * CsvParser.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class CsvParser {
    /**
     * Returns the {bare_field_name} for this CsvParser.
     * @return the csv
     */
    public Csv getCsv() {
        return csv;
    }

    private Csv csv;

    /**
     * Constructs an objecct of type CsvParser.
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     */
    public CsvParser(String src) {
        csv = new Csv();
        String delimiter = ",";
        ArrayList<CsvRow> csvRows = new ArrayList<CsvRow>();
        File file = new File(src);
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(src),
                            "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e1) {
            e1.printStackTrace();
        }
        
        try {
            while ((line = br.readLine()) != null) {
                if (line.startsWith("\uFEFF")) { line = line.substring(1); }
                String[] row = line.split(delimiter);
                CsvRow data = new CsvRow();
                for(int i = 0; i < row.length; i++) {
                    data.addData(row[i]);
                }
                csv.AddRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        csv.print();

    }

}
