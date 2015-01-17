/**
 * Created with IntelliJ IDEA.
 * User: gtkachenko
 * Date: 17/01/15
 * Time: 19:45
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GradientTest extends JPanel {
    private float progress = 0.0f;

    public void animate() {
        final Timer timer = new Timer(15, null);
        final long start = System.currentTimeMillis();

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final long now = System.currentTimeMillis();
                final long elapsed = now - start;
                progress = (elapsed % 4000 + 0f) / 4000f;

                System.out.println(progress);
                repaint();
            }
        });
        timer.start();
    }

    public void stopAnimation() {

    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("paint");
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
}