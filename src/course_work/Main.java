package course_work;

import course_work.algo.*;
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
        ComputationMethod<double[]> simpleComputationMethod = new EulerExplicitForwardMethod();
        simpleComputationMethod.init(settings);
        final CachedComputationMethod<double[]> computationMethod = new CachedComputationMethod(simpleComputationMethod);

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
    public static DoubleDimensionDataSource[] getDoubleDimensionDataSources(Settings settings) {
        ComputationMethod<double[][]> simpleComputationMethod = new EulerExplicitForwardMethod2D();
        simpleComputationMethod.init(settings);
        final CachedComputationMethod<double[][]> computationMethod = new CachedComputationMethod(simpleComputationMethod);
        DoubleDimensionDataSource[] doubleDimensionDataSources = {
                new DoubleDimensionDataSource() {
                    @Override
                    public double[][] getValues(long timeElapsed) {
                        return computationMethod.temperatures(timeElapsed);
                    }
                },
                new DoubleDimensionDataSource() {
                    @Override
                    public double[][] getValues(long timeElapsed) {
                        return computationMethod.concentrations(timeElapsed);
                    }
                },
                new DoubleDimensionDataSource() {
                    @Override
                    public double[][] getValues(long timeElapsed) {
                        return computationMethod.reactionSpeed(timeElapsed);
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
