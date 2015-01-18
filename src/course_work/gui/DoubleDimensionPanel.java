package course_work.gui;

import java.awt.*;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class DoubleDimensionPanel extends AnimatedPanel {
    private float progress;
    private double[][] currentMatrix;
    private DoubleDimensionDataSource doubleDimensionDataSource;

    public DoubleDimensionPanel(DoubleDimensionDataSource doubleDimensionDataSource) {
        this.doubleDimensionDataSource = doubleDimensionDataSource;
        currentMatrix = doubleDimensionDataSource.getValues(0);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setPaint(Color.gray);
        for (int i = 0; i < currentMatrix.length; i++) {
            for (int j = 0; j < currentMatrix[i].length; j++) {
                if (currentMatrix[i][j] > 100) {
                    g2.fillRect(i, j, 1, 1);
                }
            }
        }
        g2.setPaint(Color.black);
    }

    @Override
    void doAnimationTick(long timeElapsed) {
        currentMatrix = doubleDimensionDataSource.getValues(timeElapsed);
        repaint();
    }
}