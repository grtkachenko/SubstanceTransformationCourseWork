package course_work;

import course_work.algo.CachedComputationMethod;
import course_work.algo.ComputationMethod;
import course_work.algo.EulerExplicitForwardMethod;
import course_work.algo.Settings;
import course_work.gui.DoubleDimensionDataSource;
import course_work.gui.GUIHelper;
import course_work.gui.SingleDimensionDataSource;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class Main {

    // TODO: add params
    public static SingleDimensionDataSource[] getSingleDimensionDataSources(Settings settings) {
        ComputationMethod simpleComputationMethod = new EulerExplicitForwardMethod();
        simpleComputationMethod.init(settings);
        final CachedComputationMethod computationMethod = new CachedComputationMethod(simpleComputationMethod);

        SingleDimensionDataSource[] singleDimensionDataSources = {
                new SingleDimensionDataSource() {
                    @Override
                    public double[] getValues(long timeElapsed) {
                        return computationMethod.temperatures(timeElapsed);
                    }
                },
                new SingleDimensionDataSource() {
                    @Override
                    public double[] getValues(long timeElapsed) {
                        return computationMethod.concentrations(timeElapsed);
                    }
                },
                new SingleDimensionDataSource() {
                    @Override
                    public double[] getValues(long timeElapsed) {
                        return computationMethod.reactionSpeed(timeElapsed);
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
