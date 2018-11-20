package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import Json.JSONElement;

/**
 * ScriptGenerator.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class ScriptGenerator {

    static ArrayList<String> completed = new ArrayList<String>();
    static ArrayList<String> finalOrder = new ArrayList<String>();
    static int index = 0;

    private static final String FILENAME = "converter.js";
    /**
     * Main method for generating the converter script.
     * @param left
     * @param right
     * @param location 
     */
    public static void generateScript(ArrayList<String> left, ArrayList<String> right, String location, JSONElement element) {
        System.out.println(Arrays.asList(right));
        System.out.println(Arrays.asList(left));

        JSONElement root = element;
        System.out.println(root.getKey());
        String script = "";
        script += writeHeader(); //Beginning of Script
        script += writeRootArray(); //Generates the root object and root array
        script += writeCSVArray(); //Generates array for csv and loops through each row.
        script += writeArrays(root); //Generate useful arrays and objects to store data.
        writeScript("", left, right, root, "root", "");
        finalOrder.remove(finalOrder.size() - 1);
        for(String s : finalOrder) {
            script += s;
        }
        script +=  "rootArray" + ".push(" + root.getCompletePath() + ");\n";
        script += "}\n";
        script += "merge(root, \"rootArray\", rootArray);\n";
        script += writeFooter(); //End of Script

        //Writes to file
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
     * @return
     */
    private static String writeFooter() {
        return "  return rootArray;\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "let CSVToArray = (strData, strDelimiter) => {\r\n" + 
                "  strDelimiter = (strDelimiter || \",\");\r\n" + 
                "\r\n" + 
                "  if (strData.charAt(0) === ',') {\r\n" + 
                "    strData = \" \" + strData;\r\n" + 
                "  }\r\n" + 
                "\r\n" + 
                "  let objPattern = new RegExp(\r\n" + 
                "    (\r\n" + 
                "      \"(\\\\\" + strDelimiter + \"|\\\\r?\\\\n|\\\\r|^)\" +\r\n" + 
                "      \"(?:\\\"([^\\\"]*(?:\\\"\\\"[^\\\"]*)*)\\\"|\" +\r\n" + 
                "      \"([^\\\"\\\\\" + strDelimiter + \"\\\\r\\\\n]*))\"\r\n" + 
                "    ),\r\n" + 
                "    \"gi\"\r\n" + 
                "  );\r\n" + 
                "\r\n" + 
                "  let arrData = [];\r\n" + 
                "  let arrMatches;\r\n" + 
                "  while (arrMatches = objPattern.exec(strData)) {\r\n" + 
                "    let strMatchedValue = \"\";\r\n" + 
                "    let strMatchedDelimiter = arrMatches[1];\r\n" + 
                "    if (\r\n" + 
                "      strMatchedDelimiter.length &&\r\n" + 
                "      (strMatchedDelimiter !== strDelimiter)\r\n" + 
                "    ) {\r\n" + 
                "      arrData.push([]);\r\n" + 
                "    }\r\n" + 
                "    if (arrMatches[2]) {\r\n" + 
                "      strMatchedValue = arrMatches[2].replace(new RegExp(\"\\\"\\\"\", \"g\"), \"\\\"\");\r\n" + 
                "    } else {\r\n" + 
                "      strMatchedValue = arrMatches[3];\r\n" + 
                "    }\r\n" + 
                "    arrData.push(strMatchedValue);\r\n" + 
                "  }\r\n" + 
                "\r\n" + 
                "  return (arrData);\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "let merge = (json, key, value) => { \r\n" + 
                "  if ((value !== null && value !== undefined) && (Object.getOwnPropertyNames(value).length != 0)\r\n" + 
                "    && (typeof value !== \"string\" || (typeof value === \"string\" && value.trim() !== \"\"))) {\r\n" + 
                "    let temp = {};\r\n" + 
                "    temp[key] = value;\r\n" + 
                "    Object.assign(json, temp);\r\n" + 
                "  }\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "let parseBoolean = (value) => {\r\n" + 
                "  return \"true\" === value.toLowerCase();\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "let parseNumber = (value) => {\r\n" + 
                "  return Number(value);\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "let push = (arr, value) => {\r\n" + 
                "  if ((value !== null && value !== undefined)\r\n" + 
                "    && (typeof value !== \"string\" || (typeof value === \"string\" && value.trim() !== \"\"))) {\r\n" + 
                "    arr.push(value);\r\n" + 
                "  }\r\n" + 
                "};\n";
    }
    /**
     * @return
     */
    private static String writeHeader() {
        return "module.exports = {\r\n" + 
                "  doConvert: function (csv) { // input: CSV file\r\n" + 
                "    return convert(csv);      // output: JSON Object\r\n" + 
                "  }\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "let convert = (csvFile) => {\r\n" + 
                "  let validFormat = validateCSVFile(csvFile); // 1. validate CSV\r\n" + 
                "  if (!validFormat) {\r\n" + 
                "    return null; // TODO Through an exception?\r\n" + 
                "  }\r\n" + 
                "\r\n" + 
                "  let data, jsonObject;\r\n" + 
                "  data = parseCSVFile(csvFile); // 2. parse CSV file into a string & parse CSV string into an array\r\n" + 
                "  jsonObject = generateJSON(data);  // 3. Store the data into a JSON object\r\n" + 
                "\r\n" + 
                "  return jsonObject;\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "// Returns true if the CSV file is in a valid format, otherwise returns false.\r\n" + 
                "// Validates only format, not contents.\r\n" + 
                "let validateCSVFile = (csvFile) => {\r\n" + 
                "  return true;\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "let parseCSVFile = (csvText) => {\r\n" + 
                "  let allTextLines = csvText.split(/\\r\\n|\\n/);\r\n" + 
                "  let data = [];\r\n" + 
                "\r\n" + 
                "  // TODO: SANITIZE DATA - IF THE DATA HAS AN EMPTY CELL, THIS FOR LOOP CRASHES THIS PROGRAM.\r\n" + 
                "  for (let i = 0; i < allTextLines.length; i++) {\r\n" + 
                "    if (allTextLines[i].length !== 0) {\r\n" + 
                "      data[i] = CSVToArray(allTextLines[i]);\r\n" + 
                "    }\r\n" + 
                "  }\r\n" + 
                "\r\n" + 
                "  return data;\r\n" + 
                "};\r\n" + 
                "\r\n" + 
                "// Note: This function should be different for each schema.\r\n" + 
                "let generateJSON = (data) => {\n";
    }
    /**
     * @return
     */
    private static String writeCSVArray() {
        return "  for (let i = 1; i < data.length; i++) {\r\n" + 
                "    let row = data[i]; // TODO: CONDITIONAL STATEMENT for header rows in CSV data in JAVA\r\n" + 
                "                       // i should start from 1 if the CSV has a header row.\n";
    }
    private static String writeRootArray() {
        return "var root = {};\n" 
                + "var rootArray = [];\n";
    }
    /**
     * @param left
     * @return
     */
    private static String writeArrays(JSONElement e) {
        String text = "";

        if(e.isLeaf()) {
            text += "let " + e.getCompletePath() + " = {};\n";
        } else {
            text += "let " + e.getCompletePath() + " = {};\n";
            for(JSONElement element : e.getChildren()) {
                text += writeArrays(element);
            }

        }
        return text;
    }
    /**
     * @param left
     * @param right
     * @return
     */
    private static void writeScript(String str, ArrayList<String> left, ArrayList<String> right, JSONElement object, String parent, String parent2) {
        String text = str;
        str += ""; 
        ArrayList<JSONElement> list = object.getChildren();
        for(JSONElement element : list) {
            if(!completed.contains(element.getKey())) {
                //completed.add(object.getKey());
                if(element.isLeaf()) {
                    completed.add(element.getKey());
                    if(Integer.parseInt(right.get(index)) > 0) {
                        if(object.getKey().equals("properties")) {
                            finalOrder.add("merge(" + parent + ", \"" + element.getKey() + "\", row[" + (Integer.parseInt(right.get(index)) - 1) + "]);\n");

                        } else {
                            finalOrder.add("merge(" + object.getCompletePath() + ", \"" + element.getKey() + "\", row[" + (Integer.parseInt(right.get(index)) - 1) + "]);\n");
                            //str += "merge(" + left.get(index) + ", \"" + left.get(index) + "\", " + "CSVrows["+ right.get(index) + "])\n";
                        }
                    }
                    index++;

                } else {
                    writeScript(str, left, right, element, object.getCompletePath(), parent);
                    //str += "MID" + object.getKey() + " <- " + element.getKey() + "\n";

                }
            }
            //str += "merge(" + object.getKey() + ", \"" + left.get(index) + "\", " + left.get(index) + ")\n\n";
        }
        if(object.getKey().equals("properties")) {
        } else {
            finalOrder.add("merge(" + parent2 + ", \"" + object.getKey() + "\"," + object.getCompletePath() + ");\n");

        }
        //finalOrder.add("merge(" + parent + ", \"\"," + object.getCompletePath() + ");\n");
    }

    /**
     * @param jsonHead2
     * @return
     */
    private static String deleteSubstring(String s) {
        String stringToSplit = s;
        String delimiter = "/";
        String[] tempArray = stringToSplit.split(delimiter);
        for (int i = 0; i < tempArray.length; i++) {
            System.out.println(tempArray[i]);
        }
        return tempArray[tempArray.length - 1];
    }




}
