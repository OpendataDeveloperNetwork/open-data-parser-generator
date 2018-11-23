package Json;

import java.util.ArrayList;

/**
 * JSONElement.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 * 
 * 
 * 
 * -------------------Known Issues
 * 
 * JSONElement does not correctly store Arrays in JSON Schema
 * -Cannot store complicated attributes of JSON Schema such as oneOf, definition, or arrays
 * -could be fixed with a list containing all attributes of JSONElement or creating a class for storing other attributes
 * 
 */
public class JSONElement {
    private String key; //Stores its own key for ease of use
    private String type; //stores type of JSONObject/JSONArray it is
    private ArrayList<JSONElement> children; //Stores children in a tree structure.
    private String completePath; //Used for name of generated array inside generated javascript file to uniquely identify JSONElement.


    /**
     * Returns the key for this JSONElement.
     * @return the key
     */
    public String getKey() {
        return key;
    }


    /**
     * Sets the key for this JSONElement
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }


    /**
     * Returns the Type for this JSONElement.
     * @return the type
     */
    public String getType() {
        return type;
    }


    /**
     * Sets the type for this JSONElement
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Returns the children for this JSONElement.
     * @return the children
     */
    public ArrayList<JSONElement> getChildren() {
        return children;
    }


    /**
     * Sets the children for this JSONElement
     * @param children the children to set
     */
    public void setChildren(ArrayList<JSONElement> children) {
        this.children = children;
    }

    public void addChild(JSONElement a) {
        children.add(a);
    }


    /**
     * Constructs an object of type JSONElement.
     * @param key
     */
    public JSONElement(String key, String path) {
        super();
        this.key = key;
        completePath = path + key;
        children = new ArrayList<JSONElement>();
    }


    /**
     * Returns the completePath for this JSONElement.
     * @return the completePath
     */
    public String getCompletePath() {
        return completePath;
    }


    /**
     * Sets the completePath for this JSONElement
     * @param completePath the completePath to set
     */
    public void setCompletePath(String completePath) {
        this.completePath = completePath;
    }


    public ArrayList<String> getLeaves(ArrayList<String> strArray) {
        if(isLeaf()) {
            strArray.add(key);
        }
        if(children != null || children.size() == 0) {
            for(JSONElement i : children) {
                i.getLeaves(strArray);
            }
        }
        return strArray;
    }

    /**
     * Prints info to console.
     */
    public void print() {
        System.out.println("Key:" + key);
        System.out.println("Type:" + type);
        if(children != null || children.size() == 0) {
            for(JSONElement i : children) {
                System.out.println("Child: ");
                i.print();
            }
        }
    }


    /**
     * checks if JSONElememnt is a leaf node of the JSONObject
     * @return
     */
    public boolean isLeaf() {
        if(type != null && !type.equals("object") && !type.equals("array")) {
            return children.size() == 0;
        } else {
            return false;
        }
    }

    /**
     * Returns a complete list of all the leaf nodes inside the children of this JSONObject
     * @param strArray
     * @param path
     * @return
     */
    public ArrayList<String> getCompleteLeaves(ArrayList<String> strArray, String path) {
        if(isLeaf()) {
            System.out.println(path + "/" + key);
            strArray.add(path + key+ "/");
        } else if(children != null || children.size() == 0) {
            for(JSONElement i : children) {
                i.getCompleteLeaves(strArray, path + key + "/");
            }
        }
        return strArray;
    }
}
