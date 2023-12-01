package Monoid;

import Language.TransferFunction;

import java.util.ArrayList;
import java.util.List;

// creates Transition Homomorphism
// --amounthOfElements: the number of states
// --Image: the mapping value for each state
// --Kern: the kern values for each image value
// --infiniteCases: the states that got cut off throw finitisation
public class Transition {
    int amounthOfElements;
    int[] Image;
    List<Integer>[] Kern;
    List<Integer> infiniteCases;

    public Transition(int amounthOfElements, List<TransferFunction> transitionFunctions){
        Transform(amounthOfElements, transitionFunctions);
        this.infiniteCases=new ArrayList<>();
    }

    public Transition(int amounthOfElements, List<TransferFunction> transitionFunctions,List<Integer> infiniteCases){
        Transform(amounthOfElements, transitionFunctions);
        this.infiniteCases=infiniteCases;
    }

    //This functions initialises the image and kern value throw inputting transfer functions
    private void Transform(int amounthOfElements, List<TransferFunction> transitionFunctions){
        this.amounthOfElements=amounthOfElements;
        this.infiniteCases=new ArrayList<>();
        List<Integer> contains = new ArrayList<>();
        Image = new int[amounthOfElements];
        Kern = new List[amounthOfElements];
        for(int i=0;i< Kern.length;i++){
            Kern[i] = new ArrayList<Integer>();
        }
        for(int i=0;i< transitionFunctions.size();i++){
            if(!((amounthOfElements>(transitionFunctions.get(i).getStartStateID())||transitionFunctions.get(i).getStartStateID()<-1)
                    &&(amounthOfElements>(transitionFunctions.get(i).getEndStateID())||transitionFunctions.get(i).getEndStateID()<-1))){
                throw new IndexOutOfBoundsException("Id is not in Elements");
            }
            if(contains.contains(transitionFunctions.get(i).getStartStateID())){
                throw new NullPointerException("Only one Function with same StartID");
            }
            contains.add(transitionFunctions.get(i).getStartStateID());
            Image[i]= transitionFunctions.get(i).getEndStateID();
            try{
                Kern[transitionFunctions.get(i).getEndStateID()].add(transitionFunctions.get(i).getStartStateID());
            }catch (IndexOutOfBoundsException e){

            }
        }
    }

    public Transition(int amounthOfElements, int[] Image, List<Integer>[] Kern, List<Integer> infiniteCases){
        this.Image=Image;
        this.amounthOfElements=amounthOfElements;
        this.Kern=Kern;
        this.infiniteCases=infiniteCases;
    }

    public Transition(int amounthOfElements, int[] Image, List<Integer>[] Kern){
        this.Image=Image;
        this.amounthOfElements=amounthOfElements;
        this.Kern=Kern;
        this.infiniteCases=new ArrayList<>();
    }

    //ID-Transition
    public Transition(int amounthOfElements){
        this.amounthOfElements=amounthOfElements;
        Image = new int[amounthOfElements];
        Kern = new List[amounthOfElements];
        for(int i=0;i< amounthOfElements;i++){
            Image[i]=i;
            List<Integer> idList=new ArrayList<>();
            idList.add(amounthOfElements);
            Kern[i]=idList;
        }
        this.infiniteCases=new ArrayList<>();
    }

    //Returns the image array
    public int[] getImage() {
        return Image;
    }

    //Returns the kern array
    public List<Integer>[] getKern() {
        return Kern;
    }

    //Returns the set of reachable image values
    public List<Integer> getSetOfImage(){
        List<Integer> result = new ArrayList<>();
        for(int i=0;i<this.Image.length;i++){
            if(!result.contains(i)){
                result.add(i);
            }
        }
        return result;
    }

    //Returns the Rang of the Homomorphism
    public int getRang(){
        int kernSize=0;
        for(int i=0;i<this.getKern().length;i++){
            if(!this.Kern[i].isEmpty()){
                kernSize++;
            }
        }
        return Math.min(getSetOfImage().size(),kernSize);
    }

    //Sets the image value at state ID "index"
    public void setImage(int value, int index) {
        Image[index] = value;
    }

    //Copies the Transition
    public Transition copy(){
        return new Transition(amounthOfElements,Image,Kern, infiniteCases);
    }

    //Returns the infinite states
    public List<Integer> getInfiniteCases() {
        return infiniteCases;
    }

    //Sets the infinite states
    public void setInfiniteCases(List<Integer> infiniteCases) {
        this.infiniteCases = infiniteCases;
    }

    //Displays all mappings
    public void display(){
        System.out.print("(");
        for (int i=0;i<this.Kern.length-1;i++){
            System.out.print(i+"->"+Image[i]);
            System.out.print("|");
        }
        System.out.print(Kern.length-1+"->"+Image[Kern.length-1]+")");
    }
}
