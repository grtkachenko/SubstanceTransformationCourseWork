package course_work.algo;

/**
 * Created by antonkov on 1/18/2015.
 */
public class Settings {

    public Settings() {
        initParams();
    }

    public Settings(Settings from) {
        this(from.h_steps, from.R, from.E, from.K, from.alpha, from.Q, from.C, from.rho, from.T0, from.Tw, from.lam, from.kappa, from.D, from.xLeft,
                from.xRight, from.dt, from.dz, from.dy, from.maxTime, from.l, from.h, from.time_steps, from.l_steps, from.countIterations, from.Tw, from.compTime);
    }

    private Settings(int h_steps, double r, double e, double k, double alpha, double q, double c,
                    double rho, double t0, double tm, double lam, double kappa, double d, double xLeft,
                    double xRight, double dt, double dz, double dy, double maxTime, double l, double h, int time_steps, int l_steps, int countIterations, double tw, int compTime) {
        this.h_steps = h_steps;
        R = r;
        E = e;
        K = k;
        this.alpha = alpha;
        Q = q;
        C = c;
        this.rho = rho;
        T0 = t0;
        Tw = tw;
        this.lam = lam;
        this.kappa = kappa;
        D = d;
        this.xLeft = xLeft;
        this.xRight = xRight;
        this.maxTime = maxTime;
        this.l = l;
        this.h = h;
        this.time_steps = time_steps;
        this.l_steps = l_steps;
        this.compTime = compTime;
        this.countIterations = countIterations;
        Tm = tm;

        initParams();
    }

    private void initParams() {
        this.dt = maxTime / time_steps;
        this.dz = l / l_steps;
        this.dy = h / h_steps;
    }

    @Modifiable
    double R = 8.314;
    @Modifiable
    double E = 8e+4;
    @Modifiable
    double K = 1.6e+6;
    @Modifiable
    double alpha = 1.0;
    double Q = 7e+5;
    double C = 1980.0;
    @Modifiable
    double rho = 830.0;
    double T0 = 293.0;
    double Tm = T0 + Q / C;
    @Modifiable
    double Tw = 610;
    double lam = 0.13;
    double kappa = lam / (rho * C);
    @Modifiable
    double D = kappa; // var
    double xLeft = 0.0;
    double xRight = 1.0;
    double maxTime = 2000;
    double l = 0.05;
    double h = 0.02;
    @Modifiable
    int time_steps = 6000;
    @Modifiable
    int l_steps = 50;
    @Modifiable
    int h_steps = 20;
    @Modifiable
    int compTime = 20;
    @Modifiable
    int countIterations = 5;

    double dt;
    double dz;
    double dy;

    public int getCompTime() {
        return compTime;
    }

    public void setCompTime(int compTime) {
        this.compTime = compTime;
    }

    public void setR(double r) {
        R = r;
    }

    public void setE(double e) {
        E = e;
    }

    public void setK(double k) {
        K = k;
    }

    public void setAlpha(double alpha) {
        System.out.println("Set alpha to " + alpha);
        this.alpha = alpha;
    }

    public void setQ(double q) {
        Q = q;
    }

    public void setC(double c) {
        C = c;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public void setT0(double t0) {
        T0 = t0;
    }

    public void setTw(double tw) {
        Tw = tw;
    }

    public void setLam(double lam) {
        this.lam = lam;
    }

    public void setKappa(double kappa) {
        this.kappa = kappa;
    }

    public void setD(double d) {
        D = d;
    }

    public void setxLeft(double xLeft) {
        this.xLeft = xLeft;
    }

    public void setxRight(double xRight) {
        this.xRight = xRight;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public void setDz(double dz) {
        this.dz = dz;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public void setL(double l) {
        this.l = l;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setTime_steps(int time_steps) {
        this.time_steps = time_steps;
    }

    public void setL_steps(int l_steps) {
        this.l_steps = l_steps;
    }

    public void setH_steps(int h_steps) {
        this.h_steps = h_steps;
    }
}
