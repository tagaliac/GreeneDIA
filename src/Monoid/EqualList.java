package Monoid;

import java.util.ArrayList;
import java.util.List;

// creates an equal list that works as a hashtable
// --key = a set of keys (as Strings)
// --value = a set of value (as Strings)
// key and value must have the same size
public class EqualList {
    private final List<String> key;
    private final List<String> value;

    public EqualList(List<String> keys, List<String> values) {
        this.key = keys;
        this.value = values;
        if (!isKeyAndValueSameSize()) {
            throw new IndexOutOfBoundsException("key and Value must be at same size");
        }
    }

    public EqualList() {
        this.key = new ArrayList<>();
        this.value = new ArrayList<>();
    }

    //Returns the list of keys
    public List<String> getKey() {
        return key;
    }

    //Returns the list of values
    public List<String> getValue() {
        return value;
    }

    //checks if key and value have the same size
    private boolean isKeyAndValueSameSize(){
        return this.key.size() == this.value.size();
    }

    //Adds an entry ("key","value") at the end of the list
    public void add(String key, String value) {
        this.key.add(key);
        this.value.add(value);
        if (!isKeyAndValueSameSize()) {
            throw new IndexOutOfBoundsException("key and Value must be at same size");
        }
    }

    //Removes the entry at position "position"
    public void remove(int position) {
        if(position<0||position>this.key.size()){
            throw new IndexOutOfBoundsException("position value is not valid");
        }
        this.key.remove(position);
        this.value.remove(position);
        if (!isKeyAndValueSameSize()) {
            throw new IndexOutOfBoundsException("key and Value must be at same size");
        }
    }

    //Returns the size of the list
    public int Size(){
        return this.key.size();
    }

    //Checks if List is empty
    public boolean isEmpty(){
        return !(this.Size()>0);
    }

    //For testing
    //Displays the entry at position
    public void display(int position){
        System.out.print("("+key.get(position)+"/"+value.get(position)+")\n");
    }

    //Returns the entry at position
    public String getEntry (int position){
        String Key, Value;
        if (value.get(position).equalsIgnoreCase("")){
            Value="\u03BB";
        }else {
            Value=value.get(position);
        }
        if (key.get(position).equalsIgnoreCase("")){
            Key="\u03BB";
        }else {
            Key=key.get(position);
        }
        return "["+Key+"="+Value+"]";
    }

    //Append "equalList"
    public void append(EqualList equalList){
        for(int i=0;i<equalList.Size();i++){
            this.add(equalList.getKey().get(i),equalList.getValue().get(i));
        }
    }
}
