package Graphic;

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
    private static int answerChoosingAutomata;
    private static EqualList equal;
    private static String[][] box;

    public static void setWindow(DIA[] dias,int maxlength) {
        dea_dia[0] = "Dyck";
        dea_dia[1] = "Dyck Plus";
        dea_dia[2] = "H And";
        dea_dia[3] = "H Or";

        bild.setSize(width, height);
        bild.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bild.getContentPane().setBackground(new Color(0,255,0));
        bild.setLayout(null);
        bild.setLocationRelativeTo(null);

        for(int i=0;i<dea_dia.length;i++){
            btn[i]=new JButton(dea_dia[i]);
            btn[i].setBounds(xoff+i*(width-2*xoff)/dea_dia.length,yoff,(width-2*xoff)/dea_dia.length,50);
            setActionListener(i);
            bild.add(btn[i]);
        }

        btn[dea_dia.length-1]=new JButton("equals");
        btn[dea_dia.length-1].setBounds(width/2-100,yoff+50,100,50);
        btn[dea_dia.length-1].addActionListener(e -> {
            equal= Equals.findEqual(dias[answerChoosingAutomata],maxlength);
            textArea.setText("");
            for(int i=0;i<equal.Size();i++) {
                textArea.append(equal.getEntry(i) + "\n");
            }
        });
        textArea.setBounds(width/2-150,yoff+100,300,400);
        bild.add(textArea);
        bild.add(btn[dea_dia.length-1]);

        btn[dea_dia.length]=new JButton("Box");
        btn[dea_dia.length].setBounds(width/2,yoff+50,100,50);
        btn[dea_dia.length].addActionListener(e -> {
            box= GreensRelation.getGreenBox(dias[answerChoosingAutomata],maxlength);
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
}
