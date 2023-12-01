/*

import java.awt.*;

import javax.swing.*;

public class Draw extends JLabel {
    private static final long serialVersionUID = 1L;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d= (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g.setColor(Color.white);
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                g.drawRect(.xoff+100*i,Fenster.yoff+100*j,100,100);
            }
        }

        for(int k=0;k<(Fenster.state.length/3);k++) {
            for(int i=0;i<(Fenster.state.length/3);i++) {
                if(Fenster.state[(i+(3*k))]==1) {
                    g.drawImage(ImageLoader.imgX, Fenster.xoff+1+100*(i%3), Fenster.yoff+1+100*k, 98, 98, null);
                }else if(Fenster.state[(i+(3*k))]==2){
                    g.drawImage(ImageLoader.imgO, Fenster.xoff+1+100*(i%3), Fenster.yoff+1+100*k, 98, 98, null);
                }
            }
        }

        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.BOLD,20));
        switch (Fenster.gewinner) {
            case 0:if (Fenster.playerO) {
                g.drawString("Spieler O ist dran", Fenster.xoff+75, Fenster.yoff-25);
            }else {
                g.drawString("Spieler X ist dran", Fenster.xoff+75, Fenster.yoff-25);
            }break;
            case 1:g.drawString("Spieler X hat gewonnen", Fenster.xoff+75, Fenster.yoff-25);break;
            case 2:g.drawString("Spieler O hat gewonnen", Fenster.xoff+75, Fenster.yoff-25);break;
        }
        g.setColor(new Color(255,0,0));
        switch (Gewinner.siegerlinie) {
            case 1:g.drawLine(Fenster.xoff+50,Fenster.yoff+50, Fenster.xoff+250, Fenster.yoff+50);break;
            case 2:g.drawLine(Fenster.xoff+50,Fenster.yoff+150, Fenster.xoff+250, Fenster.yoff+150);break;
            case 3:g.drawLine(Fenster.xoff+50,Fenster.yoff+250, Fenster.xoff+250, Fenster.yoff+250);break;
            case 4:g.drawLine(Fenster.xoff+50,Fenster.yoff+50, Fenster.xoff+50, Fenster.yoff+250);break;
            case 5:g.drawLine(Fenster.xoff+150,Fenster.yoff+50, Fenster.xoff+150, Fenster.yoff+250);break;
            case 6:g.drawLine(Fenster.xoff+250,Fenster.yoff+50, Fenster.xoff+250, Fenster.yoff+250);break;
            case 7:g.drawLine(Fenster.xoff+50,Fenster.yoff+50, Fenster.xoff+250, Fenster.yoff+250);break;
            case 8:g.drawLine(Fenster.xoff+50,Fenster.yoff+250, Fenster.xoff+250, Fenster.yoff+50);break;
        }

        repaint();
    }
}*/

