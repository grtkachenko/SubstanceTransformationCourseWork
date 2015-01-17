package gui;

import javax.swing.*;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class GUIHelper {
    private static GUIHelper ourInstance = new GUIHelper();
    private JFrame frame;
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
