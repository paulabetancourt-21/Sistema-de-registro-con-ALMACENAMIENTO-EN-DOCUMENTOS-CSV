package co.edu.uptc.utils;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class CustomLabelRadioButton extends BaseComponent<CustomLabelRadioButton>{
    private ButtonGroup group;
    private JPanel panel;

    public CustomLabelRadioButton() {
        super();
        group = new ButtonGroup(); 
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(250, 30));
        add(panel);
        addErrorLabel(); 
    }


    public CustomLabelRadioButton addOption(String text) {
        JRadioButton radio = new JRadioButton(text);
        group.add(radio);
        panel.add(radio);
        return this;
    }

    public String getSelected() {
        for (Component c : panel.getComponents()) {
            if (c instanceof JRadioButton rb && rb.isSelected()) {
                return rb.getText();
            }
        }
        return null;
    }

    public CustomLabelRadioButton setSelected(String value) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JRadioButton rb && rb.getText().equals(value)) {
                rb.setSelected(true);
                return this;
            }
        }
        return this;
    }

}
