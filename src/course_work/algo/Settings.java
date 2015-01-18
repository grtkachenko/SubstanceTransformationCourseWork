package course_work.algo;

/**
 * Created by antonkov on 1/18/2015.
 */
public class Settings {
    double R = 8.314;
    double E = 8e+4;
    double K = 1.6e+6;
    double alpha = 1.0 / 6;
    double Q = 7e+5;
    double C = 1980.0;
    double rho = 830.0;
    double T0 = 293.0;
    double Tm = T0 + Q / C;
    double lam = 0.13;
    double kappa = lam / (rho * C);
    double D = kappa; // var
    double xLeft = 0.0;
    double xRight = 1.0;

    double dt = 1.0;
    double dz = 0.001;
    double maxTime = 2000;
    double l = 0.05;
    int time_steps = (int) (maxTime / dt);
    int space_steps = (int) (l / dz);
}
