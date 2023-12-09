package Graphic;

import java.awt.*;
import javax.swing.*;
import static Graphic.window.*;

public class Draw extends JLabel {
    private static final long serialVersionUID = 1L;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d= (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.setColor(Color.white);
        g2d.fillRect(xoff,yoff,width-2*xoff,height-2*yoff);

        repaint();
    }
}
