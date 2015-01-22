package course_work.algo;

import java.util.HashMap;
import java.util.Map;

/**
 * User: gtkachenko
 * Date: 19/01/15
 */
public class MethodMap {
    public static Map<Integer, ComputationMethod<double[]>> methods1D = new HashMap<>();
    public static Map<Integer, ComputationMethod<double[][]>> methods2D = new HashMap<>();

    static {
        methods1D.put(0, new EulerExplicitForwardMethod());
        methods1D.put(1, new EulerImplicitForwardMethod());
        methods1D.put(2, new EulerExplicitForwardMethod());
        methods1D.put(3, new EulerExplicitForwardMethod());

        methods2D.put(0, new EulerExplicitForwardMethod2D());
        methods2D.put(1, new EulerImplicitForwardMethod2D());
        methods2D.put(2, new EulerExplicitForwardMethod2D());
        methods2D.put(3, new EulerExplicitForwardMethod2D());
    }
}
