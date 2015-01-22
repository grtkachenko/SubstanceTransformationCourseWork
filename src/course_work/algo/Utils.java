package course_work.algo;

import java.util.Arrays;

/**
 * Created by Borys Minaiev on 19.01.2015.
 */
public class Utils {
    public static double w(double x, double t, final Settings s) {
        return -s.K * Math.pow(x, s.alpha) * Math.exp(-s.E / (s.R * t));
    }

    public static double wWithoutOneX(double x, double t, final Settings s) {
        return -s.K * Math.pow(x, s.alpha - 1) * Math.exp(-s.E / (s.R * t));
        /*double val = 1;
        if (Math.abs(s.alpha - 1) > 1e-7) {
            val = Math.pow(x, s.alpha - 1);
        }
        return -s.K * val * Math.exp(-s.E / (s.R * t));*/
    }

    public static double[] solveTridiagonal(double[] a, double[] b, double[] c, double[] d) {
        final int n = a.length;
        final double[] res = new double[n];
        for (int i = 0; i + 1 < n; i++) {
            final double mul = a[i + 1] / b[i];
            d[i + 1] -= d[i] * mul;
            b[i + 1] -= c[i] * mul;
        }
        for (int i = n - 1; i > 0; i--) {
            final double mul = c[i - 1] / b[i];
            d[i - 1] -= d[i] * mul;
        }
        for (int i = 0; i < n; i++) {
            res[i] = d[i] / b[i];
        }
        return res;
    }

    public static double[][][] solveTridiagonal(double[][][] a, double[][][] b, double[][][] c, double[][][] d) {
        final int n = a.length;
        final double[][][] res = new double[n][][];
        for (int i = 0; i + 1 < n; i++) {
            final double mul[][] = mult(getReversed(b[i]), a[i + 1]);
            subtract(b[i + 1], mult(c[i], mul));
            subtract(d[i + 1], mult(d[i], mul));
        }
        for (int i = n - 1; i > 0; i--) {
            final double mul[][] = mult(getReversed(b[i]), c[i - 1]);
            subtract(d[i - 1], mult(d[i], mul));
        }
        for (int i = 0; i < n; i++) {
            res[i] = mult(d[i], getReversed(b[i]));
        }
        return res;
    }

    public static double[][] getReversed(double[][] a) {
        final double [][] res = new double[2][2];
        final double mul = 1.0 / (a[0][0] * a[1][1] - a[1][0] * a[0][1]);
        res[0][0] = a[1][1] * mul;
        res[0][1] = -a[0][1] * mul;
        res[1][0] = -a[1][0] * mul;
        res[1][1] = a[0][0] * mul;
        return res;
    }

    public static double[][] mult(double[][] a, double[][] b) {
        final double[][] res = new double[a.length][b[0].length];
        for(int i = 0; i < a.length; i++) {
            for(int j = 0; j < b.length; j++) {
                for(int k = 0; k < b[0].length; k++) {
                    res[i][k] += a[i][j] * b[j][k];
                }
            }
        }
        return res;
    }

    public static void subtract(double[][] a, double[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] -= b[i][j];
            }
        }
    }
}
