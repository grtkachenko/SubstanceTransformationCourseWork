package course_work.gui;

import java.awt.*;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class DoubleDimensionPanel extends AnimatedPanel {
    private final int padding = 30;
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
        double maxValue = 0;

        for (int i = 0; i < currentMatrix.length; i++) {
            for (double cur : currentMatrix[i]) {
                maxValue = Math.max(maxValue, cur);
            }
        }
        int width = getWidth() - padding, height = getHeight() - padding;
        int matrixWidth = currentMatrix.length, matrixHeight = currentMatrix[0].length;
        int blockWidth = width / matrixWidth, blockHeight = height / matrixHeight;

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixHeight; j++) {
                g2.setPaint(new Color(1f, 0f, 0f, Math.min(1f, Math.max(0, (float) (currentMatrix[i][j] / maxValue)))));
                g2.fillRect(i * blockWidth, j * blockHeight, blockWidth, blockHeight);
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