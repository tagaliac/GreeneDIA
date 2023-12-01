package Graphic;

/*
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import Action.*;

public class window {
    public static JFrame bild=new JFrame("TicTacToe");
    public static int width=700,height=700,xoff=200,yoff=200,gewinner=0;
    public static boolean playerO=false;
    public static JButton btn[]=new JButton[9];
    public static int state[]=new int[9];

    public static void setFenster() {
        bild.setSize(width, height);
        bild.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bild.getContentPane().setBackground(new Color(0,0,255));
        bild.setLayout(null);
        bild.setLocationRelativeTo(null);

        for (int i=0;i<btn.length;i++) {
            btn[i]=new JButton();
            btn[i].setVisible(true);
            btn[i].addActionListener(new ActionHandler());
            btn[i].setContentAreaFilled(false);
            btn[i].setFocusPainted(false);
            btn[i].setBorder(null);
            bild.add(btn[i]);
        }
        Place.place();

        JButton kallll=new JButton("Reset");
        kallll.setVisible(true);
        kallll.setBounds(xoff+310,yoff+200,150,50);
        kallll.setBackground(new Color(255,0,0));
        kallll.setForeground(Color.white);
        kallll.setContentAreaFilled(true);
        kallll.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Neu.reset();
            }

        });
        bild.add(kallll);

        Draw g = new Draw();
        g.setBounds(0, 0, width, height);
        g.setVisible(true);
        bild.add(g);

        bild.setVisible(true);
    }
}*/
