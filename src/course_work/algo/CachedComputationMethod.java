package course_work.algo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antonkov on 1/18/2015.
 */
public class CachedComputationMethod<T> {
    private final int maxTime;
    private enum ComputationState {
        NOT_STARTED,
        RUNNING,
        FINISHED
    }
    ComputationMethod<T> computationMethod;
    ArrayList<T> Ts;
    ArrayList<T> Xs;
    ArrayList<T> Ws;
    private ComputationState state;
    private List<ComputationProgressListener> listeners = new ArrayList<>();
    private volatile boolean isComputing = false;

    public CachedComputationMethod(ComputationMethod computationMethod) {
        this.computationMethod = computationMethod;
        Ts = new ArrayList<>();
        Xs = new ArrayList<>();
        Ws = new ArrayList<>();
        state = ComputationState.NOT_STARTED;
        maxTime = computationMethod.getSettings().getCompTime() * 1000;
        System.out.println("MAX TIME " + maxTime);
    }

    int getIteration(long timeElapsed) {
        int idx = (int) (timeElapsed / computationMethod.getSettings().dt);
        return Math.min(Ts.size() - 1, Math.max(0, idx));
    }

    public T temperatures(long timeElapsed) {
        int idx = getIteration(timeElapsed);
        return Ts.get(idx);
    }

    public T concentrations(long timeElapsed) {
        int idx = getIteration(timeElapsed);
        return Xs.get(idx);
    }

    public T reactionSpeed(long timeElapsed) {
        int idx = getIteration(timeElapsed);
        return Ws.get(idx);
    }

    public synchronized void startComputation() {
        if (state == ComputationState.NOT_STARTED) {
            state = ComputationState.RUNNING;
            isComputing = true;

            for (ComputationProgressListener listener : listeners) {
                listener.onComputationStarted();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int idx = (int) (maxTime / computationMethod.getSettings().dt);
                    int curProgress = 0;
                    long before = System.currentTimeMillis();
                    while (Ts.size() <= idx && isComputing) {
                        Ts.add(computationMethod.temperatures());
                        Xs.add(computationMethod.concentration());
                        Ws.add(computationMethod.reactionSpeed());
                        computationMethod.makeStep();
                        if ((int) (Ts.size() * 100d / idx) > curProgress) {
                            curProgress = (int) (Ts.size() * 100d / idx);
                            for (ComputationProgressListener listener : listeners) {
                                listener.onProgressChanged(curProgress);
                            }
                        }
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            for (ComputationProgressListener listener : listeners) {
                                listener.onComputationFinished();
                            }
                        }
                    });
                    System.out.println(System.currentTimeMillis() - before);
                    state = ComputationState.FINISHED;

                }
            }).start();
        }
    }

    public void stopComputation() {
        isComputing = false;
    }

    public void addComputationProgressListener(ComputationProgressListener listener) {
        listeners.add(listener);
    }

    public interface ComputationProgressListener {
        public void onProgressChanged(int progress);
        public void onComputationStarted();
        public void onComputationFinished();
    }
}
