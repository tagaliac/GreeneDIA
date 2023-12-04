package Monoid;

import Language.DIA;

import java.util.ArrayList;
import java.util.List;

public class GreensRelation {
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
        for(int i=w2.length()-1;i>=0;i--){
            for(int e=0;e<equalList.Size();e++){
                if(ReduceWordR(w2,equalList.getKey().get(e),equalList.getValue().get(e),i)!=null){
                    w2=ReduceWordR(w2,equalList.getKey().get(e),equalList.getValue().get(e),i);
                    if(w2.equalsIgnoreCase(w1)){
                        Rright=true;
                        break;
                    }
                    if(w2.length()<w1.length()){
                        break;
                    }
                }
            }
            if(Rright||w2.length()<w1.length()){break;}
        }
        return Rleft&&Rright;
    }

    public static String ReduceWordL(String word,String key,String value,int position){
        if(key.length()-position>=0
                &&Submonoid.Subsequence(word,0,position).equalsIgnoreCase(Submonoid.Subsequence(key,key.length()-position,key.length()))){
            return value+Submonoid.Subsequence(word,position,word.length());
        }
        return null;
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
        if(w1.length()==0||w1.equalsIgnoreCase(Submonoid.Subsequence(w2,0,w1.length()))){
            Lleft=true;
        }else{
            //fehlt
        }
        int i=1;
        while(i<w2.length()){
            for(int e=0;e<equalList.Size();e++){
                if(ReduceWordL(w2,equalList.getKey().get(e),equalList.getValue().get(e),i)!=null){
                    w2=ReduceWordL(w2,equalList.getKey().get(e),equalList.getValue().get(e),i);
                    i=1;
                    if(w2.equalsIgnoreCase(w1)){
                        Lright=true;
                        break;
                    }
                    if(w2.length()<w1.length()){
                        break;
                    }
                }else
                {
                    i++;
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
                }
            }
            if(!found){
                Rlist.add(element);
                RPos=Rlist.size();
            }
            found=false;
            for (int j=0;j< Llist.size();j++){
                if(isL_Related(element, Llist.get(j),equal)){
                    found=true;
                    LPos=j;
                }
            }
            if(!found){
                Llist.add(element);
                LPos=Rlist.size();
            }
            result[LPos][RPos]=element;
        }
        System.out.print(Rlist);
        return result;
    }

    private static List<String> removeEqualsFromMonoid(List<String> monoid,EqualList equalList){
        List<String> result = new ArrayList<>();
        String el;
        for(String element:monoid){
            el=element;
            for(int i=0;i<equalList.Size();i++){
                el.replaceAll(equalList.getKey().get(i),equalList.getValue().get(i));
            }
            result.add(el);
        }
        return result;
    }
}
