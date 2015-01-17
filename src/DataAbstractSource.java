/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public interface DataAbstractSource {
    int[][] getValues2D(long time);
    int[] getValues1d(long time);
}
