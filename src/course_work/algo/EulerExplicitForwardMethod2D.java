package course_work.algo;

import java.util.Arrays;

/**
 * Created by antonkov on 1/18/2015.
 */
public class EulerExplicitForwardMethod2D implements ComputationMethod<double[][]> {
    double[][] T, X, W;
    Settings settings;

    double w(double x, double t) {
        Settings s = getSettings();
        return -s.K * Math.pow(x, s.alpha) * Math.exp(-s.E / (s.R * t));
    }

    public EulerExplicitForwardMethod2D() {
    }

    public double[][] initialT() {
        double[][] t = new double[settings.l_steps][settings.h_steps];
        for (double[] ts : t)
            Arrays.fill(ts, settings.T0);
        Arrays.fill(t[0], settings.Tw);
        return t;
    }

    public double[][] initialX() {
        double[][] x = new double[settings.l_steps][settings.h_steps];
        for (double[] xs : x)
            Arrays.fill(xs, settings.xRight);
        Arrays.fill(x[0], settings.xRight);
        return x;
    }

    @Override
    public void init(Settings settings) {
        this.settings = settings;
        T = initialT();
        X = initialX();
        W = new double[settings.l_steps][settings.h_steps];
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    private double der2(double[][] a, int i, int j) {
        return (a[i - 1][j] - 2 * a[i][j] + a[i + 1][j]) + (a[i][j - 1] - 2 * a[i][j] + a[i][j + 1]);
    }

    @Override
    public void makeStep() {
        Settings s = getSettings();
        double[][] newX = new double[X.length][X[0].length], newT = new double[T.length][T[0].length];
        Arrays.fill(newX[0], s.xLeft);
        Arrays.fill(newT[0], s.Tw);
        for (int i = 1; i < X.length - 1; ++i) {
            for (int j = 1; j < X[0].length - 1; ++j) {
                newX[i][j] = X[i][j] + s.dt * (s.D * der2(X, i, j) / s.dz / s.dz + w(X[i][j], T[i][j]));
                newT[i][j] = T[i][j] + s.dt * (s.kappa * der2(T, i, j) / s.dz / s.dz - s.Q / s.C * w(X[i][j], T[i][j]));
            }
        }
        Arrays.fill(newX[X.length - 1], s.xRight);
        Arrays.fill(newT[T.length - 1], s.T0);
        X = newX; T = newT;
    }

    @Override
    public double[][] temperatures() {
        return T;
    }

    @Override
    public double[][] concentration() {
        return X;
    }

    @Override
    public double[][] reactionSpeed() {
        return W;
    }
}
