package Json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
//import org.json.*;
import org.json.*;


/**
 * JsonParser.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class JsonParser {

    private JsonElement object;

    public JsonParser(String fileLocation) {

        File file = new File(fileLocation);
        String content;
        try {
            content = FileUtils.readFileToString(file, "utf-8");
            JSONObject jo = new JSONObject(content); 
            object = new JsonElement("", "object");
            parseJson(jo, 0, object);
            print(object, 0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void parseJson(JSONObject jsonObject, int count, JsonElement element) {


        Set<Object> set = jsonObject.keySet();

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {

            Object obj = iterator.next();

            if (jsonObject.get((String) obj) instanceof JSONArray) {
                JsonElement array = new JsonElement((String) obj, "array", null, new ArrayList<String>());
                //                System.out.println(new String(new char[count]).replace('\0', ' ') + "" + (String) obj + " : ");
                getArray(jsonObject.get((String) obj), count + 1, (String) obj, array);
                if(element.getChildren() != null) {
                    element.getChildren().add(array);
                }
            } else if (jsonObject.get((String) obj) instanceof JSONObject) {
                JsonElement object = new JsonElement((String) obj, "object", new ArrayList<JsonElement>(), null);
                //                System.out.println(new String(new char[count]).replace('\0', ' ') + "" + (String) obj + " : ");
                parseJson((JSONObject) jsonObject.get((String) obj), count + 1, object);
                if(element.getChildren() != null) {
                    element.getChildren().add(object);
                }                
            } else {
                JsonElement property = new JsonElement((String) obj, "property", null, null);
                //                System.out.println(new String(new char[count]).replace('\0', ' ') + "" + (String) obj + " : " + jsonObject.get((String) obj));
                if(element.getChildren() != null) {
                    element.getChildren().add(property);
                }
            }
        }
    }

    public static void getArray(Object object2, int count, String name, JsonElement element) {

        JSONArray jsonArr = (JSONArray) object2;

        for (int k = 0; k < (jsonArr).length(); k++) {
            if (jsonArr.get(k) instanceof JSONObject) {

                JsonElement object = new JsonElement("" , "object");
                //                System.out.println(new String(new char[count]).replace('\0', ' ') + "" + (String) jsonArr.get(k) + " : ");
                parseJson((JSONObject) jsonArr.get(k), count + 1, object);
                if(element.getChildren() != null) {
                    element.getChildren().add(object);
                }                
            } else if(jsonArr.get(k) instanceof JSONArray){

                JsonElement array = new JsonElement((String) jsonArr.get(k), "array", null, new ArrayList<String>());
                //                System.out.println(new String(new char[count]).replace('\0', ' ') + "" + (String) jsonArr.get(k) + " : ");
                getArray(jsonArr.get(k), count + 1, name, array);
                if(element.getChildren() != null) {
                    element.getChildren().add(array);
                }

            } else {
                JsonElement property = new JsonElement((String) jsonArr.get(k), "property", null, null);
                //                System.out.println(new String(new char[count]).replace('\0', ' ') + " " + jsonArr.get(k));

                if(element.getChildren() != null) {
                    element.getChildren().add(property);
                }

            }
        }
    }


    public void print(JsonElement element, int count) {
        System.out.println(new String(new char[count]).replace('\0', ' ') + " " + element.getType() + " : " + element.getName());  
        if(element.getChildren() != null) {
            for(JsonElement a : element.getChildren()) {
                print(a, count + 1);
            }
        }
    }

    /**
     * Returns the {bare_field_name} for this JsonParser.
     * @return the object
     */
    public JsonElement getObject() {
        return object;
    }

    /**
     * Sets the object for this JsonParser
     * @param object the object to set
     */
    public void setObject(JsonElement object) {
        this.object = object;
    }


}


