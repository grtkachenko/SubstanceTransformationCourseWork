package course_work.algo;

import java.util.Arrays;

/**
 * Created by antonkov on 1/18/2015.
 */
public class EulerExplicitForwardMethod implements ComputationMethod<double[]> {
    double[] T, X, W;
    Settings settings;

    double w(double x, double t) {
        Settings s = getSettings();
        return -s.K * Math.pow(x, s.alpha) * Math.exp(-s.E / (s.R * t));
    }

    public EulerExplicitForwardMethod() {
    }

    public double[] initialT() {
        double[] t = new double[settings.l_steps];
        Arrays.fill(t, settings.T0);
        t[0] = settings.Tm;
        return t;
    }

    public double[] initialX() {
        double[] x = new double[settings.l_steps];
        Arrays.fill(x, settings.xRight);
        x[0] = settings.xLeft;
        return x;
    }

    @Override
    public void init(Settings settings) {
        this.settings = settings;
        T = initialT();
        X = initialX();
        W = new double[settings.l_steps];
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public void makeStep() {
        Settings s = getSettings();
        double[] newX = new double[X.length], newT = new double[T.length];
        newX[0] = s.xLeft; newT[0] = s.Tm;
        for (int i = 1; i < X.length - 1; ++i) {
            newX[i] = X[i] + s.dt * (s.D * (X[i + 1] - 2 * X[i] + X[i - 1]) / s.dz / s.dz + Utils.w(X[i], T[i], getSettings()));
            newT[i] = T[i] + s.dt * (s.kappa * (T[i + 1] - 2 * T[i] + T[i - 1]) / s.dz / s.dz - s.Q / s.C * Utils.w(X[i], T[i], getSettings()));
        }
        newX[X.length - 1] = s.xRight; newT[T.length - 1] = s.T0;
        X = newX; T = newT;
    }

    @Override
    public double[] temperatures() {
        return T;
    }

    @Override
    public double[] concentration() {
        return X;
    }

    @Override
    public double[] reactionSpeed() {
        return W;
    }
}
