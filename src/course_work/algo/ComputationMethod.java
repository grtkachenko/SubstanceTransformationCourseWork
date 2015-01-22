package course_work.algo;

/**
 * Created by antonkov on 1/18/2015.
 */
public interface ComputationMethod<T> {
    // equations:    dX/dt -     D d2X/dz2 =      w(X, T)
    //               dT/dt - kappa d2T/dz2 = -Q/C w(X, T)
    //               w(X, T) = -K X^alpha exp(-E/RT)
    public void init(Settings settings);

    public Settings getSettings();
    public void makeStep();

    public T temperatures();
    public T concentration();
    public T reactionSpeed();
}
