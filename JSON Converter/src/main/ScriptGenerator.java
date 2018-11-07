package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Json.JsonElement;

/**
 * ScriptGenerator.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class ScriptGenerator {

    private static final String FILENAME = "converter.js";
    /**
     * @param left
     * @param right
     * @param location 
     */
    public static void generateScript(ArrayList<String> left, ArrayList<String> right, String location, JsonElement element) {

        String script = writeScript(left, right, element);

        //Writes to script
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(location +"\\" +  FILENAME);
            bw = new BufferedWriter(fw);
            bw.write(script);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }


        }

    }
    /**
     * @param left
     * @param right
     * @return
     */
    private static String writeScript(ArrayList<String> left, ArrayList<String> right, JsonElement object) {
        for(int i = 0; i < left.size();i++) {
            System.out.println(left.get(i) + " : " + right.get(i));
        }
        
        JsonElement element = object;
        String script = "";
        
        //Logic for generating script
        return script;
    }




}

