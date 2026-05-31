package co.edu.uptc.utils;

import java.awt.Dimension;

import javax.swing.JComboBox;

public class CustomLabelJcombox extends BaseComponent<CustomLabelJcombox>{
    private JComboBox<String> jCombox; 

    public CustomLabelJcombox(String[] options) {
        super();
        createJcombxComponent(options);
    }

    private void createJcombxComponent(String[] options) {
        jCombox = new JComboBox<>(options);
        jCombox.setPreferredSize(new Dimension(200, 30));
        add(jCombox); 
        addErrorLabel(); 
    }

    public String getSelected() {
        return (String) jCombox.getSelectedItem();
    }

    public CustomLabelJcombox setSelected(String value) {
        jCombox.setSelectedItem(value);
        return this;
    }
    
    
}