package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.utils.CustomLabelBotton;
import co.edu.uptc.utils.CustomLabelTable;


public class TableProduct extends JDialog{
    private JFrame frame;
    private JPanel titlePanel = new JPanel(); 
    private JPanel tablePanel = new JPanel(); 
    private JPanel buttonsPanel = new JPanel(); 
    private CustomLabelTable table; 
    private CustomLabelBotton newProduct; 
    private CustomLabelBotton modifyProduct; 
    private CustomLabelBotton deleteProduct;
    private PresenterInterface presenter;

    public TableProduct(JFrame frame, PresenterInterface presenter){
        super(frame, true); 
        this.frame = frame; 
        this.presenter = presenter;
        initDialog();
        addComponents(); 
        setVisible(true);
    }

    private void initDialog(){
        setSize(new Dimension(850,650));
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());
    }

    private void addComponents(){
        addTitleComponents();
        addInputComponents();
        addButtonComponents();
        fillTable();
        add(titlePanel, BorderLayout.NORTH); 
        add(tablePanel, BorderLayout.CENTER); 
        add(buttonsPanel, BorderLayout.SOUTH); 
    }

    private void addTitleComponents(){
        title(); 
    }

    private void addInputComponents(){
        tablePeople();
    }

    private void addButtonComponents(){ 
        createPersonButton(); 
        modifyPersonButton(); 
        deletePersonButton(); 
    }

    public void title(){
        JLabel title = new JLabel("<html>" +
            "<div style='text-align: center; font-family: Serif; font-size: 14px; font-weight: bold;'>"+
            "Inventario de productos" +
            "</div>" +
            "</html>"
        ); 
        titlePanel.add(title); 
    }

    private void tablePeople(){
        String[] columns = {"Código", "Nombre", "Categoría", "Precio Unitario", "Cantidad disponible"}; 
        table = new CustomLabelTable(columns); 
        table.getScrollPane().setPreferredSize(new Dimension(700, 500));
        tablePanel.add(table.getScrollPane()); 
    }

    private void fillTable(){
        List<Product> product = presenter.getAllProduct();  
        if (product.isEmpty()) {
            table.addRow(new Object[]{"No hay productos", " registrados", "", ""});
        }else{
        product.stream()
        .map(p -> new Object[]{ p.getCode(), p.getName(), p.getCategory(), p.getPrice(), p.getAmount()})
        .forEach(row -> table.addRow(row));
        }
    }

    public void createPersonButton(){
        newProduct = new CustomLabelBotton("Nuevo"); 
        newProduct.onClick(e -> {
            showRegisterProduct();
        });
        buttonsPanel.add(newProduct.getButton()); 
    }

    public void modifyPersonButton(){
        modifyProduct = new CustomLabelBotton("Modificar"); 
        modifyProduct.onClick(e -> {
            showModifyProduct();
        });
        buttonsPanel.add(modifyProduct.getButton()); 
    }

    public void deletePersonButton(){
        deleteProduct = new CustomLabelBotton("Eliminar"); 
        deleteProduct.onClick(e -> {
            showDeleteProduct(); 
        });
        buttonsPanel.add(deleteProduct.getButton()); 
    }

    private void showModifyProduct(){
        ModifyProduct product = new ModifyProduct(frame, presenter); 
        table.clearRows();
        fillTable();
    }

    private void showRegisterProduct(){
        AddProduct product = new AddProduct(frame, presenter);  
        product.setVisible(true);
        table.clearRows();
        fillTable();
    }

    private void showDeleteProduct(){
        DeleteProduct product = new DeleteProduct(frame, presenter); 
        table.clearRows();
        fillTable();
    }

}
