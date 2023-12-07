package Monoid;

import Language.DIA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GreensRelation {
    private static String reverseString(String value){
        try{
            char letter;
            String result="";
            for (int i=0;i<value.length();i++){
                letter=value.charAt(i);
                result=letter+result;
            }
            return result;
        }catch (NullPointerException e){
            return value;
        }
    }

    public static String ReduceWordR(String word,String key,String value,int position){
        if(Submonoid.Subsequence(word,position,Math.min(position+key.length(),word.length())).equalsIgnoreCase(
                Submonoid.Subsequence(key,0,Math.min(key.length(),word.length()-position)))){
            if(word.length()-position>key.length()){
                return Submonoid.Subsequence(word,0,position)+value+Submonoid.Subsequence(word,position+key.length(),word.length());
            }
            return Submonoid.Subsequence(word,0,position)+value;
        }
        return null;
    }

    private static boolean isR_Related(String element, String compare, EqualList equalList){
        boolean Rleft=false,Rright;
        String w1,w2;
        if(element.length()<=compare.length()){
            w1=element;
            w2=compare;
        }else{
            w1=compare;
            w2=element;
        }
        if(w1.length()==0||w1.equalsIgnoreCase(Submonoid.Subsequence(w2,0,w1.length()))){
            Rleft=true;
        }else if(w1.length()!=0){
            Rleft=Relatible(w1,w2,equalList,true,true);
        }
        Rright=Relatible(w1,w2,equalList,true,false);
        return Rleft&&Rright;
    }

    public static String ReduceWordL(String word,String key,String value,int position){
        return reverseString(ReduceWordR(reverseString(word),reverseString(key),reverseString(value),position));
    }

    private static boolean isL_Related(String element, String compare, EqualList equalList){
        boolean Lleft=false,Lright;
        String w1,w2;
        if(element.length()<=compare.length()){
            w1=element;
            w2=compare;
        }else{
            w1=compare;
            w2=element;
        }
        if(w1.length()==0||reverseString(w1).equalsIgnoreCase(Submonoid.Subsequence(reverseString(w2),0,w1.length()))){
            Lleft=true;
        }else if(w1.length()!=0){
            Lleft=Relatible(w1,w2,equalList,false,true);
        }
        Lright=Relatible(w1,w2,equalList,false,false);
        return Lleft&&Lright;
    }

    private static boolean Relatible(String w1, String w2, EqualList equalList, boolean isRrelation, boolean isToLeft){
        String reducedWord;
        if(isToLeft){
            String temp=w1;
            w1=w2;
            w2=temp;
        }
        for(int i=w2.length()-1;i>=0;i--){
            for(int e=0;e<equalList.Size();e++){
                if(isRrelation){
                    reducedWord=ReduceWordR(w2,equalList.getKey().get(e),equalList.getValue().get(e),i);
                }else{
                    reducedWord=ReduceWordL(w2,equalList.getKey().get(e),equalList.getValue().get(e),i);
                }
                if(reducedWord!=null&&reducedWord.length()<=w2.length()){
                    w2=reducedWord;
                    if(isToLeft&&isRrelation){
                        if(w2.equalsIgnoreCase(Submonoid.Subsequence(w1,0,w2.length()))){
                            return true;
                        }
                        break;
                    }else if(isToLeft&&!isRrelation){
                            if(reverseString(w2).equalsIgnoreCase(Submonoid.Subsequence(reverseString(w1),0,w2.length()))) {
                                return true;
                            }
                            break;
                    }else{
                        if(w2.equalsIgnoreCase(w1)){
                            return true;
                        }
                        break;
                    }
                }
            }
            if(isToLeft){
                if(w1.length()<w2.length()){break;}
            }else{
                if(w2.length()<w1.length()){break;}
            }
        }
        for(int e=0;e<equalList.Size();e++){
            if(isRrelation){
                reducedWord=ReduceWordR(w2,equalList.getKey().get(e),equalList.getValue().get(e),0);
            }else{
                reducedWord=ReduceWordL(w2,equalList.getKey().get(e),equalList.getValue().get(e),0);
            }
            if(reducedWord!=null&&reducedWord.length()<=w2.length()) {
                w2 = reducedWord;
                if (isToLeft && isRrelation) {
                    if (w2.equalsIgnoreCase(Submonoid.Subsequence(w1, 0, w2.length()))) {
                        return true;
                    }
                    break;
                } else if (isToLeft && !isRrelation) {
                    if (reverseString(w2).equalsIgnoreCase(Submonoid.Subsequence(reverseString(w1), 0, w2.length()))) {
                        return true;
                    }
                    break;
                } else{
                    if (w2.equalsIgnoreCase(w1)) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    public static String[][] getGreenBox(DIA dia, int maxLength){
        List<String> submonoid= Equals.convertAlphabet(dia, maxLength);
        Collections.sort(submonoid, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length()-o2.length();
            }
        });
        EqualList equal=Equals.findEqual(dia, maxLength);
        String[][] result=new String[submonoid.size()][submonoid.size()];
        List<String> Rlist=new ArrayList<>();
        List<String> Llist=new ArrayList<>();
        Rlist.add("");
        Llist.add("");
        result[0][0] = "";
        submonoid=removeEqualsFromMonoid(submonoid,equal);
        boolean found;
        int RPos,LPos;
        for(String element:submonoid){
            if(element.equalsIgnoreCase("")||element.equalsIgnoreCase(" ")){
                continue;
            }
            RPos=0;LPos=0;
            found=false;
            for (int j=0;j< Rlist.size();j++){
                if(isR_Related(element, Rlist.get(j),equal)){
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
                if(isL_Related(element, Llist.get(j),equal)){
                    found=true;
                    LPos=j;
                    break;
                }
            }
            if(!found){
                LPos=Llist.size();
                Llist.add(element);
            }
            result[RPos][LPos]=element;
        }
        result[0][0]="\u03BB";
        fitValuesOfGreenBox(result,maxLength);
        return reduceSizeOfGreenBox(result, Rlist.size(), Llist.size());
    }

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

    private static String[][] reduceSizeOfGreenBox(String[][] greenbox, int height, int length){
        String[][] result = new String[height][length];
        for(int i=0;i<result.length;i++){
            for(int j=0;j<result[i].length;j++){
                result[i][j]=greenbox[i][j];
            }
        }
        return result;
    }

    public static List<List<String>> getHValues(DIA dia, int maxLength){
        List<List<String>> result=new ArrayList<>();
        List<String> submonoid= Equals.convertAlphabet(dia, maxLength);
        EqualList equal=Equals.findEqual(dia, maxLength);
        submonoid=removeEqualsFromMonoid(submonoid,equal);
        Collections.sort(submonoid, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length()-o2.length();
            }
        });
        boolean newElement;
        for(String element:submonoid){
            newElement=true;
            for(String compare:submonoid){
                if((!element.equalsIgnoreCase(compare))&&isR_Related(element,compare,equal)&&isL_Related(element,compare,equal)){
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
