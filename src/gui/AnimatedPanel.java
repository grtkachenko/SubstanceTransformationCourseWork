package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: gtkachenko
 * Date: 18/01/15
 */
public abstract class AnimatedPanel extends JPanel {
    private Timer timer;

    public void animate() {
        timer = new Timer(15, null);
        final long start = System.currentTimeMillis();
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final long now = System.currentTimeMillis();
                final long elapsed = now - start;
                doAnimationTick(elapsed);
            }
        });
        timer.start();
    }

    public void stopAnimation() {
        if (timer != null) {
            timer.stop();
        }
    }

    abstract void doAnimationTick(long timeElapsed);
}
