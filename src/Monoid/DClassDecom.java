package Monoid;

import Language.DIA;

import java.util.ArrayList;
import java.util.List;

public class DClassDecom {
    /*public static void DClassDecompensation(DEA dea,int maxLength){
        Character[] alphabet = dea.getAlphabet().toArray(new Character[0]);
        String[] submonoid = createSubmonoid(alphabet,maxLength);
        List<String> W = Arrays.stream(submonoid.clone()).toList();
        W=RemoveIdempotents(dea,W);
        String value;
        while(!W.isEmpty()){
            value=W.remove(0);
        }
    }

    public static void DClassCompensation(DEA dea, int maxLength, String idempotent){
        int n=0;
        List<String> submonoid = Arrays.stream(createSubmonoid(dea.getAlphabet().toArray(new Character[0]), maxLength)).toList();
        for(String element:submonoid){
            //if(dea.getTransitionWithString(element).getRang()==dea.getTransitionWithString(element+idempotent).getRang()
            //&&)
        }
    }*/

    public static String[] createSubmonoid(List<Character> alphabet, int maxLength){
        String[] submonoid = new String[(int) Math.pow(alphabet.size(),maxLength)];
        int[] count = new int[maxLength];
        count[0]=1;
        submonoid[0]="";
        for(int i=1;i<submonoid.length;i++){
            submonoid[i]="";
            for( int j=0;j<count.length;j++){
                if(count[j]!=0){
                    submonoid[i]+=alphabet.get(count[j]-1);
                }
            }
            count=raiseCounter(count,0, alphabet.size());
        }
        return submonoid;
    }

    private static int[] raiseCounter(int[] counter, int position, int pow){
        int[] result=counter;
        if(position>=result.length){
            for( int j=0;j<result.length;j++){
                result[j]=0;
            }
            return result;
        }
        if(result[position]>=pow){
            if(position>0){
                result[position]=0;
            }
            result[position]=1;
        }else {
            result[position]++;
            return result;
        }
        return raiseCounter(result,position+1,pow);
    }

    public static String FirstIdempotent(DIA dia, List<String> submonoid){
        Transition transition;
        boolean isIdempotent;
        for (int i=1;i<submonoid.size();i++){
            transition=dia.getTransitionWithString(submonoid.get(i));
            isIdempotent=((transition.getImage().length-transition.getInfiniteCases().size())>0);
            for(int j=0;j<transition.getImage().length;j++){
                if((transition.getImage()[j]!=-1)&&(transition.getImage()[j]!=j)){
                    isIdempotent=false;
                }
            }
            if(isIdempotent){
                return submonoid.get(i);
            }
        }
        return null;
    }

    public static List<String> findIdempotents(DIA dia, List<String> submonoid, String value){
        Transition transition;
        List<String> result=new ArrayList<>();
        Transition compare = dia.getTransitionWithString(value);
        boolean isIdempotent;
        for (int i=1;i<submonoid.size();i++){
            transition=dia.getTransitionWithString(value+submonoid.get(i));
            isIdempotent=((transition.getImage().length-transition.getInfiniteCases().size())>0);
            for(int j=0;j<transition.getImage().length;j++){
                if((transition.getImage()[j]!=-1)&&(compare.getImage()[j]!=-1)
                        &&(transition.getImage()[j]!=compare.getImage()[j])){
                    isIdempotent=false;
                }
            }
            if(isIdempotent){
                result.add(submonoid.get(i));
            }
        }
        return result;
    }

    public static List<String> RemoveIdempotent(DIA dia,List<String> submonoid){
        List<String> result=new ArrayList<>();
        String idempotent= FirstIdempotent(dia, submonoid);
        for(int i=0;i<submonoid.size();i++){
            result.add(submonoid.get(i));
        }
        for(int i=0;i<result.size();i++){
            if(result.get(i)==idempotent){
                result.remove(i);
                i--;
            }
        }
        result.replaceAll(x -> x.replace(idempotent,""));
        return result;
    }

    public static List<String> RemoveAllIdempotent(DIA dia,List<String> submonoid, String value){
        List<String> result=new ArrayList<>();
        List<String> idempotents= findIdempotents(dia, submonoid, value);
        for(int i=0;i<submonoid.size();i++){
            result.add(submonoid.get(i));
        }
        for (String idempotent:idempotents){
            for(int i=0;i<result.size();i++){
                if(result.get(i)==idempotent){
                    result.remove(i);
                    i--;
                }
            }
            result.replaceAll(x -> x.replace(idempotent,value));
        }
        return result;
    }

    public static List<String> DeleteFirstElementOfSubmonoid(List<String> monoid, String BeginningString){
        List<String> submonoid=monoid.subList(0, monoid.size());
        List<String> result= new ArrayList<>();
        for (int i=0;i<submonoid.size();i++){
            if(BeginningString.length()>0&&BeginningString.length()<submonoid.get(i).length()
                &&Subsequence(submonoid.get(i),0,BeginningString.length()).equals(BeginningString)){
                result.add(Subsequence(submonoid.get(i),BeginningString.length(),submonoid.get(i).length()));
            }else{
                result.add(submonoid.get(i));
            }
        }
        return result;
    }
    public static List<String> DeleteElementOfSubmonoid(List<String> monoid, String deleteString){
        List<String> submonoid=monoid.subList(0, monoid.size());
        List<String> result= new ArrayList<>();
        result.replaceAll(x->x.replace(deleteString,""));
        return result;
    }

    private static String Subsequence(String value, int Start, int End){
        if(End<=Start){
            throw new IndexOutOfBoundsException("Start must be smaller than End");
        }
        Character[] characters=new Character[value.length()];
        for(int i=0;i<value.length();i++){
            characters[i] =value.charAt(i);
        }
        String result="";
        for(int i=Start;i<End;i++){
            result+= characters[i];
        }
        return result;
    }
}
