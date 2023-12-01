package Monoid;

import Language.DIA;

import java.util.*;

import static Monoid.DClassDecom.*;

public class Equals {
    public static List<String> removeUnique(List<String> relations){
        List<String> result=new ArrayList<>();
        while(relations.size()>2){
            if(!relations.get(0).equalsIgnoreCase(relations.get(1))){
                result.add(relations.remove(0));
            }else {
                relations.remove(0);
            }
        }
        if(!relations.get(0).equalsIgnoreCase(relations.get(1))){
            result.add(relations.remove(0));
        }else {
            relations.remove(0);
        }
        result.add(relations.remove(0));
        return result;
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

    //alte Version
    //returns a List with entries of words that can be interchanged in the DIA "dia" at same entry position
    //--macLength: the maximum length of the words
    /*public static EqualList findEqual(DIA dia, int maxLength){
        List<String> submonoid = convertAlphabet(dia,maxLength);
        List<String> allIdempotent;
        EqualList result =new EqualList(new ArrayList<>(),new ArrayList<>());
        String currentKey;
        for(int i=0;i< submonoid.size();i++){
            currentKey=submonoid.get(i);
            allIdempotent=findIdempotents(dia,submonoid,currentKey);
            for(String element:allIdempotent){
                result.add(currentKey+ element,currentKey);
            }
        }
        result=clearUnnecessaryEquals(result);
        return result;
    }*/

    //returns a List with entries of words that can be interchanged in the DIA "dia" at same entry position
    //--macLength: the maximum length of the words
    public static EqualList findEqual(DIA dia, int maxLength){
        List<String> submonoid = convertAlphabet(dia,maxLength);
        List<String> allIdempotent;
        EqualList result =new EqualList(new ArrayList<>(),new ArrayList<>());
        String currentKey;
        for(int i=0;i< submonoid.size();i++){
            currentKey=submonoid.get(i);
            allIdempotent=findIdempotents(dia,submonoid,currentKey);
            for(String element:allIdempotent){
                result.add(currentKey+ element,currentKey);
            }
        }
        for (int i=0;i<result.Size();i++){
            result=clearUnnecessaryEquals(result);
        }
        return result;
    }

    //--vielleicht unnötig
    public static void DisplayMonoid(List<String> monoid){
        for(int i=0;i< monoid.size();i++){
            System.out.print("["+monoid.get(i)+"]");
        }
    }

    // Returns a List of all alphabet permutations of "dia" with maximum Length "maxLength"
    private static List<String> convertAlphabet(DIA dia, int maxLength){
        List<String> result =new ArrayList<>();
        String[] array =createSubmonoid(dia.getAlphabet(),maxLength);
        for(int i=0;i<array.length;i++){
            result.add(array[i]);
        }
        Collections.sort(result);
        return result;
    }

    //Removes obsolet entries in EqualList "equal"
    //work in Progress
    private static EqualList clearUnnecessaryEquals(EqualList equal){
        EqualList result= new EqualList(new ArrayList<>(),new ArrayList<>());
        String firstKey, firstValue;
        while(!equal.isEmpty()){
            firstKey=equal.getKey().get(0);
            firstValue=equal.getValue().get(0);
            result.add(firstKey,firstValue);
            equal.remove(0);
            equal=removeEqual(equal,firstKey,firstValue);
        }
        return reverseMap(result);
    }

    private static EqualList reverseMap(EqualList map){
        EqualList result= new EqualList(new ArrayList<>(),new ArrayList<>());
        String firstKey, firstValue;
        while(!map.isEmpty()){
            firstKey=map.getKey().get(map.getKey().size()-1);
            firstValue=map.getValue().get(map.getKey().size()-1);
            map.remove(map.getKey().size()-1);
            result.add(firstKey,firstValue);
        }
        return result;
    }
}
