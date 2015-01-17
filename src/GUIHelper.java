import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: gtkachenko
 * Date: 17/01/15
 * Time: 19:00
 */
public class GUIHelper {
    private static GUIHelper ourInstance = new GUIHelper();

    private JFrame frame;
    private JPanel rightPanel;
    private JPanel leftPanel;


    public static GUIHelper getInstance() {
        return ourInstance;
    }

    private GUIHelper() {
        frame = new MainGui();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JCheckBox button = new JCheckBox("1-dimensional case");
//        leftPanel = new JPanel();
//        rightPanel = new JPanel();
//        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.LINE_AXIS));
//        rightPanel.add(button);
//
//        GradientTest applet = new GradientTest();
//        frame.add(rightPanel, BorderLayout.EAST);
//        frame.add(applet, BorderLayout.CENTER);
//        frame.setSize(new Dimension(500, 500));
//        applet.animate();

    }

    public void showGui() {
        frame.show();
    }


}
