package Graphic;

import java.awt.*;
import java.io.Serial;
import javax.swing.*;
import static Graphic.window.*;

public class Draw extends JLabel {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d= (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.setColor(Color.white);
        g2d.fillRect(XOFF,YOFF,window.WIDTH-2*XOFF,window.HEIGHT-2*YOFF);

        repaint();
    }
}
