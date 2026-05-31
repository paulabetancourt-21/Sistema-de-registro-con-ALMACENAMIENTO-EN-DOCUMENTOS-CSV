package co.edu.uptc.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;

public class MenuGui extends JFrame implements ViewInterface{
    private PresenterInterface presenter;
    private JMenuBar jMenuBar; 
    private static MenuGui instance; 

    private MenuGui() {
        initFrame(); 
        addComponents();
        setVisible(true);
    }

    public static MenuGui getInstance(){
        if (instance == null) {
            instance = new MenuGui(); 
        }
        return instance; 
    }

    @Override
    public void initFrame(){
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(null);
    }

    @Override
    public void addComponents(){
        addMenuBar();
        addMenuRegister();
        addMenuHelp();
    }
    
    private void addMenuBar() {
        jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
    }

    private void addMenuRegister(){
        JMenu jMenu = new JMenu("Gestión");
        addIntemPeople(jMenu);
        addIntemProducts(jMenu); 
        jMenuBar.add(jMenu);
    }

    private void addMenuHelp(){
        JMenu jMenu = new JMenu("Ayuda");
        addIntemAbout(jMenu);
        jMenuBar.add(jMenu);
    }

    private void addIntemAbout(JMenu jMenu){
        JMenuItem jMenuItem = new JMenuItem("Acerca de");
        jMenuItem.addActionListener(e -> {
            showAboutWindow();
        }); 
        jMenu.add(jMenuItem);
    }

    private void showAboutWindow(){
        AboutWindow window = new AboutWindow(this); 
    }


    private void addIntemPeople(JMenu jMenu){
        JMenuItem jMenuItem = new JMenuItem("Gestión de personas");
        jMenuItem.addActionListener(e -> {
            showTableRegisterPeople();
        }); 
        jMenu.add(jMenuItem);
    }

    private void addIntemProducts(JMenu jMenu){
        JMenuItem jMenuItem = new JMenuItem("Gestión de productos");
        jMenuItem.addActionListener(e -> {
            showTableRegisterProduct(); 
        }); 
        jMenu.add(jMenuItem);
    }

    private void showTableRegisterPeople(){
        TablePeople people = new TablePeople(this, presenter);
    }

    private void showTableRegisterProduct(){
        TableProduct product = new TableProduct(this, presenter);
    }

    @Override
    public void setPresenter(PresenterInterface presenter) {
        this.presenter = presenter; 
    }



}