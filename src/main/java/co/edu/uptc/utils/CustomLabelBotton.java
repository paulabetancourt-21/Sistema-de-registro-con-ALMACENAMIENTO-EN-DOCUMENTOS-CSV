package co.edu.uptc.utils;

import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CustomLabelBotton{
    private JButton button;
    
    public CustomLabelBotton(String text) {
        button = new JButton(text);
    }

    public CustomLabelBotton onClick(ActionListener action) {
        button.addActionListener(action);
        return this;
    }

    public JButton getButton() {
        return button;
    }
}
