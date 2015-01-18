package course_work.algo;

import java.util.ArrayList;

/**
 * Created by antonkov on 1/18/2015.
 */
public class CachedComputationMethod {
    ComputationMethod computationMethod;
    ArrayList<double[]> Ts;
    ArrayList<double[]> Xs;
    ArrayList<double[]> Ws;

    public CachedComputationMethod(ComputationMethod computationMethod) {
        this.computationMethod = computationMethod;
        Ts = new ArrayList<>();
        Xs = new ArrayList<>();
        Ws = new ArrayList<>();
    }

    int getIteration(long timeElapsed) {
        int idx = (int) (timeElapsed / computationMethod.getSettings().dt);
        while (Ts.size() <= idx) {
            Ts.add(computationMethod.temperatures());
            Xs.add(computationMethod.concentration());
            Ws.add(computationMethod.reactionSpeed());
            computationMethod.makeStep();
        }
        return Math.max(0, idx);
    }

    public double[] temperatures(long timeElapsed) {
        int idx = getIteration(timeElapsed);
        return Ts.get(idx);
    }

    public double[] concentrations(long timeElapsed) {
        int idx = getIteration(timeElapsed);
        return Xs.get(idx);
    }

    public double[] reactionSpeed(long timeElapsed) {
        int idx = getIteration(timeElapsed);
        return Ws.get(idx);
    }
}
