package course_work;

import course_work.gui.DoubleDimensionDataSource;
import course_work.gui.GUIHelper;
import course_work.gui.SingleDimensionDataSource;

import java.util.Random;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class Main {

    // TODO: add params
    public static SingleDimensionDataSource[] getSingleDimensionDataSources() {
        SingleDimensionDataSource[] singleDimensionDataSources = {
                new SingleDimensionDataSource() {
                    private double prevStart = 0;
                    private double prevTime = 0;

                    public double testFunction(double value) {
                        return Math.exp(-value * value);
                    }

                    @Override
                    public double[] getValues(long timeElapsed) {
                        final int valuesCnt = 400;
                        double[] values = new double[valuesCnt];
                        for (int i = 0; i < valuesCnt; i++) {
                            values[i] = testFunction((prevStart - i) / 50d);
                        }

                        prevStart += (timeElapsed - prevTime) / 20d;
                        prevTime = timeElapsed;
                        return values;
                    }
                },
                new SingleDimensionDataSource() {
                    private double prevStart = 0;
                    private double prevTime = 0;

                    public double testFunction(double value) {
                        return Math.exp(-value * value);
                    }

                    @Override
                    public double[] getValues(long timeElapsed) {
                        final int valuesCnt = 400;
                        double[] values = new double[valuesCnt];
                        for (int i = 0; i < valuesCnt; i++) {
                            values[i] = testFunction((prevStart - i) / 50d);
                        }

                        prevStart += (timeElapsed - prevTime) / 20d;
                        prevTime = timeElapsed;
                        return values;
                    }
                }
        };
        return singleDimensionDataSources;
    }

    // TODO: add params
    public static DoubleDimensionDataSource[] getDoubleDimensionDataSources() {
        DoubleDimensionDataSource[] doubleDimensionDataSources = {
                new DoubleDimensionDataSource() {
                    @Override
                    public double[][] getValues(long timeElapsed) {
                        Random random = new Random();
                        final int size = 400;
                        double[][] result = new double[size][size];
                        for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size; j++) {
                                result[i][j] = random.nextInt(256);
                            }
                        }
                        return result;
                    }
                }
        };
        return doubleDimensionDataSources;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUIHelper.getInstance().showGui();
            }
        });
    }
}
