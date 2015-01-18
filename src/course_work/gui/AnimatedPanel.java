package course_work.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: gtkachenko
 * Date: 18/01/15
 */
public abstract class AnimatedPanel extends JPanel {
    private Timer timer;
    private double speed;
    private double speedToSet;
    private boolean speedChanged;

    private long start;
    private long totalElapsedTime;

    private boolean isRunning = false;

    public void animate() {
        if (isRunning) {
            return;
        }
        System.out.println("Starting animation");
        isRunning = true;
        timer = new Timer(15, null);
        start = System.currentTimeMillis();
        totalElapsedTime = 0;
        speed = 1;
        setAnimationSpeed(1);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    return;
                }
                final long now = System.currentTimeMillis();
                if (speedChanged) {
                    speedChanged = false;
                    totalElapsedTime += (now - start) * speed;
                    start = now;
                    speed = speedToSet;
                }
                final long elapsed = totalElapsedTime + (long) ((now - start) * speed);
                if (elapsed < 0) {
                    stopAnimation();
                }
                doAnimationTick(elapsed);
            }
        });
        timer.start();
    }

    public void stopAnimation() {
        if (!isRunning) {
            return;
        }
        System.out.println("Stopping animation");
        isRunning = false;
        if (timer != null) {
            timer.stop();
        }
    }

    public void setAnimationSpeed(double speed) {
        System.out.println("Set speed to " + speed);
        this.speedToSet = speed;
        speedChanged = true;
    }

    abstract void doAnimationTick(long timeElapsed);
}
