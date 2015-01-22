package course_work.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.Raster;

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
        double minValue = Double.MAX_VALUE;
        for (double[] row : currentMatrix) {
            for (double cur : row) {
                maxValue = Math.max(maxValue, cur);
                minValue = Math.min(minValue, cur);
            }
        }
        double len = maxValue - minValue;
        int width = getWidth() - padding, height = getHeight() - padding;
        int matrixWidth = currentMatrix.length, matrixHeight = currentMatrix[0].length;
        int blockWidth = width / matrixWidth, blockHeight = height / matrixHeight;
        for (double[] row : currentMatrix) {
            for (int i = 0; i < row.length; i++) {
                row[i] = (row[i] - minValue) / len;
                row[i] = Math.min(1f, Math.max(0f, row[i]));
            }
        }
        BufferedImage img = new BufferedImage(matrixWidth, matrixHeight, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixHeight; j++) {
                img.setRGB(i, j, new Color((float)currentMatrix[i][j], 0f, 1f - (float)currentMatrix[i][j], 1f).getRGB());
            }
        }
        g2.drawImage(img, 0, 0, width, height, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
        g2.setPaint(Color.black);
    }

    @Override
    void doAnimationTick(long timeElapsed) {
        currentMatrix = doubleDimensionDataSource.getValues(timeElapsed);
        repaint();
    }
}