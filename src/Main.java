import gui.GUIHelper;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUIHelper.getInstance().showGui();
            }
        });
    }
}
