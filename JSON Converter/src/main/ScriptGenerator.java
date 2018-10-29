package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
    public static void generateScript(ArrayList<String> left, ArrayList<String> right, String location) {

        String script = writeScript(left, right);

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
    private static String writeScript(ArrayList<String> left, ArrayList<String> right) {
        String script = "";
        script = script + "let fileInput = document.getElementById(\"csv\");\r\n" + 
                "\r\n" + 
                "let readFile = function () {\r\n" + 
                "  let reader = new FileReader();\r\n" + 
                "  reader.onload = function () {\r\n" + 
                "    parse(reader.result);\r\n" + 
                "  };\r\n" + 
                "  reader.readAsText(fileInput.files[0], 'utf8');\r\n" + 
                "};\r\n" + 
                "fileInput.addEventListener('change', readFile);\r\n" + 
                "\r\n" + 
                "function CSVToArray(strData, strDelimiter) {\r\n" + 
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
                "  let arrMatches = null;\r\n" + 
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
                "      strMatchedValue = arrMatches[2].replace(\r\n" + 
                "        new RegExp(\"\\\"\\\"\", \"g\"),\r\n" + 
                "        \"\\\"\"\r\n" + 
                "      );\r\n" + 
                "    } else {\r\n" + 
                "      strMatchedValue = arrMatches[3];\r\n" + 
                "    }\r\n" + 
                "    arrData.push(strMatchedValue);\r\n" + 
                "  }\r\n" + 
                "\r\n" + 
                "  return (arrData);\r\n" + 
                "}\r\n" + 
                "\r\n" + 
                "function parse(csv) {\r\n" + 
                "  let allTextLines = csv.split(/\\r\\n|\\n/);\r\n" + 
                "  let data = [];\r\n" + 
                "\r\n" + 
                "  // TODO: SANITIZE DATA - IF THE DATA HAS AN EMPTY CELL, THIS FOR LOOP CRASHES THIS PROGRAM.\r\n" + 
                "  for (let i = 0; i < allTextLines.length; i++) {\r\n" + 
                "    if (allTextLines[i].length !== 0) {\r\n" + 
                "      data[i] = CSVToArray(allTextLines[i]);\r\n" + 
                "    }\r\n" + 
                "  }\r\n" + 
                "  arrayToJSON(data);\r\n" + 
                "  return data;\r\n" + 
                "}\r\n" + 
                "\r\n" + 
                "function arrayToJSON(arr) {\r\n" + 
                "  let converted = {};\r\n" + 
                "  converted.name = \"PUBLIC_ART\";\r\n" + 
                "  converted.type = \"FeatureCollection\";\r\n" + 
                "  converted.features = [];\r\n" + 
                "\r\n" + 
                "  console.log(arr);\r\n" + 
                "\r\n" + 
                "  for (let i = 0; i < arr.length - 1; i++) {\r\n" + 
                "    let row = arr[i + 1]; // TODO: CONDITIONAL STATEMENT for header rows in CSV data\r\n" + 
                "    let feature = {};\r\n" + 
                "\r\n" + 
                "    feature.type = \"Feature\";\r\n" + 
                "\r\n" + 
                "    let geometry = {};\r\n" + 
                "    geometry.type = \"Point\";\r\n" + 
                "    geometry.coordinates = [row[18], row[19]];\r\n" + 
                "\r\n" + 
                "    feature.geometry = geometry;\r\n" + 
                "\r\n" + 
                "    let properties = {};\r\n" + 
                "\r\n";
        
        
        for(int i = 0; i < left.size();i++) {
            String[] data = left.get(i).split("/");
            script = script + "    " + data[0] + "." + data[1] + " = row[" + right.get(i) + "];\r\n";
        }
        
        script = script +
                "\r\n" + 
                "    feature.properties = properties;\r\n" + 
                "    converted.features.push(feature);\r\n" + 
                "  }\r\n" + 
                "\r\n" + 
                "  downloadObjectAsJson(converted, \"Sampledata-json-public-art\");\r\n" + 
                "  return JSON.stringify(converted, null, 4);\r\n" + 
                "}\r\n" + 
                "\r\n" + 
                "\r\n" + 
                "function downloadObjectAsJson(exportObj, exportName) {\r\n" + 
                "  let dataStr = \"data:text/json;charset=utf-8,\" + encodeURIComponent(JSON.stringify(exportObj));\r\n" + 
                "  let downloadAnchorNode = document.createElement('a');\r\n" + 
                "  downloadAnchorNode.setAttribute(\"href\", dataStr);\r\n" + 
                "  downloadAnchorNode.setAttribute(\"download\", exportName + \".json\");\r\n" + 
                "  document.body.appendChild(downloadAnchorNode); // required for firefox\r\n" + 
                "  downloadAnchorNode.click();\r\n" + 
                "  downloadAnchorNode.remove();\r\n" + 
                "}\r\n" + 
                "";
        
        
        return script;
    }




}

