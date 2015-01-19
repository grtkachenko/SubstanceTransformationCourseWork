package course_work.algo;

import java.util.HashMap;
import java.util.Map;

/**
 * User: gtkachenko
 * Date: 19/01/15
 */
public class MethodMap {
    public static Map<Integer, Class> methodMap = new HashMap<>();

    static {
        methodMap.put(0, EulerExplicitForwardMethod.class);
    }
}
