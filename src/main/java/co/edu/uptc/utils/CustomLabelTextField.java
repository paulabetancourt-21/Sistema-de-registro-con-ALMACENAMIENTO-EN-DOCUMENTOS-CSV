package co.edu.uptc.utils;

import java.awt.Dimension;
import javax.swing.JTextField;

public class CustomLabelTextField extends BaseComponent<CustomLabelTextField>{
    private JTextField textField;

    public CustomLabelTextField() {
        super();
        addTextField();
        addErrorLabel();
    }

    private void addTextField() {
        textField = new JTextField();
        add(textField,1);
    }

    public CustomLabelTextField setInput(String text) {
        textField.setText(text);
        return this;
    }

    public CustomLabelTextField setInputDimension(Dimension d) {
        textField.setPreferredSize(d);
        return this;
    }

    public String getInput() {
        return textField.getText();
    }

}
