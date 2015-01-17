package gui;

import java.awt.*;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class GradientTest extends AnimatedPanel {
    private float progress;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.gray);
        int x = 0;
        int y = 0;
        // fill RoundRectangle2D.Double
        GradientPaint redtowhite = new GradientPaint(0, 0, Color.red, 200 * progress, y,
                Color.blue);
        g2.setPaint(redtowhite);
        g2.fillRect(0, 0, 200, 200);
        g2.setPaint(Color.black);
    }

    @Override
    void doAnimationTick(long timeElapsed) {
        progress = (timeElapsed % 4000 + 0f) / 4000f;
        repaint();
    }
}