package course_work.algo;

import java.util.Arrays;

public class NewtonLinearization implements ComputationMethod<double[]> {
    double[] T, X, W;
    Settings settings;

    public double[] initialT() {
        double[] t = new double[settings.l_steps];
        Arrays.fill(t, settings.T0);
        t[0] = settings.Tw;
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

        double Ddivdz2 = s.D / s.dz / s.dz;
        double invDt = 1.0 / s.dt;
        double kappaDivDz2 = s.kappa / s.dz / s.dz;
        double eDivR = s.E / s.R;
        double QDivC = s.Q / s.C;

        final int n = X.length;
        double[] newX, newT;
        {
            final double[] a = new double[n];
            final double[] b = new double[n];
            final double[] c = new double[n];
            final double[] d = new double[n];
            for (int i = 0; i < n; i++) {
                a[i] = c[i] = Ddivdz2;
                b[i] = -2 * Ddivdz2 - invDt + Utils.wWithoutOneX(X[i], T[i], s) * s.alpha;
                d[i] = X[i] * (-invDt + Utils.wWithoutOneX(X[i], T[i], s) * (1 - s.alpha));
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
                a[i] = c[i] = kappaDivDz2;
                b[i] = -2 * kappaDivDz2 - invDt - QDivC * Utils.w(newX[i], T[i], s) *  eDivR / (T[i] * T[i]);
                d[i] = -T[i] * invDt + QDivC * Utils.w(newX[i], T[i], s) * (1 - eDivR / T[i]);
            }
            a[n - 1] = c[0] = 0;
            b[0] = b[n - 1] = 1;
            d[0] = s.Tw;
            d[n - 1] = s.T0;
            newT = Utils.solveTridiagonal(a, b, c, d);
        }
        X = newX;
        T = newT;
        W = new double[n];
        for (int i = 0; i < n; i++) {
            W[i] = -Utils.w(X[i], T[i], settings);
        }
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
