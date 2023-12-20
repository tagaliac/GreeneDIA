package Default;

import Graphic.window;
import Language.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        //test functions
        /* DIA[] dias = new DIA[5];
        DEA TestDEA = createFiniteTestDEA();
        dias[0] = createDyck(15);
        dias[1] = createDyckPlus(10);
        dias[2] = createHAnd(15,15);
        dias[3] = createHOr(15,15);
        dias[4] = createDyckWithoutEmpty(15);*/

        window.setWindow();

    }

    public static DEA createFiniteTestDEA(){
        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalStates = new ArrayList<>();

        //define alphabet
        alphabet.add('a');
        alphabet.add('b');
        alphabet.add('c');
        alphabet.add('d');

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
        finalStates.add(3);

        //define dea
        return new DEA(3, alphabet, transferFunctions, 0, finalStates);
    }

    public static DIA createDyck(int length) {
        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalStates = new ArrayList<>();

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
        finalStates.add(1);

        List<Integer> InfiniteStates = new ArrayList<>();
        InfiniteStates.add(length - 1);
        return new DIA(length, alphabet, transferFunctions, 1, finalStates, InfiniteStates);
    }


    public static DIA createDyckPlus(int length){
        if(length<3){
            throw new IndexOutOfBoundsException("length must be 3 or higher");
        }
        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalStates = new ArrayList<>();

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
        finalStates.add(3);

        //define dia
        List<Integer> InfiniteStates=new ArrayList<>();
        InfiniteStates.add(length+2);
        return new DIA(length+3,alphabet,transferFunctions,1,finalStates,InfiniteStates);
    }

    public static DIA createHAnd(int length1,int length2){
        if(length1<3||length2<3){
            throw new IndexOutOfBoundsException("length1 and length2 must be 3 or higher");
        }

        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalStates = new ArrayList<>();
        List<Integer> infiniteStates = new ArrayList<>();

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
                infiniteStates.add(i);
            }else{
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }
        }

        for(int i=length1*length2-length2+2;i<length1*length2+2;i++){
            if((i-2)%length2==0){
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-length2+1));
                infiniteStates.add(i);
            }else if((i-2)%length2==1){
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-length2-1));
            }else if((i-2)%length2==length2-1){
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
                infiniteStates.add(i);
            }else{
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }
        }

        //define final states
        finalStates.add(1);

        //transform
        return new DIA(length1*length2+2, alphabet, transferFunctions, 1, finalStates,infiniteStates);
    }

    public static DIA createHOr(int length1,int length2){
        if(length1<3||length2<3){
            throw new IndexOutOfBoundsException("length1 and length2 must be 3 or higher");
        }

        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalStates = new ArrayList<>();
        List<Integer> infiniteStates = new ArrayList<>();

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
                infiniteStates.add(i);
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }else{
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }
        }

        for(int i=length1*length2-length2+1;i<length1*length2+3;i++){
            if((i-1)%length2==0){
                infiniteStates.add(i);
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-length2+1));
            }else if((i-1)%length2==1){
                if(i==length1*length2+2){
                    infiniteStates.add(i);
                    transferFunctions.add(new TransferFunction(i,'a',i));
                }else{
                    transferFunctions.add(new TransferFunction(i,'a',i+1));
                }
                transferFunctions.add(new TransferFunction(i,'b',i-length2-1));
            }else if((i-1)%length2==length2-1){
                infiniteStates.add(i);
                transferFunctions.add(new TransferFunction(i,'a',i));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }else{
                transferFunctions.add(new TransferFunction(i,'a',i+1));
                transferFunctions.add(new TransferFunction(i,'b',i-1));
            }
        }

        //define final states
        finalStates.add(2);

        //define dea
        return new DIA(length1*length2+3, alphabet, transferFunctions, 1, finalStates,infiniteStates);
    }

    public static DIA createDyckWithoutEmpty(int length){
        List<Character> alphabet = new ArrayList<>();
        List<TransferFunction> transferFunctions = new ArrayList<>();
        List<Integer> finalStates = new ArrayList<>();
        List<Integer> InfiniteStates = new ArrayList<>();

        //define alphabet
        alphabet.add('a');
        alphabet.add('b');

        //define transfer functions
        transferFunctions.add(new TransferFunction(0, 'a', 1));
        transferFunctions.add(new TransferFunction(0, 'b', 0));

        for (int i = 1; i < length - 1; i++) {
            transferFunctions.add(new TransferFunction(i, 'a', i + 1));
            transferFunctions.add(new TransferFunction(i, 'b', i - 1));
        }

        transferFunctions.add(new TransferFunction(length - 1, 'a', length - 1));
        transferFunctions.add(new TransferFunction(length - 1, 'b', length - 2));

        //define final states
        finalStates.add(0);

        InfiniteStates.add(length - 1);
        return new DIA(length, alphabet, transferFunctions, 0, finalStates, InfiniteStates);
    }

    //Prints the list "stringList" in lines with maximum Size "sizeOfLine"
    //It exists for testing
    public static void PrintList(List<String> StringList,int sizeOfLine){
        int count=0;
        for (String s : StringList) {
            System.out.print(s);
            if (count >= sizeOfLine) {
                count = 0;
                System.out.print("\n");
            } else {
                count++;
            }
        }
    }
}
