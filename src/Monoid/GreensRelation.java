package Monoid;

import Language.DIA;

import java.util.ArrayList;
import java.util.List;

public class GreensRelation {
    private static String reverseString(String value){
        try{
            char letter;
            String result="";
            for (int i=0;i<value.length();i++){
                letter=value.charAt(i);
                result=letter+result; //adds each character in front of the existing string
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
        boolean Rleft=false,Rright=false;
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
        }else{
            //fehlt
        }
        String reducedWord;
        for(int i=w2.length()-1;i>=0;i--){
            if(element.equalsIgnoreCase("aa")&&compare.equalsIgnoreCase("a")){
                System.out.println("");
            }
            for(int e=0;e<equalList.Size();e++){
                reducedWord=ReduceWordR(w2,equalList.getKey().get(e),equalList.getValue().get(e),i);
                if(element.equalsIgnoreCase("aa")&&compare.equalsIgnoreCase("a")){
                    System.out.println(reducedWord);
                }
                if(reducedWord!=null&&reducedWord.length()<=w2.length()){
                    w2=reducedWord;
                    if(w2.equalsIgnoreCase(w1)){
                        Rright=true;
                        break;
                    }
                    break;
                }
                if(e==equalList.Size()-1){
                    break;
                }
            }
            if(Rright||w2.length()<w1.length()){break;}
        }
        for(int e=0;e<equalList.Size();e++){
            reducedWord=ReduceWordR(w2,equalList.getKey().get(e),equalList.getValue().get(e),0);
            if(element.equalsIgnoreCase("aa")&&compare.equalsIgnoreCase("a")){
                System.out.println(reducedWord);
            }
            if(reducedWord!=null&&reducedWord.length()<=w2.length()){
                w2=reducedWord;
                if(w2.equalsIgnoreCase(w1)){
                    Rright=true;
                    break;
                }
                break;
            }
            if(e==equalList.Size()-1){
                break;
            }
        }
        return Rleft&&Rright;
    }

    public static String ReduceWordL(String word,String key,String value,int position){
        /*if(key.length()-position>=0
                &&Submonoid.Subsequence(word,0,position).equalsIgnoreCase(
                        Submonoid.Subsequence(reverseString(key),0,position))){
            return value+Submonoid.Subsequence(word,position,word.length());
        }
        return null;*/
        return reverseString(ReduceWordR(reverseString(word),reverseString(key),reverseString(value),position));
    }

    private static boolean isL_Related(String element, String compare, EqualList equalList){
        boolean Lleft=false,Lright=false;
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
        }else{
            //fehlt
        }
        String reducedWord;
        for(int i=w2.length()-1;i>=0;i--){
            for(int e=0;e<equalList.Size();e++){
                reducedWord=ReduceWordL(w2,equalList.getKey().get(e),equalList.getValue().get(e),i);
                if(reducedWord!=null&&reducedWord.length()<=w2.length()){
                    w2=reducedWord;
                    if(w2.equalsIgnoreCase(w1)){
                        Lright=true;
                        break;
                    }
                    break;
                }
            }
            if(Lright||w2.length()<w1.length()){break;}
        }
        return Lleft&&Lright;
    }

    public static String[][] getGreenBox(DIA dia, int maxLength){
        List<String> submonoid= Equals.convertAlphabet(dia, maxLength);
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
        System.out.println(Rlist);
        System.out.println(Llist);
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
}
