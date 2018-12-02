package Json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * JSONParser.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class JSONParser {

    ArrayList<String> leafNodes;
    ArrayList<String> completeLeafNodes;
    JSONElement object;

    /**
     * Constructs an object of type JSONParser.
     * @param fileLocation
     */
    public JSONParser(String fileLocation) {
        File file = new File(fileLocation);
        String content;
        try {
            leafNodes = new ArrayList<String>();
            completeLeafNodes = new ArrayList<String>();
            content = FileUtils.readFileToString(file, "utf-8");
            JSONObject jo = new JSONObject(content); 

            Map<String, Object> myMap = jsonToMap(jo);

            System.out.println(myMap);
            object = toJSONElement("", myMap, "root");
            object.print();
            object.getLeaves(leafNodes);
            object.getCompleteLeaves(completeLeafNodes, "");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the leaf nodes for this JSONParser.
     * @return the leafNodes
     */
    public ArrayList<String> getLeafNodes() {
        return leafNodes;
    }

    /**
     * Sets the leafNodes for this JSONParser
     * @param leafNodes the leafNodes to set
     */
    public void setLeafNodes(ArrayList<String> leafNodes) {
        this.leafNodes = leafNodes;
    }

    /**
     * Returns the {bare_field_name} for this JSONParser.
     * @return the j
     */
    public JSONElement getObject() {
        return object;
    }

    /**
     * Sets the j for this JSONParser
     * @param j the j to set
     */
    public void setObject(JSONElement object) {
        this.object = object;
    }

    //reads JSON to map
    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new TreeMap<String, Object>();

        if(json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    /**
     * Converts a JSONObject to a Map<String, Object>
     * @param object
     * @return
     * @throws JSONException
     */
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new TreeMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * Part of the toMap function
     * @param array
     * @return
     * @throws JSONException
     */
    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * Part of the toMap function
     * @param key
     * @param map
     * @param path
     * @return
     */
    public JSONElement toJSONElement(String key, Map<String, Object> map, String path) {

        JSONElement i = new JSONElement(key, path);
        Map<String, Object> m = map;
        for (Map.Entry<String, Object> entry : m.entrySet())
        {
            if(entry.getValue() instanceof Map<?, ?> ) {
                if(!entry.getKey().equals("definitions")) {
                    i.addChild(toJSONElement(entry.getKey(), (Map<String, Object>) entry.getValue(), i.getCompletePath()));
                }
            } else {
                if(entry.getValue() instanceof String && entry.getKey().equals("type")) {
                    i.setType((String) entry.getValue());
                }
            }
        }
        return i;
    }

    
    /**
     * Return complete leaf nodes
     * @return
     */
    public ArrayList<String> getCompleteLeafNodes() {
        return completeLeafNodes;
    }





}


