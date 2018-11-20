package Json;

import java.util.ArrayList;

/**
 * JSONElement.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class JSONElement {
    private String key;
    private String type;
    private ArrayList<JSONElement> children;
    private String completePath;


    /**
     * Returns the {bare_field_name} for this JSONElement.
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
     * Returns the {bare_field_name} for this JSONElement.
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
     * Returns the {bare_field_name} for this JSONElement.
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
     * Constructs an objecct of type JSONElement.
     * @param key
     */
    public JSONElement(String key, String path) {
        super();
        this.key = key;
        completePath = path + key;
        children = new ArrayList<JSONElement>();
    }


    /**
     * Returns the {bare_field_name} for this JSONElement.
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
                //  if(type == null || type.equals("object") || type.equals("array")) {
                i.getLeaves(strArray);
            }
        }
        return strArray;
    }

    public void print() {
        System.out.println("Key:" + key);
        System.out.println("Type:" + type);
        if(children != null || children.size() == 0) {
            for(JSONElement i : children) {
                //  if(type == null || type.equals("object") || type.equals("array")) {
                System.out.println("Child: ");
                i.print();
            }
        }
    }


    public boolean isLeaf() {
        if(type != null && !type.equals("object") && !type.equals("array")) {
            System.out.println("LEAF NODE " + key + " : " +  type);
            return children.size() == 0;
        } else {
            return false;
        }
    }


    public ArrayList<String> getCompleteLeaves(ArrayList<String> strArray, String path) {
        if(isLeaf()) {
            System.out.println(path + "/" + key);
            strArray.add(path + key+ "/");
        } else if(children != null || children.size() == 0) {
            for(JSONElement i : children) {
                //  if(type == null || type.equals("object") || type.equals("array")) {
                i.getCompleteLeaves(strArray, path + key + "/");
            }
        }
        return strArray;
    }
}
