package course_work;

import course_work.algo.*;
import course_work.gui.DoubleDimensionDataSource;
import course_work.gui.GUIHelper;
import course_work.gui.SingleDimensionDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class Main {

    public static SingleDimensionDataSource[] getSingleDimensionDataSources(Settings settings, int methodId, CachedComputationMethod.ComputationProgressListener listener, JButton stopButton) {

        ComputationMethod<double[]> simpleComputationMethod = MethodMap.methods1D.get(methodId);
        simpleComputationMethod.init(settings);
        final CachedComputationMethod<double[]> computationMethod = new CachedComputationMethod(simpleComputationMethod);
        if (listener != null) {
            computationMethod.addComputationProgressListener(listener);
        }

        computationMethod.startComputation();
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computationMethod.stopComputation();
            }
        });
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

    public static DoubleDimensionDataSource[] getDoubleDimensionDataSources(Settings settings, int methodId, CachedComputationMethod.ComputationProgressListener listener, JButton stopButton) {
        ComputationMethod<double[][]> simpleComputationMethod = MethodMap.methods2D.get(methodId);
        simpleComputationMethod.init(settings);
        final CachedComputationMethod<double[][]> computationMethod = new CachedComputationMethod(simpleComputationMethod);
        if (listener != null) {
            computationMethod.addComputationProgressListener(listener);
        }
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computationMethod.stopComputation();
            }
        });

        computationMethod.startComputation();
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
