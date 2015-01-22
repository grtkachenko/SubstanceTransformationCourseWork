package course_work.algo;

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
    static int counter = 0;
    @Override
    public void makeStep() {
//        counter++;
//        if (counter == 50)
//            throw new AssertionError();
        final Settings s = getSettings();
        final int n = X.length;

        final double[][][] a = new double[n][2][2];
        final double[][][] b = new double[n][2][2];
        final double[][][] c = new double[n][2][2];
        final double[][][] d = new double[n][1][2];
        for(int i = 0; i < n; i++) {
            a[i][0][0] = c[i][0][0] = s.D / s.dz / s.dz;
            a[i][1][1] = c[i][1][1] = s.kappa / s.dz / s.dz;
            a[i][0][1] = a[i][1][0] = c[i][0][1] = c[i][1][0] = b[i][0][1] = 0.0;
            b[i][0][0] = -2 * s.D / s.dz / s.dz - 1 / s.dt + Utils.wWithoutOneX(X[i], T[i], s);
//                    s.K * Math.pow(X[i], s.alpha - 1) * Math.exp(-s.E / s.R / T[i]);
            b[i][1][1] = -2 * s.kappa / s.dz / s.dz - 1 / s.dt;
            b[i][1][0] = -s.Q / s.C * Utils.wWithoutOneX(X[i], T[i], s);
//                    s.K * Math.pow(X[i], s.alpha - 1) * Math.exp(-s.E / s.R / T[i]);
            d[i][0][0] = -X[i] / s.dt;
            d[i][0][1] = -T[i] / s.dt;
        }
        b[0] = new double[][]{{1, 0}, {0, 1}};
        c[0] = new double[][]{{0, 0}, {0, 0}};
        d[0] = new double[][]{{s.xLeft, s.Tw}};
        b[b.length - 1] = new double[][]{{1, 0}, {0, 1}};
        a[a.length - 1] = new double[][]{{0, 0}, {0, 0}};
        d[d.length - 1] = new double[][]{{s.xRight, s.T0}};

//        System.out.println(Arrays.deepToString(a));
//        System.out.println(Arrays.deepToString(b));
//        System.out.println(Arrays.deepToString(c));
//        System.out.println(Arrays.deepToString(d));

        double[][][] res = Utils.solveTridiagonal(a, b, c, d);
        X = new double[n];
        T = new double[n];
        for (int i = 0; i < n; ++i) {
            X[i] = res[i][0][0];
            T[i] = res[i][0][1];
        }
        W = new double[n];
        for (int i = 0; i < n; i++) {
            W[i] = -Utils.w(X[i], T[i], settings);
        }

        System.out.println("AAA");
        System.out.println(Arrays.toString(X));
        System.out.println(Arrays.toString(T));
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
