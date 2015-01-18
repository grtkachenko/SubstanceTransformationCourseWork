package course_work.gui;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class GUIHelper {
    private static GUIHelper ourInstance = new GUIHelper();
    private MainGui frame;
    public static GUIHelper getInstance() {
        return ourInstance;
    }

    private GUIHelper() {
        frame = new MainGui();
    }

    public void showGui() {
        frame.show();
    }

}
