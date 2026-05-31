package co.edu.uptc.utils; 

import javax.swing.*;
import java.awt.*;

public class BaseComponent<T> extends JPanel {
    protected JLabel label;
    protected String labelInfo = "";
    protected JLabel errorLabel; 
    protected int whith = 30;
    private Dimension dimensionPanel = new Dimension(380, 70);
    private int  widthLabel = 100;

    public BaseComponent() {
        setPreferredSize(dimensionPanel);
        addComponents();
    }

    private void addComponents() {
        addLabel();
    }

    private void addLabel() {
        label = new JLabel(labelInfo);
        errorLabel = new JLabel(); 
        label.setPreferredSize(new Dimension(widthLabel,whith));
        errorLabel.setBorder(BorderFactory.createEmptyBorder(0, widthLabel, 0, 0));
        errorLabel.setPreferredSize(new Dimension(dimensionPanel.width,25));
        add(label);
    }

    public void addErrorLabel() {
        add(errorLabel);
    }

    public T setText(String value) {
        this.labelInfo = value;
        label.setText(value);
        return (T) this;
    }


    public T setDimension(Dimension d) {
        this.dimensionPanel = d;
        setPreferredSize(d);
        return (T) this;
    }

    
    public T setWidthLabel(int d) {
        this.widthLabel = d;
        label.setPreferredSize(new Dimension(d,whith));
        return (T) this;
    }


    public T setError(String message) {
        errorLabel.setText(message);
        errorLabel.setToolTipText(message);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 10));
        errorLabel.setBorder(BorderFactory.createEmptyBorder(0, widthLabel, 0, 0));
        errorLabel.setVisible(!message.isEmpty());
        errorLabel.setPreferredSize(new Dimension(dimensionPanel.width,25));
        errorLabel.setForeground(new Color(255, 0, 0)); 
        return (T) this;
    }

    public String getErrorText() {
        return errorLabel.getText();
    }
    
}