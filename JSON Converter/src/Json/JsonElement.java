package Json;

import java.util.ArrayList;
import java.util.List;

/**
 * JsonElement.
 *
 * @author Tommy Yeh (Jen-Hao) A01025451 
 * @version 2017
 */
public class JsonElement {


    private String type;
    private List<JsonElement> children;
    private String name;
    private List<String> array;



    /**
     * Constructs an objecct of type JsonElement.
     * @param type
     * @param name
     */
    public JsonElement(String name, String type) {
        super();
        this.type = type;
        this.name = name;
        children = new ArrayList<JsonElement>();
        array = new ArrayList<String>();
    }

    public JsonElement(String name, String type, List<JsonElement> children, List<String> array) {
        super();
        this.type = type;
        this.name = name;
        this.children = children;
        this.array = array;
    }


    /**
     * Returns the {bare_field_name} for this JsonElement.
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * Sets the type for this JsonElement
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * Returns the {bare_field_name} for this JsonElement.
     * @return the children
     */
    public List<JsonElement> getChildren() {
        return children;
    }
    /**
     * Sets the children for this JsonElement
     * @param children the children to set
     */
    public void setChildren(List<JsonElement> children) {
        this.children = children;
    }
    /**
     * Returns the {bare_field_name} for this JsonElement.
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name for this JsonElement
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public void addObject(String name) {
        children.add(new JsonElement(name, "object"));
    }

    public void addArray(String name) {
        children.add(new JsonElement(name, "array", null, new ArrayList<String>()));
    }

    /**
     * Returns the {bare_field_name} for this JsonElement.
     * @return the array
     */
    public List<String> getArray() {
        return array;
    }

    /**
     * Sets the array for this JsonElement
     * @param array the array to set
     */
    public void setArray(List<String> array) {
        this.array = array;
    }

    public void addProperty(String name) {
        children.add(new JsonElement(name, "property", null, null));
    }

    public List<String> getJsonHeaders() {
        List<String> list = new ArrayList<String>();

        if(getChildren() != null) {
            for(JsonElement a : getChildren()) {           
                if(a.getType().equals("property")) {
                    list.add(getName());
                } else {
                    List<String> childList = a.getJsonHeaders();
                    for(String str : childList) {
                        if(!list.contains(getName() + "*" + str)) {
                            list.add(getName() + "*" + str);
                        }
                    }
                }
            }
        }
        return list;
    }



    public ArrayList<String> getArrays(){
        ArrayList<String> temp = new ArrayList<String>();
        if(children == null) {
            return null;
        }
        for(JsonElement a : children) {
            if(a.type.equals("object") && a.containsObject()) {
                temp.add(a.name);
            }
        }
        return temp;
    }
    public ArrayList<JsonElement> getObjectArrays(){
        ArrayList<JsonElement> temp = new ArrayList<JsonElement>();
        if(children == null) {
            return null;
        }
        for(JsonElement a : children) {
            if(a.type.equals("object") && a.containsObject()) {
                temp.add(a);
            }
        }
        return temp;
    }

    public Boolean isEndObject() {
        for(JsonElement a : children) {
            if((a.getChildren()==null || a.getChildren().size() == 0) && type.equals("object")) {
                return true;
            } else {
                if(a.isEndObject()) {
                    return true;
                }
            }
        }
        return false;  
    }
    public Boolean containsObject() {
        
       for(JsonElement a : children) {
           if(a.type.equals("object")) {
               return true;
           }
       }
       return false;
    }
}
