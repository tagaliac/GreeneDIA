package Language;

import java.util.Hashtable;
import java.util.List;

// creates state
// --ID: the id of the state
// --alphabet: the set of letters the transfer functions of the state contains
// --AmounthOfTransferFunctions: the numbers of transfer functions of the state
// --Transferfunctions: A hashtable that maps a letter to a state with id
public class state {
    private int ID;
    private List<Character> alphabet;
    private int AmounthOfTransferFunctions;
    private Hashtable<Character, Integer> Transferfunctions = new Hashtable<Character, Integer>();

    public state(int ID, List<Character> alphabet){
        this.ID=ID;
        this.alphabet = alphabet;
        this.AmounthOfTransferFunctions = alphabet.size();
        for(int i=0;i<AmounthOfTransferFunctions;i++){
            Transferfunctions.put(alphabet.get(i), ID);
        }
    }

    //Remaps the transfer with letter "transsissionValue" to the state with the ID "nextStateID"
    //transmissionValue must be in the set of letters
    public void ChangeTransferFunction(Character transsissionValue, int nextStateID){
        if (!Transferfunctions.containsKey(transsissionValue)){
            throw new ArrayStoreException("the state "+this.ID+" does not contain the char "+transsissionValue);
        }
        Transferfunctions.replace(transsissionValue, nextStateID);
    }

    //returns the id of the state that will be reached with the letter "transsissionValue"
    //transmissionValue must be in the set of letters
    public int process(Character transsissionValue){
        if (!Transferfunctions.containsKey(transsissionValue)){
            throw new ArrayStoreException("the state "+this.ID+" does not contain the char "+transsissionValue);
        }
        return Transferfunctions.get(transsissionValue);
    }

    //Returns the ID of the state
    public int getID() {
        return ID;
    }

    //Returns the set of letters
    public List<Character> getAlphabet() {
        return alphabet;
    }
}
