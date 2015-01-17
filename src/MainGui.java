import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: gtkachenko
 * Date: 17/01/15
 * Time: 19:31
 */
public class MainGui extends JFrame {
    private JPanel rootPanel;
    private JButton startButton;
    private JButton stopButton;
    private JComboBox comboBox1;
    private JPanel paramsPanel;
    private JPanel graphsLayout;

    public MainGui() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        for (int i = 0; i < 3; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            panel.add(new JTextArea("a = "));
            panel.add(new JTextField(10));
            addComponent(i, paramsPanel, panel);
        }
        GradientTest[] gradientTests = new GradientTest[3];
        for (int i = 0; i < 3; i++) {
            gradientTests[i] = new GradientTest();
            addComponent(i, graphsLayout, gradientTests[i]);
        }
        for (int i = 0; i < 3; i++) {
            gradientTests[i].animate();
        }

        setSize(new Dimension(500, 500));
    }

    private void addComponent(int row, JPanel panel, JComponent component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(component, gbc);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setAlignmentX(0.0f);
        panel1.setAlignmentY(0.0f);
        panel1.setAutoscrolls(true);
        rootPanel.add(panel1, BorderLayout.EAST);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        startButton = new JButton();
        startButton.setText("Start");
        panel2.add(startButton);
        stopButton = new JButton();
        stopButton.setText("Stop");
        panel2.add(stopButton);
        comboBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("1-dimensional");
        defaultComboBoxModel1.addElement("2-dimensional");
        comboBox1.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(comboBox1, gbc);
        paramsPanel = new JPanel();
        paramsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(paramsPanel, gbc);
        graphsLayout = new JPanel();
        graphsLayout.setLayout(new GridBagLayout());
        rootPanel.add(graphsLayout, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
