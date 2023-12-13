package Graphic;

import Language.DIA;
import Language.TransferFunction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DIAChoiceWindow {
    public static JFrame image=new JFrame("Choose DIA");
    private static int width=700,height=700,xoff=50,yoff=100, ycaption=50;
    private static JFormattedTextField[] textFields=new JFormattedTextField[7];
    private static Label[] labels=new Label[7];
    private static JButton btn[]=new JButton[4];
    private static DIA result;
    private static int states, beginningState;
    private static List<Integer> finalStates=new ArrayList<>(),infiniteStates=new ArrayList<>();
    private static List<Character> alphabet=new ArrayList<>();
    private static List<TransferFunction> transferFunctions=new ArrayList<>();
    private static TextArea functionList=new TextArea();
    private static Label[] basicInfo =new Label[3];

    private static final int HEIGHT_OF_LINES = 50;

    public static void setImage(){
        //define image
        image.setSize(width, height);
        image.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image.getContentPane().setBackground(Color.white);
        image.setLayout(null);
        image.setLocationRelativeTo(null);
        if(textFields[0]==null){
            textFields[0]=new JFormattedTextField(1);
            textFields[1]=new JFormattedTextField("a");
            textFields[2]=new JFormattedTextField(0);
            textFields[3]=new JFormattedTextField("0");
            textFields[4]=new JFormattedTextField("0");
            textFields[5]=new JFormattedTextField("0,a,0");
            textFields[6]=new JFormattedTextField("");
        }
        for(int i=0;i<textFields.length;i++){
            image.add(textFields[i]);
        }
        for(int i=0;i<labels.length;i++){
            labels[i]=new Label();
            image.add(labels[i]);
        }

        //set basic information
        for(int i=0;i<basicInfo.length;i++){
            basicInfo[i]=new Label();
            image.add(basicInfo[i]);
        }
        basicInfo[0].setBounds(xoff,ycaption,width-xoff*2,(yoff-ycaption)/basicInfo.length);
        basicInfo[1].setBounds(xoff,(yoff-ycaption)/basicInfo.length+ycaption,width-xoff*2,(yoff-ycaption)/basicInfo.length);
        basicInfo[2].setBounds(xoff,(yoff-ycaption)/basicInfo.length*2+ycaption,width-xoff*2,(yoff-ycaption)/basicInfo.length);
        basicInfo[0].setText("First use the define button to define the number of states, the alphabet, the start state, the final states\n");
        basicInfo[1].setText("and the infinite states. Then use the add and clear button to define the functions.\n");
        basicInfo[2].setText("The states are defined as [0,...,(number of states)-1]");

        //sets the number of states
        textFields[0].setBounds(width/4*3,yoff,width/4-xoff,HEIGHT_OF_LINES);
        labels[0].setBounds(xoff,yoff,width/4*3-xoff,HEIGHT_OF_LINES);
        labels[0].setText("define the number of states");

        //sets the alphabet
        textFields[1].setBounds(width/4*3,yoff+HEIGHT_OF_LINES,width/4-xoff,HEIGHT_OF_LINES);
        labels[1].setBounds(xoff,yoff+HEIGHT_OF_LINES,width/4*3-xoff,HEIGHT_OF_LINES);
        labels[1].setText("define the alphabet. just add the letters (duplicated letter will be ignored)");

        //sets the start state
        textFields[2].setBounds(width/4*3,yoff+HEIGHT_OF_LINES*2,width/4-xoff,HEIGHT_OF_LINES);
        labels[2].setBounds(xoff,yoff+HEIGHT_OF_LINES*2,width/4*3-xoff,HEIGHT_OF_LINES);
        labels[2].setText("define the start state (must be defined)");

        //sets the final states
        textFields[3].setBounds(width/4*3,yoff+HEIGHT_OF_LINES*3,width/4-xoff,HEIGHT_OF_LINES);
        labels[3].setBounds(xoff,yoff+HEIGHT_OF_LINES*3,width/4*3-xoff,HEIGHT_OF_LINES);
        labels[3].setText("define the final states (split the numbers with a \",\". f.e \"1,5,3\")");

        //sets the infinite states
        textFields[4].setBounds(width/4*3,yoff+HEIGHT_OF_LINES*4,width/4-xoff,HEIGHT_OF_LINES);
        labels[4].setBounds(xoff,yoff+HEIGHT_OF_LINES*4,width/4*3-xoff,HEIGHT_OF_LINES);
        labels[4].setText("define the infinite states (split the numbers with a \",\". f.e \"1,5,3\")");

        //sets the functions
        textFields[5].setBounds(width/4*3,yoff+HEIGHT_OF_LINES*5,width/4-xoff,HEIGHT_OF_LINES);
        labels[5].setBounds(xoff,yoff+HEIGHT_OF_LINES*5,width/4*3-xoff,HEIGHT_OF_LINES);
        labels[5].setText("adds function (split the numbers with a \",\". f.e \u03b4(1,a)=3 := \"1,a,3\")");

        //sets extra label
        labels[6].setBounds(xoff,yoff+HEIGHT_OF_LINES*6,width-xoff*2,HEIGHT_OF_LINES);


        //sets define button
        btn[0]=new JButton("define Parameters");
        btn[0].setBounds(xoff,yoff+HEIGHT_OF_LINES*7,(width-xoff*2)/btn.length,HEIGHT_OF_LINES);
        btn[0].addActionListener(e->{
            try {
                alphabet.clear();
                finalStates.clear();
                infiniteStates.clear();
                states=(int) textFields[0].getValue();
                beginningState=(int)textFields[2].getValue();
                for (int i=0;i<((String)textFields[1].getValue()).length();i++){
                    System.out.println((String)textFields[1].getValue());
                    if(!alphabet.contains(((String)textFields[1].getValue()).charAt(i))){
                        alphabet.add(((String)textFields[1].getValue()).charAt(i));
                    }
                }
                if(alphabet.isEmpty()){
                    throw new NullPointerException("alphabet");
                }
                String[] finalstates= ((String)textFields[3].getValue()).split(",");
                for(int i=0;i<finalstates.length;i++){
                    finalStates.add(Integer.valueOf(finalstates[i]));
                }
                String[] infinitestates= ((String)textFields[4].getValue()).split(",");
                for(int i=0;i<infinitestates.length;i++){
                    infiniteStates.add(Integer.valueOf(infinitestates[i]));
                }
                result=new DIA(states,alphabet,transferFunctions,beginningState,finalStates,infiniteStates);
                labels[6].setText("DIA is set");
            }catch (NumberFormatException k){
                labels[6].setText("invalid inputs");
            }catch (NullPointerException j){
                if(j.getMessage().equalsIgnoreCase("alphabet")){
                    labels[6].setText("invalid alphabet");
                }else{
                    labels[6].setText("invalid inputs");
                }
            }
        });
        image.add(btn[0]);

        //sets adding button
        btn[1]=new JButton("add function");
        btn[1].setBounds(xoff+(width-xoff*2)/btn.length,yoff+HEIGHT_OF_LINES*7,(width-xoff*2)/btn.length,HEIGHT_OF_LINES);
        btn[1].addActionListener(e->{
            try{
                String[] values=((String)textFields[5].getValue()).split(",");
                if(values.length!=3||values[1].length()!=1){
                    labels[6].setText("invalid input format (split the numbers with a \",\". f.e \u03b4(1,a)=3 := \"1,a,3\")");
                }else {
                    try{
                        if(Integer.valueOf(values[0])<0||Integer.valueOf(values[0])>=states||Integer.valueOf(values[2])<0||Integer.valueOf(values[2])>=states){
                            labels[6].setText("states not defined");
                        }else {
                            transferFunctions.add(new TransferFunction(Integer.valueOf(values[0]),values[1].charAt(0),Integer.valueOf(values[2])));
                            functionList.append("\u03b4("+values[0]+","+values[1]+")="+values[2]+"\n");
                        }
                    }catch (NumberFormatException k){
                        labels[6].setText("invalid input (split the numbers with a \",\". f.e \u03b4(1,a)=3 := \"1,a,3\")");
                    }
                }
            }catch (NullPointerException l){
                labels[6].setText("invalid input (maybe other values are not defined)");
            }
        });
        image.add(btn[1]);

        //sets function clear button
        btn[2]=new JButton("clear functions");
        btn[2].setBounds(xoff+((width-xoff*2)/btn.length)*2,yoff+HEIGHT_OF_LINES*7,(width-xoff*2)/btn.length,HEIGHT_OF_LINES);
        btn[2].addActionListener(e->{
            transferFunctions.clear();
            functionList.setText("");
        });
        image.add(btn[2]);

        //sets finish button
        btn[3]=new JButton("finish");
        btn[3].setBounds(xoff+((width-xoff*2)/btn.length)*3,yoff+HEIGHT_OF_LINES*7,(width-xoff*2)/btn.length,HEIGHT_OF_LINES);
        btn[3].addActionListener(e->{
            try{
                for (TransferFunction transferFunction:transferFunctions){
                    result.ChangeTransfer(transferFunction);
                }
                window.costumDIA=result;
                image.dispose();
            }catch (NullPointerException k){
                labels[6].setText("invalid input");
            }
        });
        image.add(btn[3]);

        //sets functionList
        JScrollPane scroll = (new JScrollPane(functionList));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(xoff,yoff+HEIGHT_OF_LINES*8,width-2*xoff,height-yoff*2-HEIGHT_OF_LINES*8);
        functionList.setEditable(false);
        image.getContentPane().add(scroll);

        image.setVisible(true);
    }
}
