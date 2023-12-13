package Graphic;

import Default.Main;
import Language.DIA;
import Monoid.EqualList;
import Monoid.Equals;
import Monoid.GreensRelation;

import java.awt.Color;
import java.util.List;
import javax.swing.*;

public class window {
    public static JFrame image=new JFrame("GreeneDIA");
    public static int width=1000,height=1000,xoff=50,yoff=100;
    private static JTextArea textArea=new JTextArea();
    private static JTextField extrainformation=new JTextField();
    private static final String[] dea_dia = new String[5];
    public static JButton[] btn=new JButton[dea_dia.length+3];
    private static DIA[] dias=new DIA[dea_dia.length];
    private static int answerChoosingAutomata,maxLength;
    private static EqualList equal;
    private static String[][] box;
    private static boolean isInterrupted=false;

    private static final int DEFAULT_MAX_LENGTH = 6;
    private static final int DEFAULT_LENGTH_1 = 15;
    private static final int DEFAULT_LENGTH_2 = 15;
    private static final int MAX_SLEEPING_DURATION=6000;

    public static DIA costumDIA=Main.createDyckWithoutEmpty(DEFAULT_LENGTH_1);

    public static void setWindow() {
        //set DIAs
        dea_dia[0] = "Dyck";
        dea_dia[1] = "Dyck R";
        dea_dia[2] = "H And";
        dea_dia[3] = "H Or";
        dea_dia[4] = "Custom";
        setDIAS(DEFAULT_LENGTH_1,DEFAULT_LENGTH_2);

        //define image
        image.setSize(width, height);
        image.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image.getContentPane().setBackground(new Color(0,255,0));
        image.setLayout(null);
        image.setLocationRelativeTo(null);

        //set max length input
        JFormattedTextField inputMaxLength;
        JLabel labelMaxLength = new JLabel("Maxlength:");
        inputMaxLength = new JFormattedTextField(DEFAULT_MAX_LENGTH);
        labelMaxLength.setBounds(width/2-200,yoff+100,100,50);
        inputMaxLength.setBounds(width/2-100,yoff+100,100,50);
        image.add(inputMaxLength);
        image.add(labelMaxLength);

        //set first length input
        JFormattedTextField inputLength1=new JFormattedTextField(DEFAULT_LENGTH_1);
        JLabel labelLength1 = new JLabel("First Length:");
        labelLength1.setBounds(width/2,yoff+100,100,25);
        inputLength1.setBounds(width/2+100,yoff+100,100,25);
        image.add(inputLength1);
        image.add(labelLength1);

        //set second length input
        JFormattedTextField inputLength2;
        JLabel labelLength2 = new JLabel("Second Length:");
        inputLength2 = new JFormattedTextField(DEFAULT_LENGTH_2);
        labelLength2.setBounds(width/2,yoff+125,100,25);
        inputLength2.setBounds(width/2+100,yoff+125,100,25);
        image.add(inputLength2);
        image.add(labelLength2);

        //set extra informations
        extrainformation.setBounds(xoff,yoff+150,width-2*xoff,50);
        extrainformation.setText("it is recommended to set \"First Length\"=\"Second Length\"=\"3*Maxlength\"");
        extrainformation.setEditable(false);
        image.add(extrainformation);

        //set DIA definition buttons
        for(int i=0;i<dea_dia.length;i++){
            btn[i]=new JButton(dea_dia[i]);
            btn[i].setBounds(xoff+i*(width-2*xoff)/dea_dia.length,yoff,(width-2*xoff)/dea_dia.length,50);
            setActionDIAButtons(i);
            image.add(btn[i]);
        }

        //sets scrollbar
        JScrollPane scroll = (new JScrollPane(textArea));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(xoff,yoff+200,width-2*xoff,height-2*yoff-200);
        textArea.setEditable(true);
        image.getContentPane().add(scroll);

        //sets equal-button
        btn[dea_dia.length]=new JButton("Equals");
        btn[dea_dia.length].setBounds(width/2-200,yoff+50,133,50);
        btn[dea_dia.length].addActionListener(e -> {
            timer(()->{
                setMaxLength((int)inputMaxLength.getValue());
                setDIAS((int)inputLength1.getValue(),(int)inputLength2.getValue());
                equal= Equals.findEqual(dias[answerChoosingAutomata],maxLength);
                if(!isInterrupted){
                    textArea.setText("");
                    for(int i=0;i<equal.Size();i++) {
                        textArea.append(equal.getEntry(i) + "\n");
                    }
                }else{
                    isInterrupted=false;
                }
            },MAX_SLEEPING_DURATION);
        });
        image.add(btn[dea_dia.length]);

        //sets box-button
        btn[dea_dia.length+1]=new JButton("Box");
        btn[dea_dia.length+1].setBounds(width/2-67,yoff+50,134,50);
        btn[dea_dia.length+1].addActionListener(e -> {
            timer(()->{
                setMaxLength((int)inputMaxLength.getValue());
                setDIAS((int)inputLength1.getValue(),(int)inputLength2.getValue());
                box= GreensRelation.getGreenBox(dias[answerChoosingAutomata],maxLength);
                if(!isInterrupted){
                    textArea.setText("");
                    for(int i=0;i<box.length;i++) {
                        for (int j=0;j<box[i].length;j++){
                            textArea.append(box[i][j] + " ");
                        }
                        textArea.append("\n");
                    }
                }else{
                    isInterrupted=false;
                }
            },MAX_SLEEPING_DURATION);
        });
        image.add(btn[dea_dia.length+1]);

        //sets H-CLass-button
        btn[dea_dia.length+2]=new JButton("H Class");
        btn[dea_dia.length+2].setBounds(width/2+67,yoff+50,133,50);
        btn[dea_dia.length+2].addActionListener(event -> {
            timer(()->{
                setMaxLength((int)inputMaxLength.getValue());
                setDIAS((int)inputLength1.getValue(),(int)inputLength2.getValue());
                List<List<String>> HClasses=GreensRelation.getHValues(dias[answerChoosingAutomata],maxLength);
                if(!isInterrupted){
                    if(!HClasses.isEmpty()){
                        textArea.setText("");
                        for(List<String> HCLass:HClasses) {
                            if(HCLass.size()<=1){
                                continue;
                            }
                            for (String element:HCLass){
                                textArea.append(element+"<->");
                            }
                            textArea.append("\n");
                        }
                    }else{
                        textArea.setText("Nothing");
                    }
                }else{
                    isInterrupted=false;
                }
            },MAX_SLEEPING_DURATION);
        });
        image.add(btn[dea_dia.length+2]);

        //sets background image
        Draw draw = new Draw();
        draw.setBounds(0,0,width,height);
        draw.setVisible(true);
        image.add(draw);

        image.setVisible(true);
    }


    //Sets the action of a button
    //"choice" is needed to define the dia what should be connected with the button
    private static void setActionDIAButtons(int choice){
        btn[choice].addActionListener(e -> {
            if(choice==dias.length-1){
                DIAChoiceWindow.setImage();
            }
            answerChoosingAutomata=choice;
        });
    }

    //Defines the DIAs with proper parameters
    private static void setDIAS(int length1, int length2){
        dias[0]= Main.createDyck(length1);
        dias[1]= Main.createDyckPlus(length1);
        dias[2]= Main.createHAnd(length1,length2);
        dias[3]= Main.createHOr(length1,length2);
        dias[4]= costumDIA;
        System.out.println(costumDIA.getAlphabet());
    }

    //Sets the maximum Length
    private static void setMaxLength(int value){
        if(value<3){
            maxLength=3;
        }else{
            maxLength=value;
        }
    }

    //Starts the action "runnable" for a duration of "timeInMilliSecond"
    //To fully interrupt an action, the action must use the "isInterrupt"-Variable
    private static void timer(Runnable runnable, int timeInMilliSeconds){
        Thread exeThread=new Thread(runnable);
        Thread waitThread=new Thread(()->{
            try {
                Thread.sleep(timeInMilliSeconds);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
        waitThread.start();
        exeThread.start();
        while (exeThread.isAlive()&&waitThread.isAlive()){}
        if(waitThread.isAlive()){
            waitThread.interrupt();
        }else {
            isInterrupted=true;
            textArea.setText("action interrupt because it takes too long");
            exeThread.interrupt();
        }
    }
}
