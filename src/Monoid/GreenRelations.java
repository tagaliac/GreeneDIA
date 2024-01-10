package Monoid;

import Language.DIA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GreenRelations {
    private static boolean isR_Related(String word1, String word2, DIA dia, int maxLength, boolean expandSearch){
        boolean isLeft=false,isRight=false;
        boolean found;
        Transition word1Morphism= dia.getTransitionWithString(word1);
        Transition word2Morphism= dia.getTransitionWithString(word2);
        Transition compare;
        List<String> submonoid;
        if(expandSearch){
            submonoid= Equals.convertAlphabet(dia, maxLength*2);
        }else {
            submonoid= Equals.convertAlphabet(dia, maxLength);
        }
        submonoid.sort(Comparator.comparingInt(String::length));
        for(String element:submonoid){
            compare=dia.getTransitionWithString(word2+element);
            found=true;
            if(word1Morphism.getImage().length==compare.getImage().length){
                for (int i=0; i<word1Morphism.getImage().length; i++) {
                    if ((word1Morphism.getImage()[i]!=-1)&&(compare.getImage()[i]!=-1)
                            &&(word1Morphism.getImage()[i]!=compare.getImage()[i])){
                        found=false;
                        break;
                    }
                }
            }else {
                found=false;
            }
            if(found){
                isRight=true;
                break;
            }
        }
        for(String element:submonoid){
            compare=dia.getTransitionWithString(word1+element);
            found=true;
            if(word2Morphism.getImage().length==compare.getImage().length){
                for (int i=0; i<word2Morphism.getImage().length; i++) {
                    if ((word2Morphism.getImage()[i]!=-1)&&(compare.getImage()[i]!=-1)
                            &&(word2Morphism.getImage()[i]!=compare.getImage()[i])){
                        found=false;
                        break;
                    }
                }
            }else {
                found=false;
            }
            if(found){
                isLeft=true;
                break;
            }
        }
        return isLeft&&isRight;
    }

    private static boolean isL_Related(String word1, String word2, DIA dia, int maxLength, boolean expandSearch){
        boolean isLeft=false,isRight=false;
        boolean found;
        Transition word1Morphism= dia.getTransitionWithString(word1);
        Transition word2Morphism= dia.getTransitionWithString(word2);
        Transition compare;
        List<String> submonoid;
        if(expandSearch){
            submonoid= Equals.convertAlphabet(dia, maxLength*2);
        }else {
            submonoid= Equals.convertAlphabet(dia, maxLength);
        }
        submonoid.sort(Comparator.comparingInt(String::length));
        for(String element:submonoid){
            compare=dia.getTransitionWithString(element+word2);
            found=true;
            if(word1Morphism.getImage().length==compare.getImage().length){
                for (int i=0; i<word1Morphism.getImage().length; i++) {
                    if ((word1Morphism.getImage()[i]!=-1)&&(compare.getImage()[i]!=-1)
                            &&(word1Morphism.getImage()[i]!=compare.getImage()[i])){
                        found=false;
                        break;
                    }
                }
            }else {
                found=false;
            }
            if(found){
                isRight=true;
                break;
            }
        }
        for(String element:submonoid){
            compare=dia.getTransitionWithString(element+word1);
            found=true;
            if(word2Morphism.getImage().length==compare.getImage().length){
                for (int i=0; i<word2Morphism.getImage().length; i++) {
                    if ((word2Morphism.getImage()[i]!=-1)&&(compare.getImage()[i]!=-1)
                            &&(word2Morphism.getImage()[i]!=compare.getImage()[i])){
                        found=false;
                        break;
                    }
                }
            }else {
                found=false;
            }
            if(found){
                isLeft=true;
                break;
            }
        }
        return isLeft&&isRight;
    }

    //Returns the Green Box of the "dia" with the maximum length "maxLength"
    //The results are saved as an array-matrix of strings
    public static String[][] getGreenBox(DIA dia, int maxLength, boolean expandSearch){
        //gets and modifies all important variables
        List<String> submonoid= Equals.convertAlphabet(dia, maxLength);
        submonoid.sort(Comparator.comparingInt(String::length));
        EqualList equal=Equals.findEquals(dia, maxLength, expandSearch);
        String[][] result=new String[submonoid.size()][submonoid.size()];
        List<String> Rlist=new ArrayList<>();
        List<String> Llist=new ArrayList<>();
        Rlist.add("");
        Llist.add("");
        result[0][0] = "";
        submonoid=removeEqualsFromMonoid(submonoid,equal);
        boolean found;
        int RPos,LPos;
        //checks for relations and adds them properly
        for(String element:submonoid){
            if(element.equalsIgnoreCase("")||element.equalsIgnoreCase(" ")){
                continue;
            }
            RPos=0;LPos=0;
            found=false;
            for (int j=0;j< Rlist.size();j++){
                if((!element.equalsIgnoreCase(Rlist.get(j)))
                        &&isR_Related(element, Rlist.get(j),dia, maxLength,expandSearch)){
                    found=true;
                    RPos=j;
                    break;
                }
            }
            if(!found){
                RPos=Rlist.size();
                Rlist.add(element);
            }
            found=false;
            for (int j=0;j< Llist.size();j++){
                if((!element.equalsIgnoreCase(Llist.get(j)))
                        &&isL_Related(element, Llist.get(j),dia, maxLength, expandSearch)){
                    found=true;
                    LPos=j;
                    break;
                }
            }
            if(!found){
                LPos=Llist.size();
                Llist.add(element);
            }
            if(result[RPos][LPos]!=null){
                result[RPos][LPos]=element;
            }
        }
        //fits graphically the result-matrix
        result[0][0]="\u03BB";
        fitValuesOfGreenBox(result,maxLength);
        return reduceSizeOfGreenBox(result, Rlist.size(), Llist.size());
    }

    //Returns the Green Box of the "dia" with the maximum length "maxLength"
    //The results are saved as an array-matrix of strings
    public static String[][] getGreenBoxWithoutEqual(DIA dia, int maxLength, boolean expandSearch){
        //gets and modifies all important variables
        List<String> submonoid= Equals.convertAlphabet(dia, maxLength);
        submonoid.sort(Comparator.comparingInt(String::length));
        String[][] result=new String[submonoid.size()][submonoid.size()];
        List<String> Rlist=new ArrayList<>();
        List<String> Llist=new ArrayList<>();
        Rlist.add("");
        Llist.add("");
        result[0][0] = "";
        boolean found;
        int RPos,LPos;
        //checks for relations and adds them properly
        for(String element:submonoid){
            if(element.equalsIgnoreCase("")||element.equalsIgnoreCase(" ")){
                continue;
            }
            RPos=0;LPos=0;
            found=false;
            for (int j=0;j< Rlist.size();j++){
                if(isR_Related(element, Rlist.get(j),dia, maxLength,expandSearch)){
                    found=true;
                    RPos=j;
                    break;
                }
            }
            if(!found){
                RPos=Rlist.size();
                Rlist.add(element);
            }
            found=false;
            for (int j=0;j< Llist.size();j++){
                if(isL_Related(element, Llist.get(j),dia, maxLength,expandSearch)){
                    found=true;
                    LPos=j;
                    break;
                }
            }
            if(!found){
                LPos=Llist.size();
                Llist.add(element);
            }
            if(result[RPos][LPos]==null){
                result[RPos][LPos]=element;
            }
        }
        //fits graphically the result-matrix
        result[0][0]="\u03BB";
        fitValuesOfGreenBox(result,maxLength);
        return reduceSizeOfGreenBox(result, Rlist.size(), Llist.size());
    }

    //Removes all equal values in "equalList" to reduce the size of the "monoid"
    private static List<String> removeEqualsFromMonoid(List<String> monoid,EqualList equalList){
        List<String> result = new ArrayList<>();
        String el,oldEL;
        boolean clear;
        for(String element:monoid){
            el=element;
            clear=false;
            while(!clear){
                oldEL=el;
                for (int i = 0; i < equalList.Size(); i++) {
                    el = el.replaceAll(equalList.getKey().get(i), equalList.getValue().get(i));
                }
                if(el.equalsIgnoreCase(oldEL)){
                    clear=true;
                }
            }
            if(!result.contains(el)){
                result.add(el);
            }
        }
        return result;
    }

    //Removes null-entries of "greenbox" with "maxLength" and makes the entries equal long
    private static void fitValuesOfGreenBox(String[][] greenbox, int maxlength){
        for(int i=0;i<greenbox.length;i++){
            for(int j=0;j<greenbox[i].length;j++){
                if(greenbox[i][j]==null){
                    greenbox[i][j]= "+".repeat(maxlength);
                }else if(greenbox[i][j].length()<maxlength){
                    greenbox[i][j]+= "_".repeat(maxlength-greenbox[i][j].length());
                }
            }
        }
    }

    //Cuts the "greenbox" to the defined size
    private static String[][] reduceSizeOfGreenBox(String[][] greenbox, int height, int length){
        String[][] result = new String[height][length];
        for(int i=0;i<result.length;i++){
            System.arraycopy(greenbox[i], 0, result[i], 0, result[i].length);
        }
        return result;
    }

    //Returns a list of element that are H-Related
    //Each element of a sublist ist H-Related to the others
    public static List<List<String>> getHValues(DIA dia, int maxLength, boolean expandSearch){
        List<List<String>> result=new ArrayList<>();
        List<String> submonoid= Equals.convertAlphabet(dia, maxLength);
        EqualList equal=Equals.findEquals(dia, maxLength, expandSearch);
        submonoid=removeEqualsFromMonoid(submonoid,equal);
        submonoid.sort(Comparator.comparingInt(String::length));
        boolean newElement;
        for(String element:submonoid){
            newElement=true;
            for(String compare:submonoid){
                if((!element.equalsIgnoreCase(compare))&&isR_Related(element,compare,dia, maxLength,expandSearch)
                        &&isL_Related(element,compare,dia, maxLength,expandSearch)){
                    if(newElement){
                        result.add(new ArrayList<>());
                        result.get(result.size()-1).add(element);
                        newElement=false;
                    }
                    result.get(result.size()-1).add(compare);
                }
            }
        }
        return result;
    }
}
