package course_work.algo;

import java.util.Arrays;

/**
 * Created by Borys Minaiev on 1/18/2015.
 */
public class EulerImplicitForwardMethod implements ComputationMethod<double[]> {
    double[] T, X, W;
    Settings settings;

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
        final Settings s = getSettings();
        final int n = X.length;
        double[] newX, newT;
        {
            final double[] a = new double[n];
            final double[] b = new double[n];
            final double[] c = new double[n];
            final double[] d = new double[n];
            for (int i = 0; i < n; i++) {
                a[i] = c[i] = -s.D * s.dt / s.dz / s.dz;
                d[i] = Utils.w(X[i], T[i], s) * s.dt + X[i];
                b[i] = 1 + 2 * s.D * s.dt / s.dz / s.dz;
            }
            a[n - 1] = c[0] = 0;
            b[0] = b[n - 1] = 1;
            d[0] = s.xLeft;
            d[n - 1] = s.xRight;
            newX = Utils.solveTridiagonal(a, b, c, d);
        }
        {
            final double[] a = new double[n];
            final double[] b = new double[n];
            final double[] c = new double[n];
            final double[] d = new double[n];
            for (int i = 0; i < n; i++) {
                a[i] = c[i] = -s.kappa * s.dt / s.dz / s.dz;
                d[i] = -s.Q / s.C * Utils.w(X[i], T[i], s) * s.dt + T[i];
                b[i] = 1 + 2 * s.kappa * s.dt / s.dz / s.dz;
            }
            a[n - 1] = c[0] = 0;
            b[0] = b[n - 1] = 1;
            d[0] = s.Tm;
            d[n - 1] = s.T0;
            newT = Utils.solveTridiagonal(a, b, c, d);
        }
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
