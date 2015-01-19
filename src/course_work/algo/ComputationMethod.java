package course_work.algo;

/**
 * Created by antonkov on 1/18/2015.
 */
public interface ComputationMethod<T> {
    public void init(Settings settings);

    public Settings getSettings();
    public void makeStep();

    public T temperatures();
    public T concentration();
    public T reactionSpeed();
}
