package Monoid;

import Language.DIA;

import java.util.*;

import static Monoid.Submonoid.*;

public class Equals {
    //returns a List with entries of words that can be interchanged in the DIA "dia" at same entry position
    //--maxLength: the maximum length of the words
    public static EqualList findEquals(DIA dia, int maxLength, boolean expandSearch){
        List<String> submonoid = convertAlphabet(dia,maxLength);
        List<String> allIdempotent;
        EqualList result =new EqualList();
        String currentKey;
        for(int i=0;i<submonoid.size();i++){
            currentKey=submonoid.get(i);
            allIdempotent=findALLIdempotents(dia,submonoid,currentKey, expandSearch);
            for(String element:allIdempotent){
                result.add(element,currentKey);
            }
        }
        result=clearUnnecessaryEquals(result, 4);
        Equals.sortByValue(result);
        return reverseMap(result);
    }

    //Displays the monoid
    //It exists for testing
    public static void DisplayMonoid(List<String> monoid){
        for (String entry:monoid) {
            System.out.print("["+entry+"]");
        }
    }

    //Returns a List of Idempotents of "DIA" in "submonoid" that can be interchanged with "word"
    //the function also puts "word" in the words of the "submonoid" to expand the search
    public static List<String> findALLIdempotents(DIA dia, List<String> submonoid, String word, boolean expandSearch){
        Transition transition;
        List<String> result=new ArrayList<>();
        Transition compare = dia.getTransitionWithString(word);
        boolean isIdempotent;
        String rest;
        for (int i=1;i<submonoid.size();i++){
            //without changing the words
            rest=submonoid.get(i);
            transition=dia.getTransitionWithString(rest);
            isIdempotent=((transition.getImage().length-transition.getInfiniteCases().size())>0);
            for (int j=0; j<transition.getImage().length; j++) {
                if ((transition.getImage()[j]!=-1)&&(compare.getImage()[j]!=-1)
                        &&(transition.getImage()[j]!=compare.getImage()[j])){
                    isIdempotent=false;
                }
            }
            if (isIdempotent) {
                result.add(rest);
            }
            if(expandSearch){
                //with a change of words
                for(int k=0;k<rest.length()+1;k++) {
                    transition=dia.getTransitionWithString(Subsequence(rest, 0, k) + word + Subsequence(rest, k, rest.length()));
                    isIdempotent=((transition.getImage().length-transition.getInfiniteCases().size())>0);
                    for (int j=0; j<transition.getImage().length; j++) {
                        if ((transition.getImage()[j]!=-1)&&(compare.getImage()[j]!=-1)
                                &&(transition.getImage()[j]!=compare.getImage()[j])){
                            isIdempotent=false;
                        }
                    }
                    if (isIdempotent) {
                        result.add(Subsequence(rest, 0, k) + word + Subsequence(rest, k, rest.length()));
                    }
                }
            }
        }
        return result;
    }

    //checks if "word1" and "word2" are equal in "dia"
    public static boolean isEqual(String word1, String word2, DIA dia){
        Transition word1transition= dia.getTransitionWithString(word1);
        Transition word2transition= dia.getTransitionWithString(word2);
        if((word1transition.getImage().length!=word2transition.getImage().length)||
                ((word1transition.getImage().length-word1transition.getInfiniteCases().size())<=0)||
                ((word2transition.getImage().length-word2transition.getInfiniteCases().size())<=0)){
            return false;
        }
        for (int j=0; j<word1transition.getImage().length; j++) {
            if ((word1transition.getImage()[j]!=-1)&&(word2transition.getImage()[j]!=-1)
                    &&(word1transition.getImage()[j]!=word2transition.getImage()[j])){
                return false;
            }
        }
        return true;
    }

    //Removes obsolet entries in EqualList "equal"
    //the function has to eventually repeated multiple times to be return the correct value
    //--times defines how often that has to be
    private static EqualList clearUnnecessaryEquals(EqualList equal, int times){
        EqualList result= new EqualList(new ArrayList<>(),new ArrayList<>());
        String firstKey, firstValue;
        while(!equal.isEmpty()){
            firstKey=equal.getKey().get(0);
            firstValue=equal.getValue().get(0);
            result.add(firstKey,firstValue);
            equal.remove(0);
            if(firstKey.length()>firstValue.length()){
                equal=removeEqual(equal,firstKey,firstValue);
            }
        }
        if(times<=1){
            return result;
        }
        return clearUnnecessaryEquals(reverseMap(result),times-1);
    }

    //Returns a word in which all key entries in "equal" were replaced with its value entries
    private static EqualList removeEqual(EqualList equal, String key, String value){
        EqualList result=new EqualList();
        EqualList temp=new EqualList();
        for(int i=0;i<equal.Size();i++){
            temp.add(equal.getKey().get(i).replaceAll(key,value),equal.getValue().get(i).replaceAll(key,value));
        }
        for(int i=0;i<temp.Size();i++){
            if(!temp.getKey().get(i).equalsIgnoreCase(temp.getValue().get(i))){
                result.add(temp.getKey().get(i),temp.getValue().get(i));
            }
        }
        return result;
    }

    //Returns the "equalList" in reverse order
    private static EqualList reverseMap(EqualList equalList){
        EqualList result= new EqualList(new ArrayList<>(),new ArrayList<>());
        String firstKey, firstValue;
        while(!equalList.isEmpty()){
            firstKey=equalList.getKey().get(equalList.getKey().size()-1);
            firstValue=equalList.getValue().get(equalList.getKey().size()-1);
            equalList.remove(equalList.getKey().size()-1);
            result.add(firstKey,firstValue);
        }
        return result;
    }

    //It sorts the "equalList" by the size of the values
    public static void sortByValue(EqualList equalList){
        if(equalList.isEmpty()){
            return;
        }
        int i=equalList.Size()-1;
        int length=equalList.getValue().get(i).length();
        while(i>0){
            i--;
            if(equalList.getValue().get(i).length()<length){
                equalList.add(equalList.getKey().get(i),equalList.getValue().get(i));
                equalList.remove(i);
                i=equalList.Size()-1;
            }
            length=equalList.getValue().get(i).length();
        }
    }
}
