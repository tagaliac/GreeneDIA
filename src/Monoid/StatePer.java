package Monoid;

import Language.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatePer {
    private int[] statesNum;
    private String label;

    public StatePer(List<state> states, String label, DEA dea){
        this.statesNum=new int[states.size()];
        if(label==null){
            this.label="";
        }else{
            this.label=label;
        }
        for (int i=0;i<states.size();i++){
            this.statesNum[i]=ProcessWord(this.label, states.get(i), dea);
        }
    }

    private int ProcessWord(String word, state state, DEA dea){
        char[] wordChar = word.toCharArray();
        state currentState = state;
        for (int i=0; i<wordChar.length;i++){
            currentState = dea.GetState(currentState.process(wordChar[i]));
        }
        return currentState.getID();
    }

    public boolean CompareStatePers(StatePer compare){
        if(statesNum.length!=compare.statesNum.length){
            return false;
        }
        for (int i=0;i<statesNum.length;i++){
            if(this.statesNum[i]!=compare.statesNum[i]){
                return false;
            }
        }
        return true;
    }

    public boolean CompareStatePers(StatePer compare,int limit){
        if(statesNum.length!=compare.statesNum.length){
            return false;
        }
        if(limit> statesNum.length){
            throw new IndexOutOfBoundsException("limit ist bigger than the amounth of StatePers");
        }
        for (int i=0;i<statesNum.length;i++){
            if(this.statesNum[i]<limit &&this.statesNum[i]!=compare.statesNum[i]){
                return false;
            }
        }
        return true;
    }

    public String getLabel() {
        return label;
    }

    public int[] getStatesNum() {
        return statesNum;
    }

    public void DeleteStates(List<Integer> deleteStates){
        List<Integer> integerList= intArrayToList(statesNum);
        int i=0;
        while (i<integerList.size()){
            if(deleteStates.contains(integerList.get(i))){
                integerList.remove(i);
            }else{
                i++;
            }
        }
        this.statesNum= IntListToArray(integerList);
    }

    private List<Integer> intArrayToList(int[] array){
        List<Integer> result =new ArrayList<>();
        for(int i=0;i<array.length;i++){
            result.add(array[i]);
        }
        return result;
    }
    private int[] IntListToArray(List<Integer> list){
        int[] result =new int[list.size()];
        for(int i=0;i<result.length;i++){
            result[i]=list.remove(0);
        }
        return result;
    }
}
