package Monoid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreensRelation {
    public static List<String> getRelations(Tabelle tabelle){
        List<String> result = new ArrayList<String>();
        String a,b;
        boolean isR,isL;
        String[][] resultmatrix = tabelle.getResultmatrix();
        List<StatePer> statePers = tabelle.getStatePers();
        for(int i=0;i<statePers.size();i++){
            a=statePers.get(i).getLabel();
            for(int j=0;j<i;j++){
                b=statePers.get(j).getLabel();
                if(a!=b){
                    isR=isR_Related(resultmatrix,a,b,i,j);
                    isL=isL_Related(resultmatrix,a,b,i,j);
                    if(isL&&!isR){
                        result.add("["+a+"L"+b+"]");
                    }else if(!isL&&isR){
                        result.add("["+a+"R"+b+"]");
                    }else if(isL&&isR){
                        result.add("["+a+"D"+b+"]");
                    }
                }
            }
        }
        return result;
    }

    public static List<List<String>> getLRelationLists(Tabelle tabelle){
        List<List<String>> result = new ArrayList<>();
        List<String> checked = new ArrayList<>();
        String a,b;
        String[][] resultmatrix = tabelle.getResultmatrix();
        List<StatePer> statePers = tabelle.getStatePers();
        for(int i=0;i<statePers.size();i++){
            a=statePers.get(i).getLabel();
            for(int j=0;j<statePers.size();j++){
                b=statePers.get(j).getLabel();
                if(!a.equals(b)&&isL_Related(resultmatrix,a,b,i,j)){
                    for(int k=0;k<result.size();k++) {
                        if(!checked.contains(a)&&result.get(k).contains(b)){
                            result.get(k).add(a);
                            checked.add(a);
                        }
                    }
                }
            }
            if(!checked.contains(a)){
                result.add(new ArrayList<>(Arrays.asList(a)));
                checked.add(a);
            }
        }
        return result;
    }

    private static boolean isR_Related(String[][] resultmatrix,String a, String b, int position_a, int position_b){
        boolean isAtoB=false;
        boolean isBtoA=false;
        for (int i=0;i<resultmatrix[position_a].length;i++){
            if(resultmatrix[position_a][i].equalsIgnoreCase(b)){
                isAtoB=true;
                break;
            }
        }
        for (int i=0;i<resultmatrix[position_a].length;i++){
            if(resultmatrix[position_b][i].equalsIgnoreCase(a)){
                isBtoA=true;
                break;
            }
        }
        if(isAtoB && isBtoA){
            return true;
        }
        return false;
    }

    private static boolean isL_Related(String[][] resultmatrix,String a, String b, int position_a, int position_b){
        boolean isAtoB=false;
        boolean isBtoA=false;
        for (int i=0;i<resultmatrix.length;i++){
            if(resultmatrix[i][position_a].equalsIgnoreCase(b)){
                isAtoB=true;
                break;
            }
        }
        for (int i=0;i<resultmatrix.length;i++){
            if(resultmatrix[i][position_b].equalsIgnoreCase(a)){
                isBtoA=true;
                break;
            }
        }
        if(isAtoB && isBtoA){
            return true;
        }
        return false;
    }

    public static boolean isRelationInList(List<String> list, String Relation){
        for(int i=0;i<list.size();i++){
            if(Relation.equalsIgnoreCase(list.get(i))){
                return true;
            }
        }
        return false;
    }

    public static void DisplayList(Tabelle tabelle){
        List<List<String>> result = getLRelationLists(tabelle);
        for(int i=0;i<result.size();i++){
            for(int j=0;j<result.get(i).size();j++){
                System.out.print(result.get(i).get(j)+",");
            }
            System.out.print("\n");
        }
    }
}
