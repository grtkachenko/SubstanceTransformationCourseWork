package course_work;

import course_work.algo.*;
import course_work.gui.DoubleDimensionDataSource;
import course_work.gui.GUIHelper;
import course_work.gui.SingleDimensionDataSource;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class Main {

    public static SingleDimensionDataSource[] getSingleDimensionDataSources(Settings settings, int methodId) {
        ComputationMethod<double[]> simpleComputationMethod = MethodMap.methods1D.get(methodId);
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

    public static DoubleDimensionDataSource[] getDoubleDimensionDataSources(Settings settings, int methodId) {
        ComputationMethod<double[][]> simpleComputationMethod = MethodMap.methods2D.get(methodId);
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
