package Monoid;

import Language.DEA;
import Language.DIA;

import java.util.List;
import java.util.ArrayList;

public class Tabelle {
    private DEA dea;
    private DIA dia;
    private List<StatePer> StatePers;
    private List<StatePer> left;
    private String[][] Resultmatrix;
    private StatePer BasicStatePer;

    public Tabelle(DEA dea){
        this.dea=dea;
        StatePers = new ArrayList<StatePer>();
        left = new ArrayList<StatePer>();
        BasicStatePer = new StatePer(dea.getStates(),null, dea);
        addStatePer(BasicStatePer);
        for (int i=0;i<dea.getAlphabet().size();i++){
            addStatePer(new StatePer(dea.getStates(),dea.getAlphabet().get(i).toString(), dea));
        }
        StatePer temp;
        while(!left.isEmpty()){
            StatePer statePer=left.remove(0);
            for(int i=0;i<StatePers.size();i++){
                temp=new StatePer(dea.getStates(),statePer.getLabel()+StatePers.get(i).getLabel(), dea);
                if(!IsStatePerInList(temp)){
                    addStatePer(temp);
                }
            }
        }
        createResultMatrix();
    }

    /*public Tabelle(DEA dea, int equaltime){
        this.dea=dea;
        StatePers = new ArrayList<StatePer>();
        left = new ArrayList<StatePer>();
        BasicStatePer = new StatePer(dea.getStates(),null, dea);
        addStatePer(BasicStatePer);
        for (int i=0;i<dea.getAlphabet().size();i++){
            addStatePer(new StatePer(dea.getStates(),dea.getAlphabet().get(i).toString(), dea));
        }
        StatePer temp;
        while(!left.isEmpty()){
            StatePer statePer=left.remove(0);
            for(int i=0;i<StatePers.size();i++){
                temp=new StatePer(dea.getStates(),statePer.getLabel()+StatePers.get(i).getLabel(), dea);
                if(!IsStatePerInList(temp)){
                    addStatePer(temp);
                }
            }
        }
        createResultMatrix(equaltime);
    }*/

    public Tabelle(DIA dia){
        this.dia=dia;
        this.dea=dia;
    }

    private boolean IsStatePerInList(StatePer statePer){
        for(int i=0;i<StatePers.size();i++){
            if(statePer.CompareStatePers(StatePers.get(i))){
                return true;
            }
        }
        return false;
    }

    private StatePer returnBestStatePer(StatePer statePer){
        for(int i=0;i<StatePers.size();i++){
            if(statePer.CompareStatePers(StatePers.get(i))){
                return StatePers.get(i);
            }
        }
        return null;
    }

    public DIA getDia() {
        return dia;
    }

    private StatePer returnBestStatePer(StatePer statePer, int limit){
        if(limit>StatePers.size()){
            throw new IndexOutOfBoundsException("limit ist bigger than the amounth of StatePers");
        }
        for(int i=0;i<StatePers.size();i++){
            if(statePer.CompareStatePers(StatePers.get(i),limit)){
                return StatePers.get(i);
            }
        }
        return null;
    }

    private void addStatePer(StatePer state){
        StatePers.add(state);
        left.add(state);
    }

    private void createResultMatrix(){
        this.Resultmatrix = new String[StatePers.size()][StatePers.size()];
        for(int i=0;i<StatePers.size();i++){
            String label=StatePers.get(i).getLabel();
            for(int j=0;j<StatePers.size();j++){
                try{
                    Resultmatrix[i][j]=returnBestStatePer(new StatePer(dea.getStates(),label+StatePers.get(j).getLabel(),dea)).getLabel();
                }catch(NullPointerException e) {
                    Resultmatrix[i][j] = new StatePer(dea.getStates(), label + StatePers.get(j).getLabel(), dea).getLabel();
                }
            }
        }
    }

    /*private void createResultMatrix(Equal equal,int equalTime){
        this.Resultmatrix = new String[StatePers.size()][StatePers.size()];
        for(int i=0;i<StatePers.size();i++){
            String label=StatePers.get(i).getLabel();
            for(int j=0;j<StatePers.size();j++){
                try{
                    Resultmatrix[i][j]=Equals.removeEqual(returnBestStatePer(new StatePer(dea.getStates(),label+StatePers.get(j).getLabel(),dea)).getLabel(),equal);
                }catch(NullPointerException e){
                    Resultmatrix[i][j]=Equals.removeEqual(new StatePer(dea.getStates(),label+StatePers.get(j).getLabel(),dea).getLabel(),equal);
                }
                for(int k=1;k<equalTime;k++){
                    Resultmatrix[i][j]=Equals.removeEqual(Resultmatrix[i][j],equal);
                }
            }
        }
    }*/

    public void displayResultMatrix(){
        for(int i=0;i<Resultmatrix.length;i++){
            for(int j=0;j<Resultmatrix[i].length;j++){
                System.out.print("["+Resultmatrix[i][j]+"]");
            }
            System.out.print("\n");
        }
    }

    public List<StatePer> getStatePers() {
        return StatePers;
    }

    public String[][] getResultmatrix() {
        return Resultmatrix;
    }

    public DEA getDea() {
        return dea;
    }
}
