package Graphic;

import Default.Main;
import Language.DIA;
import Monoid.EqualList;
import Monoid.Equals;
import Monoid.GreensRelation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class window {
    public static JFrame image=new JFrame("GreeneDIA");
    public final static int WIDTH=1000,HEIGHT=1000,XOFF=50,YOFF=100;
    private static final JTextArea textArea=new JTextArea();
    private static final JTextField[] extrainformation=new JTextField[3];
    private static final String[] dea_dia = new String[5];
    public static JButton[] btn=new JButton[dea_dia.length+3];
    private static final DIA[] dias=new DIA[dea_dia.length];
    private static int answerChoosingAutomata,inputLength;
    private static EqualList equal;
    private static List<String[][]> boxes=new ArrayList<>();
    private static boolean isInterrupted=false;
    private static final JCheckBox expandSearch= new JCheckBox("expand search");
    private static final JSlider slider = new JSlider(SwingConstants.HORIZONTAL);

    private static final int DEFAULT_MAX_LENGTH = 5;
    private static final int DEFAULT_LENGTH_1 = 15;
    private static final int DEFAULT_LENGTH_2 = 15;
    private static final int DEFAULT_MAX_SLEEPING_DURATION=6;
    private static final int HEIGHT_OF_LINES = 50;
    private static final int MINIMUM_INPUT_LENGTH_VALUE=3, MINIMUM_VERTICAL_LENGTH=8, MINIMUM_HORIZONTAL_LENGTH=8;

    public static DIA costumDIA=Main.createDyckWithoutEmpty(DEFAULT_LENGTH_1);

    public static void setWindow() {
        //set DIAs
        dea_dia[0] = "Dyck";
        dea_dia[1] = "Dyck R";
        dea_dia[2] = "H +";
        dea_dia[3] = "H -";
        dea_dia[4] = "Custom";
        setDIAS(DEFAULT_LENGTH_1,DEFAULT_LENGTH_2);

        //define image
        image.setSize(WIDTH, HEIGHT);
        image.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image.getContentPane().setBackground(new Color(0,255,0));
        image.setLayout(null);
        image.setLocationRelativeTo(null);

        //set max length input
        JFormattedTextField inputMaxLength;
        JLabel labelMaxLength = new JLabel("Input Length:");
        inputMaxLength = new JFormattedTextField(DEFAULT_MAX_LENGTH);
        labelMaxLength.setBounds(XOFF,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/6,HEIGHT_OF_LINES);
        inputMaxLength.setBounds(XOFF+(WIDTH-2*XOFF)/8,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/8,HEIGHT_OF_LINES);
        image.add(inputMaxLength);
        image.add(labelMaxLength);

        //set first length input
        JFormattedTextField inputLength1=new JFormattedTextField(DEFAULT_LENGTH_1);
        JLabel labelLength1 = new JLabel("Horizontal Length:");
        labelLength1.setBounds(XOFF+(WIDTH-2*XOFF)/8*2,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/8,HEIGHT_OF_LINES);
        inputLength1.setBounds(XOFF+(WIDTH-2*XOFF)/8*3,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/8,HEIGHT_OF_LINES);
        image.add(inputLength1);
        image.add(labelLength1);

        //set second length input
        JFormattedTextField inputLength2;
        JLabel labelLength2 = new JLabel("Vertical Length:");
        inputLength2 = new JFormattedTextField(DEFAULT_LENGTH_2);
        labelLength2.setBounds(XOFF+(WIDTH-2*XOFF)/8*4,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/8,HEIGHT_OF_LINES);
        inputLength2.setBounds(XOFF+(WIDTH-2*XOFF)/8*5,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/8,HEIGHT_OF_LINES);
        image.add(inputLength2);
        image.add(labelLength2);

        //set duration timer
        JFormattedTextField duration;
        JLabel labelDuration = new JLabel("Duration of Test:");
        duration = new JFormattedTextField(DEFAULT_MAX_SLEEPING_DURATION);
        labelDuration.setBounds(XOFF+(WIDTH-2*XOFF)/8*6,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/8,HEIGHT_OF_LINES/2);
        duration.setBounds(XOFF+(WIDTH-2*XOFF)/8*7,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/8,HEIGHT_OF_LINES/2);
        image.add(duration);
        image.add(labelDuration);

        //set expand search
        expandSearch.setBounds(XOFF+(WIDTH-2*XOFF)/8*6,YOFF+HEIGHT_OF_LINES*2+HEIGHT_OF_LINES/2,(WIDTH-2*XOFF)/8*2,HEIGHT_OF_LINES/2);
        expandSearch.setSelected(true);
        image.add(expandSearch);

        //set extra information
        for (int i=0;i<extrainformation.length;i++){
            extrainformation[i]=new JTextField();
            extrainformation[i].setEditable(false);
            image.add(extrainformation[i]);
        }
        for(int i=0;i<extrainformation.length;i++){
            extrainformation[i].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*3+HEIGHT_OF_LINES/2*i,WIDTH-2*XOFF,HEIGHT_OF_LINES/2);
        }
        extrainformation[0].setText("It is recommended to set \"Horizontal Length\"=\"Vertical Length\"=\"3*Input Length\". Input Length can be minimum "+MINIMUM_INPUT_LENGTH_VALUE+".");
        extrainformation[1].setText("Horizontal Length can be minimum "+MINIMUM_HORIZONTAL_LENGTH+"."+" Vertical Length can be minimum "+MINIMUM_VERTICAL_LENGTH+".");
        extrainformation[2].setText("Expand search does search for more equals. The calculations are more accurate but require more calculation time");

        //set DIA definition buttons
        for(int i=0;i<dea_dia.length;i++){
            btn[i]=new JButton(dea_dia[i]);
            btn[i].setBounds(XOFF+i*(WIDTH-2*XOFF)/dea_dia.length,YOFF,(WIDTH-2*XOFF)/dea_dia.length,HEIGHT_OF_LINES);
            setActionDIAButtons(i);
            image.add(btn[i]);
        }

        //sets D-Class choice
        slider.setBounds(XOFF+(WIDTH-2*XOFF)/8,YOFF+HEIGHT_OF_LINES*4+HEIGHT_OF_LINES/2,(WIDTH-2*XOFF)/8*7,HEIGHT_OF_LINES/2);
        image.add(slider);
        JLabel labelDClass =new JLabel("Choose D Class:");
        labelDClass.setBounds(XOFF,YOFF+HEIGHT_OF_LINES*4+HEIGHT_OF_LINES/2,(WIDTH-2*XOFF)/8,HEIGHT_OF_LINES/2);
        image.add(labelDClass);
        slider.setVisible(false);
        slider.setMinimum(1);
        slider.addChangeListener(e ->{
            textArea.setText("");
            for (String[] strings: boxes.get(slider.getValue()-1)) {
                for (String string:strings) {
                    textArea.append(string + " ");
                }
                textArea.append("\n");
            }
        });

        //sets scrollbar
        JScrollPane scroll = (new JScrollPane(textArea));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(XOFF,YOFF+HEIGHT_OF_LINES*5,WIDTH-2*XOFF,HEIGHT-2*YOFF-200);
        textArea.setEditable(true);
        image.getContentPane().add(scroll);

        //sets equal-button
        btn[dea_dia.length]=new JButton("Equals");
        btn[dea_dia.length].setBounds(XOFF,YOFF+HEIGHT_OF_LINES,(WIDTH-2*XOFF)/3,HEIGHT_OF_LINES);
        btn[dea_dia.length].addActionListener(e -> timer(()->{
            setInputLength((int)inputMaxLength.getValue());
            setDIAS((int)inputLength1.getValue(),(int)inputLength2.getValue());
            equal= Equals.findEquals(dias[answerChoosingAutomata],inputLength, expandSearch.isSelected());
            if(!isInterrupted){
                textArea.setText("");
                for(int i=0;i<equal.Size();i++) {
                    textArea.append(equal.getEntry(i) + "\n");
                }
            }else{
                isInterrupted=false;
            }
        },(int)duration.getValue()*1000));
        image.add(btn[dea_dia.length]);

        //sets box-button
        btn[dea_dia.length+1]=new JButton("Green's Box");
        btn[dea_dia.length+1].setBounds(XOFF+(WIDTH-2*XOFF)/3,YOFF+HEIGHT_OF_LINES,(WIDTH-2*XOFF)/3,HEIGHT_OF_LINES);
        btn[dea_dia.length+1].addActionListener(e -> timer(()->{
            setInputLength((int)inputMaxLength.getValue());
            setDIAS((int)inputLength1.getValue(),(int)inputLength2.getValue());
            boxes= GreensRelation.getGreenBox(dias[answerChoosingAutomata], inputLength, expandSearch.isSelected());
            if(!isInterrupted){
                slider.setMaximum(boxes.size());
                slider.setVisible(boxes.size()>1);
                textArea.setText("");
                for (String[] strings: boxes.get(0)) {
                    for (String string:strings) {
                        textArea.append(string + " ");
                    }
                    textArea.append("\n");
                }
            }else{
                isInterrupted=false;
            }
        },(int)duration.getValue()*1000));
        image.add(btn[dea_dia.length+1]);

        //sets H-CLass-button
        btn[dea_dia.length+2]=new JButton("H Class");
        btn[dea_dia.length+2].setBounds(XOFF+(WIDTH-2*XOFF)/3*2,YOFF+HEIGHT_OF_LINES,(WIDTH-2*XOFF)/3,HEIGHT_OF_LINES);
        btn[dea_dia.length+2].addActionListener(event -> timer(()->{
            setInputLength((int)inputMaxLength.getValue());
            setDIAS((int)inputLength1.getValue(),(int)inputLength2.getValue());
            List<List<String>> HClasses=GreensRelation.getHValues(dias[answerChoosingAutomata],inputLength, expandSearch.isSelected());
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
        },(int)duration.getValue()*1000));
        image.add(btn[dea_dia.length+2]);

        //sets background image
        Draw draw = new Draw();
        draw.setBounds(0,0,WIDTH,HEIGHT);
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
        dias[0]= Main.createDyck(setHorizontalLength(length1));
        dias[1]= Main.createDyckR(setHorizontalLength(length1));
        dias[2]= Main.createHAnd(setHorizontalLength(length1),setVerticalLength(length2));
        dias[3]= Main.createHOr(setHorizontalLength(length1),setVerticalLength(length2));
        dias[4]= costumDIA;
    }

    //Sets the input Length
    private static void setInputLength(int value){
        inputLength = Math.max(value, MINIMUM_INPUT_LENGTH_VALUE);
    }

    //Sets the vertical Length
    private static int setVerticalLength(int value){
        return Math.max(value, MINIMUM_VERTICAL_LENGTH);
    }

    //Sets the horizontal Length
    private static int setHorizontalLength(int value){
        return Math.max(value, MINIMUM_HORIZONTAL_LENGTH);
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
