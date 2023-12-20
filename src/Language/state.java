package Language;

import java.util.Hashtable;
import java.util.List;

// creates state
// --ID: the id of the state
// --alphabet: the set of letters the transfer functions of the state contains
// --TransferFunctions: A hashtable that maps a letter to a state with id
public class state {
    private final int ID;
    private final List<Character> alphabet;
    private final Hashtable<Character, Integer> TransferFunctions = new Hashtable<>();

    public state(int ID, List<Character> alphabet){
        this.ID=ID;
        this.alphabet = alphabet;
        for (Character letter:alphabet) {
            TransferFunctions.put(letter, ID);
        }
    }

    //Remaps the transfer with letter "transitionValue" to the state with the ID "nextStateID"
    //transmissionValue must be in the set of letters
    public void ChangeTransferFunction(Character transitionValue, int nextStateID){
        if (!TransferFunctions.containsKey(transitionValue)){
            throw new ArrayStoreException("the state "+this.ID+" does not contain the char "+transitionValue);
        }
        TransferFunctions.replace(transitionValue, nextStateID);
    }

    //returns the id of the state that will be reached with the letter "transitionValue"
    //transmissionValue must be in the set of letters
    public int process(Character transitionValue){
        if (!TransferFunctions.containsKey(transitionValue)){
            throw new ArrayStoreException("the state "+this.ID+" does not contain the char "+transitionValue);
        }
        return TransferFunctions.get(transitionValue);
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
