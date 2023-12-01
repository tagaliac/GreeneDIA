package Language;

import Monoid.Transition;

import java.util.ArrayList;
import java.util.List;

// creates Deterministic Infinite Automata
// Inherites DEA
// --IDOfInfiniteStates: the set of ids of the state that continue infinitely (must be in {0,...,states})
public class DIA extends DEA{
    List<Integer> infiniteStates;

    public DIA(int states, List<Character> alphabet, List<TransferFunction> TransferFunctions,
               int IDOfStartState, List<Integer> IDsOfFinalStates,List<Integer> infiniteStates){
        super(states,alphabet,TransferFunctions,IDOfStartState,IDsOfFinalStates);
        //define infinite states
        this.infiniteStates=infiniteStates;
        if(!InfiniteStatesInDEA()){
            throw new NullPointerException("Infinite state not in the automata");
        }
    }

    //Checks if the infinite states are in the automata
    private boolean InfiniteStatesInDEA(){
        boolean result;
        for (int infinite:this.infiniteStates){
            result=false;
            for(state state:this.states){
                if(infinite==state.getID()){
                    result=true;
                }
            }
            if(!result){
                return false;
            }
        }
        return true;
    }

    //returns the Transition with the char "letter"
    public Transition getTransitionWithOneChar(Character letter){
        List<TransferFunction> transferFunctions=new ArrayList<>();
        for(TransferFunction transferFunction: this.TransferFunctions){
            if(transferFunction.getStartStateID()!=-1&&transferFunction.getTransmissionValue()==letter){
                if(this.infiniteStates.contains(transferFunction.getStartStateID())){
                    transferFunctions.add(new TransferFunction(transferFunction.getStartStateID(),transferFunction.getTransmissionValue(),-1));
                }else{
                    transferFunctions.add(transferFunction);
                }
            }
        }
        return new Transition(states.size(),transferFunctions, this.infiniteStates);
    }

    public List<Integer> getInfiniteStates() {
        return infiniteStates;
    }

    //returns the Transition of the whole charsequence "word"
    public Transition getTransitionWithString(String word){
        //checks empty word
        if(word==""||word==" "){
            return new Transition(states.size());
        }
        //the algorithm
        Transition result = getTransitionWithOneChar(word.charAt(0));
        result.setInfiniteCases(this.infiniteStates);
        Transition temp2;
        Transition temp;
        for (int i=0;i<word.length()-1;i++){
            temp = result.copy();
            temp2=getTransitionWithOneChar(word.charAt(i+1));
            for(int j=0;j< result.getImage().length;j++){
                if(temp.getImage()[j]==-1){
                    result.setImage(-1,j);
                }else{
                    result.setImage(temp2.getImage()[temp.getImage()[j]],j);
                }
            }
        }
        return result;
    }
}
