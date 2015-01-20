package course_work.algo;

/**
 * Created by Borys Minaiev on 19.01.2015.
 */
public class Utils {
    public static double w(double x, double t, final Settings s) {
        return -s.K * Math.pow(x, s.alpha) * Math.exp(-s.E / (s.R * t));
    }

    public static double wWithoutOneX(double x, double t, final Settings s) {
//        if (x < 1e-7) {
//            return -s.K * Math.exp(-s.E / (s.R * t));
//        }
        return -s.K * Math.pow(x, s.alpha - 1) * Math.exp(-s.E / (s.R * t));
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
}
