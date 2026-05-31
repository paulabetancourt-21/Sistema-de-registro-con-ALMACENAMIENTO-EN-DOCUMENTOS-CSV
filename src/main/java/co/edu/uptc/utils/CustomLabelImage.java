package co.edu.uptc.utils;

import javax.swing.*;
import java.awt.*;

public class CustomLabelImage extends BaseComponent<CustomLabelImage>{

    private JLabel imageLabel;

    public CustomLabelImage(String path) {
        super();
        createImage(path);
    }

    private void createImage(String path) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        imageLabel = new JLabel(icon);
        add(imageLabel);
    }

    public CustomLabelImage setImageSize(int width, int height) {
        ImageIcon icon = (ImageIcon) imageLabel.getIcon();
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        return this;
    }
}