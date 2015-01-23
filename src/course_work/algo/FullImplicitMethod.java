package course_work.algo;

import javax.rmi.CORBA.Util;
import java.util.Arrays;

/**
 * Created by zumzoom on 22/01/15.
 */
public class FullImplicitMethod implements ComputationMethod<double[]> {
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
    public Settings getSettings() { return settings; }

    @Override
    public void makeStep() {
        final Settings s = getSettings();
        final int n = X.length;

        double Ddivdz2 = s.D / s.dz / s.dz;
        double invDt = 1.0 / s.dt;
        double kappaDivDz2 = s.kappa / s.dz / s.dz;
        double QDivC = s.Q / s.C;

        final double[][][] a = new double[n][2][2];
        final double[][][] b = new double[n][2][2];
        final double[][][] c = new double[n][2][2];
        final double[][][] d = new double[n][2][1];
        for (int i = 0; i < n; i++) {
            double wWithout = Utils.wWithoutOneX(X[i], T[i], s);
            a[i][0][0] = c[i][0][0] = Ddivdz2;
            a[i][1][1] = c[i][1][1] = kappaDivDz2;
            b[i][0][0] = -2 * Ddivdz2 - invDt + wWithout;
            b[i][1][1] = -2 * kappaDivDz2 - invDt;
            b[i][1][0] = -QDivC * wWithout;
            d[i][0][0] = -X[i] * invDt;
            d[i][1][0] = -T[i] * invDt;
        }
        b[0] = new double[][]{{1, 0}, {0, 1}};
        c[0] = new double[][]{{0, 0}, {0, 0}};
        d[0] = new double[][]{{s.xLeft}, {s.Tw}};
        b[b.length - 1] = new double[][]{{1, 0}, {0, 1}};
        a[a.length - 1] = new double[][]{{0, 0}, {0, 0}};
        d[d.length - 1] = new double[][]{{s.xRight}, {s.T0}};

        double[][][] res = Utils.solveTridiagonal(a, b, c, d);
        X = new double[n];
        T = new double[n];
        for (int i = 0; i < n; ++i) {
            X[i] = res[i][0][0];
            T[i] = res[i][1][0];
        }

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
