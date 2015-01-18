package course_work.gui;

import course_work.Main;
import course_work.algo.Settings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * User: gtkachenko
 * Date: 17/01/15
 */
public class MainGui extends JFrame {
    private JPanel rootPanel;
    private JButton startButton;
    private JButton stopButton;
    private JComboBox dimensionSelector;
    private JPanel paramsPanel;
    private JPanel graphsLayout;
    private JSlider speedSlider;
    private boolean is1DimensionalState;
    private List<AnimatedPanel> currentAnimatedPanels = new ArrayList<>();
    private SingleDimensionDataSource[] singleDimensionDataSources;
    private DoubleDimensionDataSource[] doubleDimensionDataSources;

    public MainGui() throws HeadlessException {
        Settings settings = new Settings();
        this.singleDimensionDataSources = Main.getSingleDimensionDataSources(settings);
        this.doubleDimensionDataSources = Main.getDoubleDimensionDataSources();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dimensionSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setUIState(!is1DimensionalState);
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (AnimatedPanel animatedPanel : currentAnimatedPanels) {
                    animatedPanel.animate();
                }
                setStartButtonEnabled(false);
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (AnimatedPanel animatedPanel : currentAnimatedPanels) {
                    animatedPanel.stopAnimation();
                }
                setStartButtonEnabled(true);
            }
        });
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("-4"));
        labelTable.put(speedSlider.getMaximum() / 2 - 100, new JLabel("0"));
        labelTable.put(speedSlider.getMaximum() / 2, new JLabel("1"));
        labelTable.put(speedSlider.getMaximum(), new JLabel("6"));
        speedSlider.setLabelTable(labelTable);
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                for (AnimatedPanel animatedPanel : currentAnimatedPanels) {
                    animatedPanel.setAnimationSpeed((speedSlider.getValue() - 400) / 100d);
                }
            }
        });
        setContentPane(rootPanel);
        for (int i = 0; i < 3; i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());
            panel.add(new JTextArea("a = "));
            panel.add(new JTextField(10));
            addComponent(i, paramsPanel, panel);
        }
        setUIState(true);
        setSize(new Dimension(500, 500));
    }

    private void setStartButtonEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        stopButton.setEnabled(!enabled);
    }

    private void setUIState(boolean is1Dimensional) {
        for (AnimatedPanel animatedPanel : currentAnimatedPanels) {
            animatedPanel.stopAnimation();

        }
        speedSlider.setValue(speedSlider.getMaximum() / 2);
        setStartButtonEnabled(true);

        graphsLayout.removeAll();
        currentAnimatedPanels.clear();
        graphsLayout.revalidate();
        graphsLayout.repaint();
        is1DimensionalState = is1Dimensional;
        if (!is1DimensionalState) {
            for (DoubleDimensionDataSource doubleDimensionDataSource : doubleDimensionDataSources) {
                currentAnimatedPanels.add(new DoubleDimensionPanel(doubleDimensionDataSource));
            }
        } else {
            for (SingleDimensionDataSource singleDimensionDataSource : singleDimensionDataSources) {
                currentAnimatedPanels.add(new Plot(singleDimensionDataSource));
            }
        }
        int it = 0;
        for (AnimatedPanel animatedPanel : currentAnimatedPanels) {
            addComponent(it++, graphsLayout, animatedPanel);
        }

        invalidate();
        graphsLayout.repaint();
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
        dimensionSelector = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("1-dimensional");
        defaultComboBoxModel1.addElement("2-dimensional");
        dimensionSelector.setModel(defaultComboBoxModel1);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(dimensionSelector, gbc);
        paramsPanel = new JPanel();
        paramsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(paramsPanel, gbc);
        speedSlider = new JSlider();
        speedSlider.setMaximum(1000);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(500);
        speedSlider.setValueIsAdjusting(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(speedSlider, gbc);
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
