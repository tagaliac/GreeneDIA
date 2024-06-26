package Monoid;

import Language.DIA;

import java.util.*;

public class GreensRelation {
    //Returns the String "value" in reverse order
    private static String reverseString(String value){
        try{
            char letter;
            StringBuilder result= new StringBuilder();
            for (int i=0;i<value.length();i++){
                letter=value.charAt(i);
                result.insert(0, letter);
            }
            return result.toString();
        }catch (NullPointerException e){
            return value;
        }
    }

    //Append if possible lettern to word in order to reduce the word throw the equal value
    //If [0;"position"-1] of the "word" with "key" is possible [0;"position"-1]+"value" will be return
    //If it is not possible it returns null
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

    //checks if the Relation "elementRcompare" is possible with the proper "equalList"
    private static boolean isR_Related(String element, String compare, EqualList equalList){
        boolean Rleft,Rright;
        String w1,w2;
        //w1 has to be the smaller word
        if(element.length()<=compare.length()){
            w1=element;
            w2=compare;
        }else{
            w1=compare;
            w2=element;
        }
        //checks if x exists in w1+x=w2
        if(w1.length()==0||w1.equalsIgnoreCase(Submonoid.Subsequence(w2,0,w1.length()))){
            Rleft=true;
        }else{
            Rleft=Relatible(w1,w2,equalList,true,true);
        }
        //checks if x exists in w2+x=w1
        Rright=Relatible(w1,w2,equalList,true,false);
        return Rleft&&Rright;
    }

    //the same as "ReduceWordR" just with the difference that it checks the beginning of the word
    public static String ReduceWordL(String word,String key,String value,int position){
        return reverseString(ReduceWordR(reverseString(word),reverseString(key),reverseString(value),position));
    }

    //checks if the Relation "elementLcompare" is possible with the proper "equalList"
    private static boolean isL_Related(String element, String compare, EqualList equalList){
        boolean Lleft,Lright;
        String w1,w2;
        //w1 has to be the smaller word
        if(element.length()<=compare.length()){
            w1=element;
            w2=compare;
        }else{
            w1=compare;
            w2=element;
        }
        //checks if x exists in x+w1=w2
        if(w1.length()==0||reverseString(w1).equalsIgnoreCase(Submonoid.Subsequence(reverseString(w2),0,w1.length()))){
            Lleft=true;
        }else{
            Lleft=Relatible(w1,w2,equalList,false,true);
        }
        //checks if x exists in x+w2=w1
        Lright=Relatible(w1,w2,equalList,false,false);
        return Lleft&&Lright;
    }

    //Checks the relation between the smaller word "w1" and the bigger word "w2" with the proper "equalList"
    //For isRrelation=true and isToLeft=true it checks if x exists in w1+x=w2
    //For isRrelation=true and isToLeft=false it checks if x exists in w2+x=w1
    //For isRrelation=false and isToLeft=true it checks if x exists in x+w1=w2
    //For isRrelation=false and isToLeft=false it checks if x exists in x+w2=w1
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
                if(reducedWord!=null&&reducedWord.length()<=w2.length()&&!reducedWord.equalsIgnoreCase(w2)){
                    w2=reducedWord;
                    if(isToLeft&&isRrelation){
                        if(w2.equalsIgnoreCase(Submonoid.Subsequence(w1,0,w2.length()))){
                            return true;
                        }
                        break;
                    }else if(isToLeft){
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
        //another last check
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
                } else if (isToLeft) {
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

    //Returns the Green Box of the "dia" with the maximum length "maxLength"
    //The results are saved as an array-matrix of strings
    public static List<String[][]> getGreenBox(DIA dia, int maxLength, boolean expandSearch, boolean fill){
        //gets and modifies all important variables
        List<String> submonoid= Submonoid.convertAlphabet(dia, maxLength);
        submonoid.sort(Comparator.comparingInt(String::length));
        EqualList equal=Equals.findEquals(dia, maxLength, expandSearch);
        submonoid=removeEqualsFromMonoid(submonoid,equal);
        List<String[][]> resultlist;
        if(fill){
            resultlist =splitInDClassesAndFill(createBox(submonoid,equal),equal,dia,maxLength);
        }else{
            resultlist =splitInDClasses(createBox(submonoid,equal),equal);
        }
        for(String[][] box:resultlist){
            fitValuesOfGreenBox(box,maxLength);
        }
        return resultlist;
    }

    private static String[][] createBox(List<String> submonoid, EqualList equal){
        String[][] result=new String[submonoid.size()][submonoid.size()];
        submonoid.sort(Comparator.comparingInt(String::length));
        List<String> Rlist=new ArrayList<>();
        List<String> Llist=new ArrayList<>();
        Rlist.add(submonoid.get(0));
        Llist.add(submonoid.get(0));
        result[0][0]=submonoid.get(0);
        boolean found;
        int RPos,LPos;
        for(String element:submonoid){
            if(element.equalsIgnoreCase(submonoid.get(0))){
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
        return reduceSizeOfGreenBox(result, Rlist.size(), Llist.size());
    }

    private static List<String[][]> splitInDClassesAndFill(String[][] box, EqualList equal,DIA dia, int length){
        List<String[][]> result=splitInDClasses(box,equal);
        int currentLength=length,x=0;
        int[] i_x=new int[result.size()],j_x=new int[result.size()];
        List<String> newSubmonoid,newWords=new ArrayList<>();
        boolean found, lineOfWords;
        for(String[][] eggbox:result) {
            for (int i = 0; i < eggbox.length; i++) {
                lineOfWords = true;
                for (int j = 0; j < eggbox[i].length; j++) {
                    if (eggbox[i][j] == null) {
                        lineOfWords = false;
                        break;
                    }
                }
                if (lineOfWords) {
                    i_x[x] = i;
                    break;
                }
            }

            for (int j = 0; j < eggbox[0].length; j++) {
                lineOfWords = true;
                for (int i = 0; i < eggbox.length; i++) {
                    if (eggbox[i][j] == null) {
                        lineOfWords = false;
                        break;
                    }
                }
                if (lineOfWords) {
                    j_x[x] = j;
                    break;
                }
            }
            x++;
        }
        do{
            found=false;
            currentLength++;
            newSubmonoid= Submonoid.createAndConvertSubmonoidSameLength(dia.getAlphabet(),currentLength);
            for (String word : removeEqualsFromMonoid(newSubmonoid, equal)) {
                if(word.length()==currentLength){
                    newWords.add(word);
                }
            }
            x=0;
            for(String[][] eggbox:result){
                for(int i=0;i<eggbox.length;i++){
                    for (int j=0;j<eggbox[i].length;j++){
                        if(eggbox[i][j]==null){
                            for (String word:newWords){
                                if((eggbox[i][j_x[x]]==null||isR_Related(word,eggbox[i][j_x[x]],equal))
                                        &&(eggbox[i_x[x]][j]==null||isL_Related(word,eggbox[i_x[x]][j],equal))){
                                    eggbox[i][j]=word;
                                    newWords.remove(word);
                                    found=true;
                                    break;
                                }
                            }
                        }
                    }
                }
                x++;
            }
        }while(found);
        //if words are too big to check
        x=0;
        for(String[][] eggbox:result){
            for(int i=0;i<eggbox.length;i++){
                for (int j=0;j<eggbox[i].length;j++){
                    if(eggbox[i][j]==null){
                        if(eggbox[i][j_x[x]]!=null&&eggbox[i_x[x]][j]!=null){
                            eggbox[i][j]= (removeEqualsFromMonoid(
                                    new ArrayList<>(Collections.singleton(eggbox[i][j_x[x]] + eggbox[i_x[x]][j])),
                                    equal)).get(0);
                        }
                        if(eggbox[i][j].length()>currentLength){
                            currentLength=eggbox[i][j].length();
                        }
                    }
                }
            }
            x++;
        }
        for(String[][] eggbox:result){
            fitValuesOfGreenBox(eggbox,currentLength-1);
        }
        return result;
    }

    private static List<String[][]> splitInDClasses(String[][] box, EqualList equal){
        List<String> submonoid1=new ArrayList<>(),submonoid2=new ArrayList<>();
        List<String[][]> result=new ArrayList<>();
        for (String[] strings:box) {
            for (int j=0;j<strings.length;j++) {
                if (strings[j]!=null&&strings[0]==null&&box[0][j]==null) {
                    submonoid2.add(strings[j]);
                } else if (strings[j]!=null) {
                    submonoid1.add(strings[j]);
                }
            }
        }
        if(submonoid2.isEmpty()){
            result.add(box);
            return result;
        }
        result.add(createBox(submonoid1,equal));
        result.addAll(splitInDClasses(createBox(submonoid2,equal),equal));
        return result;
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
        if(greenbox[0][0].equalsIgnoreCase("")){
            greenbox[0][0]="\u03BB";
        }
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
        List<String> submonoid= Submonoid.convertAlphabet(dia, maxLength);
        EqualList equal=Equals.findEquals(dia, maxLength, expandSearch);
        submonoid=removeEqualsFromMonoid(submonoid,equal);
        submonoid.sort(Comparator.comparingInt(String::length));
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