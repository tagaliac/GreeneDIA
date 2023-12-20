package Monoid;

import Language.DIA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Submonoid {
    //Creates a submonoid with the Alphabet "alphabet" and words with a maximum length of "maxLength"
    public static String[] createSubmonoid(List<Character> alphabet, int maxLength){
        String[] submonoid = new String[(int) Math.pow(alphabet.size(),maxLength+1)-1];
        int[] count = new int[maxLength];
        count[0]=1;
        submonoid[0]="";
        for(int i=1;i<submonoid.length;i++){
            submonoid[i]="";
            for(int position:count) {
                if (position!=0) {
                    submonoid[i]+=alphabet.get(position-1);
                }
            }
            count=raiseCounter(count,0, alphabet.size());
        }
        return submonoid;
    }

    //Raises the counter-array of "createSubmonoid"-function
    private static int[] raiseCounter(int[] counter, int position, int pow){
        if(position>= counter.length){
            //Resets counter
            Arrays.fill(counter, 0);
            return counter;
        }
        if(counter[position]>=pow){
            if(position>0){
                counter[position]=0;
            }
            counter[position]=1;
        }else {
            counter[position]++;
            return counter;
        }
        return raiseCounter(counter,position+1,pow);
    }

    //Returns a List of Idempotents of "DIA" in "submonoid" that can be interchanged with "word"
    //the function also puts "word" in the words of the "submonoid" to expand the search
    public static List<String> findALLIdempotents(DIA dia, List<String> submonoid, String word){
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
        return result;
    }

    //Returns the subsequence of the word "value" in ["Start";"End"]
    //A IndexOutOfBoundsException will be thrown if "End" is smaller than "Start"
    public static String Subsequence(String value, int Start, int End){
        if(End<Start){
            throw new IndexOutOfBoundsException("Start must be smaller than End");
        }
        if(End==Start){
            return "";
        }
        Character[] characters=new Character[value.length()];
        for(int i=0;i<value.length();i++){
            characters[i] =value.charAt(i);
        }
        StringBuilder result= new StringBuilder();
        for(int i=Start;i<End;i++){
            result.append(characters[i]);
        }
        return result.toString();
    }
}

