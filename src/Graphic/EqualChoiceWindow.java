package Graphic;

import Language.DIA;
import Monoid.Equals;

import javax.swing.*;
import java.awt.*;

public class EqualChoiceWindow {
    private static final JFrame image=new JFrame("Choose Equal");
    private static final int WIDTH=500,HEIGHT=500,XOFF=50,YOFF=50, HEIGHT_OF_LINES = 50;
    private static final Label[] basicInfo =new Label[2];
    private static final JButton[] btn=new JButton[2];
    private static boolean isInterrupted;
    private static Thread exeThread, waitThread;

    public static void setWindow(int IDofDIA, DIA dia){
        //define image
        image.setSize(WIDTH, HEIGHT);
        image.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        image.getContentPane().setBackground(Color.white);
        image.setLayout(null);
        image.setLocationRelativeTo(null);
        isInterrupted=false;
        for (int i=0;i<basicInfo.length;i++) {
            basicInfo[i]=new Label();
            image.add(basicInfo[i]);
        }
        for(int i=0;i<btn.length;i++){
            btn[0]=new JButton("Check");
            btn[1]=new JButton("Close");
        }

        //set basic info
        basicInfo[0].setBounds(XOFF,YOFF,(WIDTH-2*XOFF),HEIGHT_OF_LINES);
        basicInfo[0].setText("The chosen automata is: "+IDofDIA_to_String(IDofDIA));
        basicInfo[1].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*4,(WIDTH-2*XOFF),HEIGHT_OF_LINES);
        basicInfo[1].setText("");

        //set left word
        JFormattedTextField leftWordField=new JFormattedTextField("");
        JLabel leftWordLabel = new JLabel("left word:");
        leftWordLabel.setBounds(XOFF,YOFF+HEIGHT_OF_LINES,(WIDTH-2*XOFF)/4,HEIGHT_OF_LINES);
        leftWordField.setBounds(XOFF+(WIDTH-2*XOFF)/4,YOFF+HEIGHT_OF_LINES,(WIDTH-2*XOFF)/4,HEIGHT_OF_LINES);
        image.add(leftWordField);
        image.add(leftWordLabel);

        //set right word
        JFormattedTextField rightWordField=new JFormattedTextField("");
        JLabel rightWordLabel = new JLabel("right word:");
        rightWordLabel.setBounds(XOFF+(WIDTH-2*XOFF)/4*2,YOFF+HEIGHT_OF_LINES,(WIDTH-2*XOFF)/4,HEIGHT_OF_LINES);
        rightWordField.setBounds(XOFF+(WIDTH-2*XOFF)/4*3,YOFF+HEIGHT_OF_LINES,(WIDTH-2*XOFF)/4,HEIGHT_OF_LINES);
        image.add(rightWordField);
        image.add(rightWordLabel);

        //set duration
        JFormattedTextField durationField=new JFormattedTextField(2);
        JLabel durationLabel = new JLabel("Duration:");
        durationLabel.setBounds(XOFF,YOFF+HEIGHT_OF_LINES*3,(WIDTH-2*XOFF)/2,HEIGHT_OF_LINES);
        durationField.setBounds(XOFF+(WIDTH-2*XOFF)/2,YOFF+HEIGHT_OF_LINES*3,(WIDTH-2*XOFF)/2,HEIGHT_OF_LINES);
        image.add(durationField);
        image.add(durationLabel);

        //set check button
        btn[0].setBounds(XOFF,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/2,HEIGHT_OF_LINES);
        btn[0].addActionListener(e -> timer(()->{
            String leftWord=(String) leftWordField.getValue();
            String rightWord=(String) rightWordField.getValue();
            boolean check=true;
            int resultCode=0;
                for(int i=0;i<leftWord.length();i++){
                    if(!dia.getAlphabet().contains(leftWord.charAt(i))){
                        resultCode=1;
                        check=false;
                    }
                }
                for(int i=0;i<rightWord.length();i++){
                    if(!dia.getAlphabet().contains(rightWord.charAt(i))){
                        resultCode=1;
                        check=false;
                    }
                }
                if(check){
                    if(Equals.isEqual(leftWord,rightWord,dia)){
                        resultCode=2;

                    }else{
                        resultCode=3;
                    }
                }
            if(!isInterrupted){
                switch (resultCode) {
                    case 1 -> basicInfo[1].setText("word has letters that aren't in DIA");
                    case 2 -> basicInfo[1].setText(returnEmptyWord(leftWord) + " and "
                            + returnEmptyWord(rightWord) + " are equal");
                    case 3 -> basicInfo[1].setText(returnEmptyWord(leftWord) + " and "
                            + returnEmptyWord(rightWord) + " are not equal");
                    default -> basicInfo[1].setText("error");
                }
            }else {
                isInterrupted=false;
            }
        },(int)durationField.getValue()*1000));
        image.add(btn[0]);

        //set close button
        btn[1].setBounds(XOFF+(WIDTH-2*XOFF)/2,YOFF+HEIGHT_OF_LINES*2,(WIDTH-2*XOFF)/2,HEIGHT_OF_LINES);
        btn[1].addActionListener(e ->{
            waitThread.interrupt();
            exeThread.interrupt();
            isInterrupted=false;
            image.dispose();
        });
        image.add(btn[1]);
        image.setVisible(true);
    }

    private static String IDofDIA_to_String(int IDofDIA){
        return switch (IDofDIA) {
            case 0 -> "Dyck";
            case 1 -> "Dyck R";
            case 2 -> "H+";
            case 3 -> "H-";
            default -> "Custom";
        };
    }

    private static String returnEmptyWord(String Word){
        if(Word.equalsIgnoreCase("")||Word.equalsIgnoreCase(" ")){
            return "\u03BB";
        }else{
            return Word;
        }
    }

    //Starts the action "runnable" for a duration of "timeInMilliSecond"
    //To fully interrupt an action, the action must use the "isInterrupt"-Variable
    private static void timer(Runnable runnable, int timeInMilliSeconds){
        exeThread=new Thread(runnable);
        waitThread=new Thread(()->{
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
            basicInfo[1].setText("action interrupt because it takes too long");
            exeThread.interrupt();
        }
    }
}
