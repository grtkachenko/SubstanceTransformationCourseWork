package course_work.algo;

import javax.rmi.CORBA.Util;
import java.util.Arrays;

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
        W = new double[settings.l_steps][settings.h_steps];
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
        double[][] newX = new double[X.length][X[0].length], newT = new double[T.length][T[0].length];

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
                                    a[id] = c[id] = -s.D / s.dz / s.dz;
                                    b[id] = 1.0 / s.dt + 2 * s.D / s.dz / s.dz - Utils.wWithoutOneX(X[z][y], T[z][y], s);
                                    d[id] = (X[z][y - 1] - 2 * X[z][y] + X[z][y + 1]) / s.dy / s.dy;
                                }
                            }
                        }
                    }
                }
                final double[] result = Utils.solveTridiagonal(a, b, c, d);
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[0].length; y++) {
                        int id = getIdCalcZ(z, y, X);
                        newX[z][y] = X[z][y] + result[id];
                    }
                }
                for (int z = 0; z < X.length; z++) {
                    newX[z][0] = newX[z][1];
                    newX[z][X[0].length - 2] = newX[z][X[0].length - 1];
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
                                    a[id] = c[id] = -s.kappa / s.dy / s.dy;
                                    b[id] = 1.0 / s.dt + 2 * s.kappa / s.dy / s.dy;
                                    d[id] = T[z][y] / s.dt + (T[z][y - 1] - 2 * T[z][y] + T[z][y + 1]) / s.dy / s.dy - s.Q / s.C * Utils.w(X[z][y], T[z][y], s);
                                }
                            }
                        }
                    }
                }
                final double[] result = Utils.solveTridiagonal(a, b, c, d);
                for (int z = 0; z < X.length; z++) {
                    for (int y = 0; y < X[0].length; y++) {
                        int id = getIdCalcZ(z, y, X);
                        newT[z][y] = result[id];
                    }
                }
                for (int z = 0; z < X.length; z++) {
                    newT[z][0] = newT[z][1];
                    newT[z][X[0].length - 2] = newT[z][X[0].length - 1];
                }
            }
        }



//        {
//            // implicit by y, explicit by z
//            {
//                // calculate new X
//                int n = X.length * X[0].length;
//                final double[] a = new double[n];
//                final double[] b = new double[n];
//                final double[] c = new double[n];
//                final double[] d = new double[n];
//                for (int z = 0; z < X.length; z++) {
//                    for (int y = 0; y < X[z].length; y++) {
//                        int id = getIdCalcY(z, y, X);
//                        if (z == 0) {
//                            b[id] = 1;
//                            d[id] = s.xLeft;
//                        } else {
//                            if (z == X.length - 1) {
//                                b[id] = 1;
//                                d[id] = s.xRight;
//                            } else {
//                                if (y == 0 || y == X[0].length - 1) {
//                                    b[id] = 1;
//                                    d[id] = X[z][y];
//                                } else {
//                                    a[id] = c[id] = -s.D / s.dz / s.dz;
//                                    b[id] = 1.0 / s.dt + 2 * s.D / s.dz / s.dz - Utils.wWithoutOneX(X[z][y], T[z][y], s);
//                                    d[id] = X[z][y] / s.dt + (X[z - 1][y] - 2 * X[z][y] + X[z + 1][y]) / s.dy / s.dy;
//                                }
//                            }
//                        }
//                    }
//                }
//                final double[] result = Utils.solveTridiagonal(a, b, c, d);
//                for (int z = 0; z < X.length; z++) {
//                    for (int y = 0; y < X[0].length; y++) {
//                        int id = getIdCalcY(z, y, X);
//                        newX[z][y] = result[id];
//                    }
//                }
//                for (int z = 0; z < X.length; z++) {
//                    newX[z][0] = newX[z][1];
//                    newX[z][X[0].length - 2] = newX[z][X[0].length - 1];
//                }
//            }
//            {
//                // calculate new T
//                int n = X.length * X[0].length;
//                final double[] a = new double[n];
//                final double[] b = new double[n];
//                final double[] c = new double[n];
//                final double[] d = new double[n];
//                for (int z = 0; z < X.length; z++) {
//                    for (int y = 0; y < X[z].length; y++) {
//                        int id = getIdCalcY(z, y, X);
//                        if (z == 0) {
//                            b[id] = 1;
//                            d[id] = s.Tw;
//                        } else {
//                            if (z == X.length - 1) {
//                                b[id] = 1;
//                                d[id] = s.T0;
//                            } else {
//                                if (y == 0 || y == X[0].length - 1) {
//                                    b[id] = 1;
//                                    d[id] = T[z][y];
//                                } else {
//                                    a[id] = c[id] = -s.kappa / s.dz / s.dz;
//                                    b[id] = 1.0 / s.dt + 2 * s.kappa / s.dz / s.dz + s.Q / s.C * Utils.wWithoutOneX(X[z][y], T[z][y], s);
//                                    d[id] = T[z][y] / s.dt + (T[z - 1][y] - 2 * T[z][y] + T[z + 1][y]) / s.dy / s.dy;
//                                }
//                            }
//                        }
//                    }
//                }
//                final double[] result = Utils.solveTridiagonal(a, b, c, d);
//                for (int z = 0; z < X.length; z++) {
//                    for (int y = 0; y < X[0].length; y++) {
//                        int id = getIdCalcY(z, y, X);
//                        newT[z][y] = result[id];
//                    }
//                }
//                for (int z = 0; z < X.length; z++) {
//                    newT[z][0] = newT[z][1];
//                    newT[z][X[0].length - 2] = newT[z][X[0].length - 1];
//                }
//            }
//        }

        X = newX;
        T = newT;
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
