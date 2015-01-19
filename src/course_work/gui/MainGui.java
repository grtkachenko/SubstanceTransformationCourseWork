package course_work.gui;

import course_work.Main;
import course_work.algo.Modifiable;
import course_work.algo.Settings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private JComboBox algoSelector;
    private boolean is1DimensionalState;
    private List<AnimatedPanel> currentAnimatedPanels = new ArrayList<>();
    private SingleDimensionDataSource[] singleDimensionDataSources;
    private DoubleDimensionDataSource[] doubleDimensionDataSources;
    private Settings settings;
    private boolean needToUpdateSources;
    private boolean isRunning = false;

    public MainGui() throws HeadlessException {
        this.settings = new Settings();
        this.singleDimensionDataSources = Main.getSingleDimensionDataSources(new Settings(settings), 0);
        this.doubleDimensionDataSources = Main.getDoubleDimensionDataSources(new Settings(settings), 0);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dimensionSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    refreshGraphsPanel(!is1DimensionalState);
                }
            }
        });
        algoSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                stopAnimations();
                updateSources();
                refreshGraphsPanel(is1DimensionalState);
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAnimations();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAnimations();
            }
        });
        setStartButtonEnabled(true);
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
        addParamFields();
        refreshGraphsPanel(true);
        setSize(new Dimension(500, 500));
    }

    private void stopAnimations() {
        if (isRunning) {
            isRunning = false;
            for (AnimatedPanel animatedPanel : currentAnimatedPanels) {
                animatedPanel.stopAnimation();
            }
            setStartButtonEnabled(true);
        }
    }

    private void startAnimations() {
        if (!isRunning) {
            updateSources();
            isRunning = true;
            for (AnimatedPanel animatedPanel : currentAnimatedPanels) {
                animatedPanel.animate((speedSlider.getValue() - 400) / 100d);
            }
            setStartButtonEnabled(false);
        }

    }

    private void updateSources() {
        if (needToUpdateSources) {
            needToUpdateSources = false;
            MainGui.this.singleDimensionDataSources = Main.getSingleDimensionDataSources(new Settings(settings), algoSelector.getSelectedIndex());
            MainGui.this.doubleDimensionDataSources = Main.getDoubleDimensionDataSources(new Settings(settings), algoSelector.getSelectedIndex());
            refreshGraphsPanel(is1DimensionalState);
        }
    }

    private void addParamFields() {
        Field[] fields = settings.getClass().getDeclaredFields();
        int it = 0;
        for (final Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Modifiable) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new FlowLayout());
                    final JTextArea fieldName = new JTextArea(field.getName());
                    fieldName.setEditable(false);
                    panel.add(fieldName);
                    final JTextField comp = new JTextField(10);
                    try {
                        field.setAccessible(true);
                        comp.setText(field.get(settings).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    comp.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            doUpdate();
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            doUpdate();
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            doUpdate();
                        }

                        private void doUpdate() {
                            if (callSetter(settings, field.getName(), comp.getText(), field.getType())) {
                                needToUpdateSources = true;
                            }
                        }
                    });
                    panel.add(comp);
                    addComponentToPanel(it++, paramsPanel, panel);
                }
            }
        }
    }

    private boolean callSetter(Object target, String valueName, String value, Class classValue) {
        try {
            if (value == null || value.isEmpty()) {
                return false;
            }
            valueName = Character.toUpperCase(valueName.charAt(0)) + valueName.substring(1);
            Method method = target.getClass().getDeclaredMethod("set" + valueName, classValue);
            if (classValue == double.class) {
                method.invoke(target, Double.parseDouble(value));
            } else {
                method.invoke(target, Integer.parseInt(value));
            }
            return true;
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    private void setStartButtonEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        stopButton.setEnabled(!enabled);
    }

    private void refreshGraphsPanel(boolean is1Dimensional) {
        stopAnimations();
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
            addComponentToPanel(it++, graphsLayout, animatedPanel);
        }

        invalidate();
        graphsLayout.repaint();
    }

    private void addComponentToPanel(int row, JPanel panel, JComponent component) {
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
        gbc.gridy = 2;
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
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(paramsPanel, gbc);
        speedSlider = new JSlider();
        speedSlider.setMaximum(1000);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(100);
        speedSlider.setValueIsAdjusting(true);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(speedSlider, gbc);
        algoSelector = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Явная схема");
        defaultComboBoxModel2.addElement("Частично неявная схема");
        defaultComboBoxModel2.addElement("Частично неявная схема + диагональ");
        defaultComboBoxModel2.addElement("Частично неявная схема с итерациями");
        algoSelector.setModel(defaultComboBoxModel2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(algoSelector, gbc);
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
