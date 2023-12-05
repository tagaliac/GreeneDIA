package Graphic;

import Default.Main;
import Language.DIA;
import Monoid.EqualList;
import Monoid.Equals;
import Monoid.GreensRelation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class window {
    public static JFrame bild=new JFrame("GreeneDIA");
    public static int width=700,height=700,xoff=100,yoff=100;
    public static JButton[] btn=new JButton[6];
    private static JTextArea textArea=new JTextArea();
    private static final String[] dea_dia = new String[4];
    private static DIA[] dias=new DIA[dea_dia.length];
    private static int answerChoosingAutomata,maxlength=1;
    private static EqualList equal;
    private static String[][] box;

    public static void setWindow() {
        dea_dia[0] = "Dyck";
        dea_dia[1] = "Dyck Plus";
        dea_dia[2] = "H And";
        dea_dia[3] = "H Or";
        setDIAS(15,15);

        bild.setSize(width, height);
        bild.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bild.getContentPane().setBackground(new Color(0,255,0));
        bild.setLayout(null);
        bild.setLocationRelativeTo(null);

        JFormattedTextField input1;
        JLabel label1 = new JLabel("Maxlength:");
        input1 = new JFormattedTextField(6);
        label1.setBounds(width/2-250,yoff+50,80,50);
        input1.setBounds(width/2-170,yoff+50,70,50);
        bild.add(input1);
        bild.add(label1);

        JFormattedTextField input2;
        JLabel label2 = new JLabel("First Length:");
        input2 = new JFormattedTextField(15);
        label2.setBounds(width/2+100,yoff+50,80,25);
        input2.setBounds(width/2+170,yoff+50,70,25);
        bild.add(input2);
        bild.add(label2);

        JFormattedTextField input3;
        JLabel label3 = new JLabel("Second Length:");
        input3 = new JFormattedTextField(15);
        label3.setBounds(width/2+100,yoff+75,80,25);
        input3.setBounds(width/2+170,yoff+75,70,25);
        bild.add(input3);
        bild.add(label3);

        for(int i=0;i<dea_dia.length;i++){
            btn[i]=new JButton(dea_dia[i]);
            btn[i].setBounds(xoff+i*(width-2*xoff)/dea_dia.length,yoff,(width-2*xoff)/dea_dia.length,50);
            setActionListener(i);
            bild.add(btn[i]);
        }

        JScrollPane scroll = (new JScrollPane(textArea));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(xoff,yoff+100,width-2*xoff,height-2*yoff-100);
        textArea.setEditable(true);
        bild.getContentPane().add(scroll);

        btn[dea_dia.length-1]=new JButton("Equals");
        btn[dea_dia.length-1].setBounds(width/2-100,yoff+50,100,50);
        btn[dea_dia.length-1].addActionListener(e -> {
            if((int)input1.getValue()<3){
                maxlength=3;
            }else{
                maxlength=(int)input1.getValue();
            }
            setDIAS((int)input2.getValue(),(int)input3.getValue());
            equal= Equals.findEqual(dias[answerChoosingAutomata],maxlength);
            textArea.setText("");
            for(int i=0;i<equal.Size();i++) {
                textArea.append(equal.getEntry(i) + "\n");
            }
        });
        bild.add(btn[dea_dia.length-1]);

        btn[dea_dia.length]=new JButton("Box");
        btn[dea_dia.length].setBounds(width/2,yoff+50,100,50);
        btn[dea_dia.length].addActionListener(e -> {
            if((int)input1.getValue()<3){
                maxlength=3;
            }else{
                maxlength=(int)input1.getValue();
            }
            setDIAS((int)input2.getValue(),(int)input3.getValue());
            box= GreensRelation.getGreenBox(dias[answerChoosingAutomata],maxlength);
            textArea.setColumns(box[0].length);
            textArea.setRows(box.length);
            textArea.setText("");
            for(int i=0;i<box.length;i++) {
                for (int j=0;j<box[i].length;j++){
                    textArea.append(box[i][j] + " ");
                }
                textArea.append("\n");
            }
        });
        bild.add(btn[dea_dia.length]);


        Draw draw = new Draw();
        draw.setBounds(0,0,width,height);
        draw.setVisible(true);
        bild.add(draw);

        bild.setVisible(true);
    }


    private static void setActionListener(int choice){
        btn[choice].addActionListener(e -> {
            answerChoosingAutomata=choice;
        });
    }

    private static void setDIAS(int length1, int length2){
        dias[0]= Main.createDyck(length1);
        dias[1]= Main.createDyckPlus(length1);
        dias[2]= Main.createHAnd(length1,length2);
        dias[3]= Main.createHOr(length1,length2);
    }
}
