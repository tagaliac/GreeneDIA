package Default;

import Language.*;
import Monoid.*;
import Monoid.Equals;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        DIA Dyck = createDyck(15);
        DIA Dyckplus = createDyckPlus(10);
        DIA Hand = createHAnd(15,15);
        DIA Hor = createHOr(15,15);

        EqualList equal=Equals.findEqual(Hor,6);
        for (int i=0;i<equal.getKey().size();i++){
            equal.display(i);
        }
    }

    private static Tabelle createFiniteTestDEA(){
        List<Character> alphabet = new ArrayList<>();
        List<state> states = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalstates = new ArrayList<>();

        //define alphabet
        alphabet.add('a');
        alphabet.add('b');
        alphabet.add('c');
        alphabet.add('d');

        //define states
        states.add(new state(0,alphabet));
        states.add(new state(1,alphabet));
        states.add(new state(2,alphabet));
        states.add(new state(3,alphabet));

        //define transfer functions
        transferFunctions.add(new TransferFunction(0,'a',1));
        transferFunctions.add(new TransferFunction(0,'b',2));
        transferFunctions.add(new TransferFunction(0,'c',1));
        transferFunctions.add(new TransferFunction(0,'d',1));

        transferFunctions.add(new TransferFunction(1,'a',2));
        transferFunctions.add(new TransferFunction(1,'b',0));
        transferFunctions.add(new TransferFunction(1,'c',0));
        transferFunctions.add(new TransferFunction(1,'d',0));

        transferFunctions.add(new TransferFunction(2,'a',3));
        transferFunctions.add(new TransferFunction(2,'b',3));
        transferFunctions.add(new TransferFunction(2,'c',3));
        transferFunctions.add(new TransferFunction(2,'d',3));

        transferFunctions.add(new TransferFunction(3,'a',2));
        transferFunctions.add(new TransferFunction(3,'b',3));
        transferFunctions.add(new TransferFunction(3,'c',2));
        transferFunctions.add(new TransferFunction(3,'d',2));

        //define final states
        finalstates.add(3);

        //define dea
        DEA dea = new DEA(3, alphabet, transferFunctions, 0, finalstates);
        System.out.println(dea.acceptWord("ab"));//true
        System.out.println(dea.acceptWord("bb"));//false
        System.out.println(dea.acceptWord("bbb"));//false
        System.out.println(dea.acceptWord(""));//false
        return new Tabelle(dea);
    }

    private static DIA createDyck(int length) {
        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalstates = new ArrayList<>();

        //define alphabet
        alphabet.add('a');
        alphabet.add('b');

        //define transfer functions
        transferFunctions.add(new TransferFunction(0, 'a', 0));
        transferFunctions.add(new TransferFunction(0, 'b', 0));

        for (int i = 1; i < length - 1; i++) {
            transferFunctions.add(new TransferFunction(i, 'a', i + 1));
            transferFunctions.add(new TransferFunction(i, 'b', i - 1));
        }

        transferFunctions.add(new TransferFunction(length - 1, 'a', length - 1));
        transferFunctions.add(new TransferFunction(length - 1, 'b', length - 2));

        //define final states
        finalstates.add(1);

        List<Integer> InfiniteStates = new ArrayList<>();
        InfiniteStates.add(length - 1);
        return new DIA(length, alphabet, transferFunctions, 1, finalstates, InfiniteStates);
    }


    private static DIA createDyckPlus(int length){
        if(length<3){
            throw new IndexOutOfBoundsException("length must be 3 or higher");
        }
        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalstates = new ArrayList<>();

        //define alphabet
        alphabet.add('a');
        alphabet.add('b');

        //define transfer functions
        transferFunctions.add(new TransferFunction(0,'a',0));
        transferFunctions.add(new TransferFunction(0,'b',0));

        transferFunctions.add(new TransferFunction(1,'a',0));
        transferFunctions.add(new TransferFunction(1,'b',2));

        transferFunctions.add(new TransferFunction(2,'a',3));
        transferFunctions.add(new TransferFunction(2,'b',0));

        transferFunctions.add(new TransferFunction(3,'a',5));
        transferFunctions.add(new TransferFunction(3,'b',2));

        transferFunctions.add(new TransferFunction(4,'a',5));
        transferFunctions.add(new TransferFunction(4,'b',2));

        for(int i=5;i<length+2;i++){
            transferFunctions.add(new TransferFunction(i,'a',i+1));
            transferFunctions.add(new TransferFunction(i,'b',i-1));
        }

        //transferFunctions.add(new TransferFunction(length+2,'a',length+3));
        transferFunctions.add(new TransferFunction(length+2,'b',length+1));

        //define final states
        finalstates.add(3);

        //define dia
        List<Integer> InfiniteStates=new ArrayList<>();
        InfiniteStates.add(length+2);
        return new DIA(length+3,alphabet,transferFunctions,1,finalstates,InfiniteStates);
    }

    private static DIA createHAnd(int length1,int length2){
        if(length1<3||length2<3){
            throw new IndexOutOfBoundsException("length1 and length2 must be 3 or higher");
        }

        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalstates = new ArrayList<>();
        List<Integer> InfinitieStates = new ArrayList<>();

        //define alphabet
        alphabet.add('a');
        alphabet.add('b');

        //define transfer functions
        transferFunctions.add(new TransferFunction(0,'a',0));
        transferFunctions.add(new TransferFunction(0,'b',0));

        transferFunctions.add(new TransferFunction(1,'a',2));
        transferFunctions.add(new TransferFunction(1,'b',0));

        transferFunctions.add(new TransferFunction(2,'a',2+length2));
        transferFunctions.add(new TransferFunction(2,'b',0));

        transferFunctions.add(new TransferFunction(3,'a',4));
        transferFunctions.add(new TransferFunction(3,'b',1));

        for(int i=4;i<length1*length2-length2+2;i++){
            if((i-2)%length2==0){
                transferFunctions.add(new TransferFunction(i,'a',i+length2));
                transferFunctions.add(new TransferFunction(i,'b',i-length2+1));
            }else if((i-2)%length2==1){
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-length2-1));
            }else if((i-2)%length2==length2-1){
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
                InfinitieStates.add(i);
            }else{
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }
        }

        for(int i=length1*length2-length2+2;i<length1*length2+2;i++){
            if((i-2)%length2==0){
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-length2+1));
                InfinitieStates.add(i);
            }else if((i-2)%length2==1){
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-length2-1));
            }else if((i-2)%length2==length2-1){
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
                InfinitieStates.add(i);
            }else{
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }
        }

        //define final states
        finalstates.add(1);

        //transform
        return new DIA(length1*length2+2, alphabet, transferFunctions, 1, finalstates,InfinitieStates);
    }

    private static DIA createHOr(int length1,int length2){
        if(length1<3||length2<3){
            throw new IndexOutOfBoundsException("length1 and length2 must be 3 or higher");
        }

        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalstates = new ArrayList<>();
        List<Integer> InfinitieStates = new ArrayList<>();

        //define alphabet
        alphabet.add('a');
        alphabet.add('b');

        //define transfer functions
        transferFunctions.add(new TransferFunction(0,'a',0));
        transferFunctions.add(new TransferFunction(0,'b',0));

        transferFunctions.add(new TransferFunction(1,'a',1+length2));
        transferFunctions.add(new TransferFunction(1,'b',0));

        transferFunctions.add(new TransferFunction(2,'a',3));
        transferFunctions.add(new TransferFunction(2,'b',0));

        for(int i=3;i<length1*length2-length2+1;i++){
            if((i-1)%length2==0){
                transferFunctions.add(new TransferFunction(i,'a',i+length2));
                transferFunctions.add(new TransferFunction(i,'b',i-length2+1));
            }else if((i-1)%length2==1){
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-length2-1));
            }else if((i-1)%length2==length2-1){
                InfinitieStates.add(i);
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }else{
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }
        }

        for(int i=length1*length2-length2+1;i<length1*length2+3;i++){
            if((i-1)%length2==0){
                InfinitieStates.add(i);
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-length2+1));
            }else if((i-1)%length2==1){
                if(i==length1*length2+2){
                    InfinitieStates.add(i);
                    transferFunctions.add(new TransferFunction(i,'a',i));
                }else{
                    transferFunctions.add(new TransferFunction(i,'a',i+1));
                }
                transferFunctions.add(new TransferFunction(i,'b',i-length2-1));
            }else if((i-1)%length2==length2-1){
                InfinitieStates.add(i);
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }else{
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }
        }

        //define final states
        finalstates.add(2);

        //define dea
        DIA dia = new DIA(length1*length2+3, alphabet, transferFunctions, 1, finalstates,InfinitieStates);
        System.out.println(dia.acceptWord("aababb"));//true
        System.out.println(dia.acceptWord("abab"));//false
        System.out.println(dia.acceptWord("aabb"));//true

        return dia;
    }

    private static void PrintList(List<String> StringList,int valuesInLine){
        int count=0;
        for(int i=0;i<StringList.size();i++){
            System.out.print(StringList.get(i));
            if(count>=valuesInLine){
                count=0;
                System.out.print("\n");
            }else{
                count++;
            }
        }
    }
}
