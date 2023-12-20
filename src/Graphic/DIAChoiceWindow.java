package Graphic;

import Language.DIA;
import Language.TransferFunction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DIAChoiceWindow {
    public static JFrame image=new JFrame("Choose DIA");
    private static final int WIDTH=700,HEIGHT=800,XOFF=50,YOFF=100, YCAPTION=50, HEIGHT_OF_LINES = 50;
    private static final JFormattedTextField[] textFields=new JFormattedTextField[7];
    private static final Label[] labels=new Label[7];
    private static final JButton[] btn =new JButton[4];
    private static DIA result;
    private static int states, beginningState;
    private static final List<Integer> finalStates=new ArrayList<>(), infiniteStates=new ArrayList<>();
    private static final List<Character> alphabet=new ArrayList<>();
    private static final List<TransferFunction> transferFunctions=new ArrayList<>();
    private static final TextArea functionList=new TextArea();
    private static final Label[] basicInfo =new Label[4];

    public static void setImage(){
        //define image
        image.setSize(WIDTH, HEIGHT);
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
        for (JFormattedTextField textField:textFields) {
            image.add(textField);
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
        basicInfo[0].setBounds(XOFF,YCAPTION,WIDTH-XOFF*2,(YOFF-YCAPTION)/basicInfo.length);
        basicInfo[1].setBounds(XOFF,(YOFF-YCAPTION)/basicInfo.length+YCAPTION,WIDTH-XOFF*2,(YOFF-YCAPTION)/basicInfo.length);
        basicInfo[2].setBounds(XOFF,(YOFF-YCAPTION)/basicInfo.length*2+YCAPTION,WIDTH-XOFF*2,(YOFF-YCAPTION)/basicInfo.length);
        basicInfo[3].setBounds(XOFF,(YOFF-YCAPTION)/basicInfo.length*3+YCAPTION,WIDTH-XOFF*2,(YOFF-YCAPTION)/basicInfo.length);
        basicInfo[0].setText("First use the define button to define the number of states, the alphabet, the start state, the final states");
        basicInfo[1].setText("and the infinite states. Then use the add and clear button to define the functions.");
        basicInfo[2].setText("The states are defined as [0,...,(number of states)-1].");
        basicInfo[3].setText("Undefined functions are self loops.");

        //sets the number of states
        textFields[0].setBounds(WIDTH/4*3,YOFF,WIDTH/4-XOFF,HEIGHT_OF_LINES);
        labels[0].setBounds(XOFF,YOFF,WIDTH/4*3-XOFF,HEIGHT_OF_LINES);
        labels[0].setText("define the number of states");

        //sets the alphabet
        textFields[1].setBounds(WIDTH/4*3,YOFF+HEIGHT_OF_LINES,WIDTH/4-XOFF,HEIGHT_OF_LINES);
        labels[1].setBounds(XOFF,YOFF+HEIGHT_OF_LINES,WIDTH/4*3-XOFF,HEIGHT_OF_LINES);
        labels[1].setText("define the alphabet. just add the letters (duplicated letter will be ignored)");

        //sets the start state
        textFields[2].setBounds(WIDTH/4*3,YOFF+HEIGHT_OF_LINES*2,WIDTH/4-XOFF,HEIGHT_OF_LINES);
        labels[2].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*2,WIDTH/4*3-XOFF,HEIGHT_OF_LINES);
        labels[2].setText("define the start state (must be defined)");

        //sets the final states
        textFields[3].setBounds(WIDTH/4*3,YOFF+HEIGHT_OF_LINES*3,WIDTH/4-XOFF,HEIGHT_OF_LINES);
        labels[3].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*3,WIDTH/4*3-XOFF,HEIGHT_OF_LINES);
        labels[3].setText("define the final states (split the numbers with a \",\". f.e \"1,5,3\")");

        //sets the infinite states
        textFields[4].setBounds(WIDTH/4*3,YOFF+HEIGHT_OF_LINES*4,WIDTH/4-XOFF,HEIGHT_OF_LINES);
        labels[4].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*4,WIDTH/4*3-XOFF,HEIGHT_OF_LINES);
        labels[4].setText("define the infinite states (split the numbers with a \",\". f.e \"1,5,3\")");

        //sets the functions
        textFields[5].setBounds(WIDTH/4*3,YOFF+HEIGHT_OF_LINES*5,WIDTH/4-XOFF,HEIGHT_OF_LINES);
        labels[5].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*5,WIDTH/4*3-XOFF,HEIGHT_OF_LINES);
        labels[5].setText("adds function (split the numbers with a \",\". f.e \u03b4(1,a)=3 := \"1,a,3\")");

        //sets extra label
        labels[6].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*6,WIDTH-XOFF*2,HEIGHT_OF_LINES);
        labels[6].setAlignment(JLabel.VERTICAL);

        //sets define button
        btn[0]=new JButton("define Parameters");
        btn[0].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*7,(WIDTH-XOFF*2)/btn.length,HEIGHT_OF_LINES);
        btn[0].addActionListener(e->{
            try {
                alphabet.clear();
                finalStates.clear();
                infiniteStates.clear();
                states=(int) textFields[0].getValue();
                beginningState=(int)textFields[2].getValue();
                for (int i=0;i<((String)textFields[1].getValue()).length();i++){
                    if(!alphabet.contains(((String)textFields[1].getValue()).charAt(i))){
                        alphabet.add(((String)textFields[1].getValue()).charAt(i));
                    }
                }
                if(alphabet.isEmpty()){
                    throw new NullPointerException("alphabet");
                }
                String[] final_states= ((String)textFields[3].getValue()).split(",");
                for (String final_state:final_states) {
                    finalStates.add(Integer.valueOf(final_state));
                }
                String[] infinite_states= ((String)textFields[4].getValue()).split(",");
                for (String infinite_state:infinite_states) {
                    infiniteStates.add(Integer.valueOf(infinite_state));
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
        btn[1].setBounds(XOFF+(WIDTH-XOFF*2)/btn.length,YOFF+HEIGHT_OF_LINES*7,(WIDTH-XOFF*2)/btn.length,HEIGHT_OF_LINES);
        btn[1].addActionListener(e->{
            try{
                String[] values=((String)textFields[5].getValue()).split(",");
                if(values.length!=3||values[1].length()!=1){
                    labels[6].setText("invalid input format (split the numbers with a \",\". f.e \u03b4(1,a)=3 := \"1,a,3\")");
                }else {
                    try{
                        if(Integer.parseInt(values[0])<0||Integer.parseInt(values[0])>=states||Integer.parseInt(values[2])<0||Integer.parseInt(values[2])>=states){
                            labels[6].setText("states not defined");
                        }else if(!alphabet.contains(values[1].charAt(0))) {
                            labels[6].setText("the letter is not in the alphabet");
                        }else{
                            transferFunctions.add(new TransferFunction(Integer.parseInt(values[0]),values[1].charAt(0),Integer.parseInt(values[2])));
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
        btn[2].setBounds(XOFF+((WIDTH-XOFF*2)/btn.length)*2,YOFF+HEIGHT_OF_LINES*7,(WIDTH-XOFF*2)/btn.length,HEIGHT_OF_LINES);
        btn[2].addActionListener(e->{
            transferFunctions.clear();
            functionList.setText("");
        });
        image.add(btn[2]);

        //sets finish button
        btn[3]=new JButton("finish");
        btn[3].setBounds(XOFF+((WIDTH-XOFF*2)/btn.length)*3,YOFF+HEIGHT_OF_LINES*7,(WIDTH-XOFF*2)/btn.length,HEIGHT_OF_LINES);
        btn[3].addActionListener(e->{
            try{
                for (TransferFunction transferFunction:transferFunctions){
                    result.ChangeTransfer(transferFunction);
                }
                window.costumDIA=result;
                image.dispose();
            }catch (NullPointerException k){
                labels[6].setText("invalid input (function cannot be put into the DIA)");
            }
        });
        image.add(btn[3]);

        //sets functionList
        JScrollPane scroll = (new JScrollPane(functionList));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(XOFF,YOFF+HEIGHT_OF_LINES*8,WIDTH-2*XOFF,HEIGHT_OF_LINES*4);
        functionList.setEditable(false);
        image.getContentPane().add(scroll);

        image.setVisible(true);
    }
}
