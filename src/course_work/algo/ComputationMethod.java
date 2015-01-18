package course_work.algo;

/**
 * Created by antonkov on 1/18/2015.
 */
public interface ComputationMethod {
    public void init(Settings settings);

    public Settings getSettings();
    public void makeStep();

    public double[] temperatures();
    public double[] concentration();
    public double[] reactionSpeed();
}
