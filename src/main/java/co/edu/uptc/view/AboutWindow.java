package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import co.edu.uptc.utils.CustomLabelBotton;
import co.edu.uptc.utils.CustomLabelImage;

public class AboutWindow extends JDialog{
    private JFrame frame = MenuGui.getInstance(); 

    public AboutWindow(JFrame frame){
        super(frame, true); 
        this.frame = frame; 
        initDialog();
        addComponents(); 
        setVisible(true);
    }

    private void initDialog(){
        setSize(new Dimension(350, 450));
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());
    }

    public void logo(){
        CustomLabelImage image = new CustomLabelImage("/uptc.png")
        .setImageSize(300, 130);
        image.setPreferredSize(new Dimension(250, 180));
        add(image, BorderLayout.NORTH);
    }

    private void addComponents(){
        logo();
        info();
        closeButton();
    }

    public void info(){
        JPanel panel = new JPanel(new FlowLayout());
        JLabel label = new JLabel(
        "<html>" +
            "<div style='text-align: center; font-family: Times New Roman; font-weight: normal;'>" +
            "<b style='font-size: 14px;'>GestorApp</b><br><br>" +
            "Versión: 1.0<br>" +
            "Autor: Paula Andrea Betancourt Matamoros<br>" +
            "Código: 202422429<br>" +
            "Asignatura: Programación II<br>" +
            "Universidad Pedagógica y Tecnológica de Colombia - UPTC<br><br>" +
            "<span style='font-size: 11px;'>Aplicación para la gestión de personas y productos.<br>" +
            "Permite registrar, modificar y eliminar registros,<br>" +
            "con validaciones y manejo de excepciones.</span>" +
            "</div>" +
            "</html>");
        panel.add(label);
        add(panel, BorderLayout.CENTER);
    }

    public void closeButton(){
        JPanel panel = new JPanel();
        CustomLabelBotton close = new CustomLabelBotton("Cerrar");
        close.onClick(e -> setVisible(false));
        panel.add(close.getButton());
        add(panel, BorderLayout.SOUTH);
    }
}