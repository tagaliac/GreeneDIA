package Language;

import Monoid.Transition;

import java.util.ArrayList;
import java.util.List;

// creates Deterministic Finite Automata
// --states: the numbers of states
// --alphabet: the set of letters
// --TransferFunctions: the set of transfer functions
//                      missing transfer functions will be self looped
// --IDOfStartStates: the id of the start state (must be in {0,...,states})
// --IDOfFinalStates: the set of ids of the final state (must be in {0,...,states})
public class DEA {
    List<state> states;
    List<Character> alphabet;
    List<TransferFunction> TransferFunctions;
    int IDOfStartState;
    List<Integer> IDsOfFinalStates;

    public DEA(int amounthOfStates, List<Character> alphabet, List<TransferFunction> TransferFunctions,
             int IDOfStartState, List<Integer> IDsOfFinalStates){
        //define states
        this.states=new ArrayList<>();
        for(int i=0;i<amounthOfStates;i++){
            this.states.add(new state(i,alphabet));
        }
        //define alphabet
        this.alphabet  =alphabet;
        //define transfer functions
        this.InitiateTransferFunctions(amounthOfStates,alphabet);
        for (TransferFunction transferFunction: TransferFunctions){
            ChangeTransfer(transferFunction);
        }
        this.TransferFunctions=TransferFunctions;
        //define start state
        if(!ContainsState(IDOfStartState)){
            throw new NullPointerException("the start state with the ID: "+IDOfStartState+" does not exists");
        }
        this.IDOfStartState=IDOfStartState;
        //define end state
        for (Integer finalState:IDsOfFinalStates){
            if(!ContainsState(finalState)){
                throw new NullPointerException("the final state with the ID: "+finalState+" does not exists");
            }
        }
        this.IDsOfFinalStates=IDsOfFinalStates;
    }

    //This function checks if the state with the id "ID" is in the DEA
    private Boolean ContainsState(int ID){
        for (state state:this.states){
            if(state.getID()==ID){
                return true;
            }
        }
        return false;
    }

    //Returns the state with the id "ID" in the DEA
    public state GetState(int ID){
        for (state state:this.states){
            if(state.getID()==ID){
                return state;
            }
        }
        return null;
    }

    //Sets all States to self loop every letter
    private List<TransferFunction> InitiateTransferFunctions(int amounthOfStates, List<Character> alphabet){
        List<TransferFunction> result = new ArrayList<>();
        for(int i=0;i<amounthOfStates;i++){
            for(Character letter:alphabet){
                result.add(new TransferFunction(i,letter,i));
            }
        }
        return result;
    }

    //Changes transfer function of DEA
    public void ChangeTransfer(TransferFunction transfer){
        // checking input values
        state StartState = null;
        state EndState = null;
        if(!alphabet.contains(transfer.getTransmissionValue())){
            throw new NullPointerException("the Character : "+transfer.getTransmissionValue()+" does not exists");
        }
        for (int i=0;i<states.size();i++){
            if(states.get(i).getID()==transfer.getStartStateID()){
                StartState=states.get(i);
                break;
            }
        }
        for (int i=0;i<states.size();i++){
            if(states.get(i).getID()==transfer.getEndStateID()){
                EndState=states.get(i);
                break;
            }
        }
        if(StartState==null){
            throw new NullPointerException("the startstate with the ID: "+transfer.getStartStateID()+" does not exists");
        }
        if(EndState==null){
            throw new NullPointerException("the endstate with the ID: "+transfer.getEndStateID()+" does not exists");
        }

        //coding
        StartState.ChangeTransferFunction(transfer.getTransmissionValue(), EndState.getID());
    }

    //Process the word "word" in the DEA and returns the state id of the holding state
    public int ProcessWord(String word){
        char[] wordChar = word.toCharArray();
        state currentState = GetState(IDOfStartState);
        for (int i=0; i<wordChar.length;i++){
            currentState = GetState(currentState.process(wordChar[i]));
        }
        return currentState.getID();
    }

    //Process the word "word" in the DEA and checks if the word will be accepted
    public boolean acceptWord(String word){
        int ID = ProcessWord(word);
        for (int i=0;i<IDsOfFinalStates.size();i++){
            if(IDsOfFinalStates.get(i)==ID){
                return true;
            }
        }
        return false;
    }

    public List<state> getStates() {
        return states;
    }

    public List<Character> getAlphabet() {
        return alphabet;
    }

    public List<TransferFunction> getTransferFunctions() {
        return TransferFunctions;
    }

    //returns the Transition with the char "letter"
    public Transition getTransitionWithOneChar(Character letter){
        List<TransferFunction> transferFunctions=new ArrayList<>();
        for(TransferFunction transferFunction: this.TransferFunctions){
            if(transferFunction.getTransmissionValue()==letter){
                transferFunctions.add(transferFunction);
            }
        }
        return new Transition(states.size(),transferFunctions);
    }

    //returns the Transition of the whole charsequence "word"
    public Transition getTransitionWithString(String word){
        //checks empty word
        if(word==""||word==" "){
            return new Transition(states.size());
        }
        //the algorithm
        Transition transition = getTransitionWithOneChar(word.charAt(0));
        Transition temp2;
        Transition temp;
        for (int i=0;i<word.length()-1;i++){
            temp = transition.copy();
            temp2=getTransitionWithOneChar(word.charAt(i+1));
            for(int j=0;j< transition.getImage().length;j++){
                transition.setImage(temp2.getImage()[temp.getImage()[j]],j);
            }
        }
        return transition;
    }
}
