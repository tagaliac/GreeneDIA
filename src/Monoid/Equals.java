package Monoid;

import Language.DIA;

import java.util.*;

import static Monoid.Submonoid.*;

public class Equals {
    //--vielleicht unnötig
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

    //returns a List with entries of words that can be interchanged in the DIA "dia" at same entry position
    //--macLength: the maximum length of the words
    public static EqualList findEqual(DIA dia, int maxLength){
        List<String> submonoid = convertAlphabet(dia,maxLength);
        List<String> allIdempotent;
        EqualList resultF =new EqualList();
        EqualList resultB =new EqualList();
        String currentKey;
        for(int i=0;i<submonoid.size();i++){
            currentKey=submonoid.get(i);
            allIdempotent=findIdempotents(dia,submonoid,currentKey);
            for(String element:allIdempotent){
                resultF.add(currentKey+ element,currentKey);
            }
        }
        resultF=clearUnnecessaryEquals(resultF, 4);
        for(int i=0;i<submonoid.size();i++){
            currentKey=submonoid.get(i);
            allIdempotent=findIdempotentsBack(dia,submonoid,currentKey);
            for(String element:allIdempotent){
                resultB.add(element+currentKey,currentKey);
            }
        }
        resultB=clearUnnecessaryEquals(resultB, 4);
        resultF.append(resultB);
        resultF=clearUnnecessaryEquals(resultF, 2);
        Equals.sortbyValue(resultF);
        return reverseMap(resultF);
    }

    //Displays the monoid
    public static void DisplayMonoid(List<String> monoid){
        for(int i=0;i< monoid.size();i++){
            System.out.print("["+monoid.get(i)+"]");
        }
    }

    // Returns a List of all alphabet permutations of "dia" with maximum Length "maxLength"
    public static List<String> convertAlphabet(DIA dia, int maxLength){
        List<String> result =new ArrayList<>();
        String[] array =createSubmonoid(dia.getAlphabet(),maxLength);
        for(int i=0;i<array.length;i++){
            result.add(array[i]);
        }
        Collections.sort(result);
        return result;
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

    public static void sortbyValue(EqualList equalList){
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

    private static void Sort(EqualList equalList, int begin, int end){
        if(end-begin<2){
            if(equalList.getValue().get(end-begin).length()>equalList.getValue().get(end-begin+1).length()){

            }
        }
        int mid=(end-begin)/2+begin;
        Sort(equalList,begin,mid);
        Sort(equalList,mid+1,end);
    }
}
