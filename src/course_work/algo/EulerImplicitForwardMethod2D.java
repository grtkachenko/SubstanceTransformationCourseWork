package course_work.algo;

import javax.rmi.CORBA.Util;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by Borys Minaiev on 1/18/2015.
 */
public class EulerImplicitForwardMethod2D implements ComputationMethod<double[][]> {
    double[][] T, X, W;
    Settings settings;

    public EulerImplicitForwardMethod2D() {
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
        calcW();
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    private int getIdCalcZ(int z, int y, final double[][] X) {
        return z + X.length * y;
    }

    private int getIdCalcY(int z, int y, final double[][] X) {
        return y + X[0].length * z;
    }

    @Override
    public void makeStep() {
        final Settings s = getSettings();
        double[][] newXHalf = new double[X.length][X[0].length], newTHalf = new double[T.length][T[0].length];
        double invDt = 1.0 / s.dt;
        double invDz2 = 1.0 / s.dz / s.dz;
        double invDy2 = 1.0 / s.dy / s.dy;
        double dDivZ2 = s.D * invDz2;
        double kappaDivZ2 = s.kappa * invDz2;
        double dDivY2 = s.D * invDy2;
        double kappaDivY2 = s.kappa * invDy2;

        {
            // implicit by z, explicit by y
            {
                // calculate new X
                int n = X.length * X[0].length;
                final double[] a = new double[n];
                final double[] b = new double[n];
                final double[] c = new double[n];
                final double[] d = new double[n];
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[z].length; y++) {
                        int id = getIdCalcZ(z, y, X);
                        if (z == 0) {
                            b[id] = 1;
                            d[id] = s.xLeft;
                        } else {
                            if (z == X.length - 1) {
                                b[id] = 1;
                                d[id] = s.xRight;
                            } else {
                                if (y == 0 || y == X[0].length - 1) {
                                    b[id] = 1;
                                    d[id] = X[z][y];
                                } else {
                                    a[id] = c[id] = -dDivZ2;
                                    b[id] = invDt + 2 * dDivZ2 - Utils.wWithoutOneX(X[z][y], T[z][y], s);
                                    d[id] = (X[z][y - 1] - 2 * X[z][y] + X[z][y + 1]) * invDy2 + X[z][y] * invDt;
                                }
                            }
                        }
                    }
                }
                final double[] result = Utils.solveTridiagonal(a, b, c, d);
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[0].length; y++) {
                        int id = getIdCalcZ(z, y, X);
                        newXHalf[z][y] = result[id];
                    }
                }
                for (int z = 0; z < X.length; z++) {
                    newXHalf[z][0] = newXHalf[z][1];
                    newXHalf[z][X[0].length - 1] = newXHalf[z][X[0].length - 2];
                }
            }
            {
                // calculate new T
                int n = X.length * X[0].length;
                final double[] a = new double[n];
                final double[] b = new double[n];
                final double[] c = new double[n];
                final double[] d = new double[n];
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[z].length; y++) {
                        int id = getIdCalcZ(z, y, X);
                        if (z == 0) {
                            b[id] = 1;
                            d[id] = s.Tw;
                        } else {
                            if (z == X.length - 1) {
                                b[id] = 1;
                                d[id] = s.T0;
                            } else {
                                if (y == 0 || y == X[0].length - 1) {
                                    b[id] = 1;
                                    d[id] = T[z][y];
                                } else {
                                    a[id] = c[id] = -kappaDivZ2;
                                    b[id] = invDt + 2 * kappaDivZ2;
                                    d[id] = T[z][y] * invDt + (T[z][y - 1] - 2 * T[z][y] + T[z][y + 1]) * invDy2 - s.Q / s.C * (-W[z][y]);
                                }
                            }
                        }
                    }
                }
                final double[] result = Utils.solveTridiagonal(a, b, c, d);
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[0].length; y++) {
                        int id = getIdCalcZ(z, y, X);
                        newTHalf[z][y] = result[id];
                    }
                }
                for (int z = 0; z < X.length; z++) {
                    newTHalf[z][0] = newTHalf[z][1];
                    newTHalf[z][X[0].length - 1] = newTHalf[z][X[0].length - 2];
                }
            }
            calcW();
        }

        double[][] newX = new double[X.length][X[0].length], newT = new double[T.length][T[0].length];
        {
            // implicit by y, explicit by z
            {
                // calculate new X
                int n = X.length * X[0].length;
                final double[] a = new double[n];
                final double[] b = new double[n];
                final double[] c = new double[n];
                final double[] d = new double[n];
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[z].length; y++) {
                        int id = getIdCalcY(z, y, X);
                        if (z == 0) {
                            b[id] = 1;
                            d[id] = s.xLeft;
                        } else {
                            if (z == X.length - 1) {
                                b[id] = 1;
                                d[id] = s.xRight;
                            } else {
                                if (y == 0 || y == X[0].length - 1) {
                                    b[id] = 1;
                                    d[id] = newXHalf[z][y];
                                } else {
                                    a[id] = c[id] = -dDivY2;
                                    b[id] = invDt + 2 * dDivY2;
                                    d[id] = newXHalf[z][y] * invDt - dDivY2 * (X[z][y - 1] - 2 * X[z][y] + X[z][y + 1]);
                                }
                            }
                        }
                    }
                }
                final double[] result = Utils.solveTridiagonal(a, b, c, d);
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[0].length; y++) {
                        int id = getIdCalcY(z, y, X);
                        newX[z][y] = result[id];
                    }
                }
                for (int z = 0; z < X.length; z++) {
                    newX[z][0] = newX[z][1];
                    newX[z][X[0].length - 1] = newX[z][X[0].length - 2];
                }
            }
            {
                // calculate new T
                int n = X.length * X[0].length;
                final double[] a = new double[n];
                final double[] b = new double[n];
                final double[] c = new double[n];
                final double[] d = new double[n];
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[z].length; y++) {
                        int id = getIdCalcY(z, y, X);
                        if (z == 0) {
                            b[id] = 1;
                            d[id] = s.Tw;
                        } else {
                            if (z == X.length - 1) {
                                b[id] = 1;
                                d[id] = s.T0;
                            } else {
                                if (y == 0 || y == X[0].length - 1) {
                                    b[id] = 1;
                                    d[id] = newTHalf[z][y];
                                } else {
                                    a[id] = c[id] = -kappaDivY2;
                                    b[id] = invDt + 2 * kappaDivY2;
                                    d[id] = newTHalf[z][y] * invDt - kappaDivY2 * (T[z][y - 1] - 2 * T[z][y] + T[z][y + 1]);
                                }
                            }
                        }
                    }
                }
                final double[] result = Utils.solveTridiagonal(a, b, c, d);
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[0].length; y++) {
                        int id = getIdCalcY(z, y, X);
                        newT[z][y] = result[id];
                    }
                }
                for (int z = 0; z < X.length; z++) {
                    newT[z][0] = newT[z][1];
                    newT[z][X[0].length - 1] = newT[z][X[0].length - 2];
                }
            }
        }
        newX = newXHalf;
        newT = newTHalf;

        X = newX;
        T = newT;

    }

    public void calcW() {
        Settings s = getSettings();
        W = new double[X.length][X[0].length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X[i].length; j++) {
                W[i][j] = -Utils.w(X[i][j], T[i][j], s);
            }
        }
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
