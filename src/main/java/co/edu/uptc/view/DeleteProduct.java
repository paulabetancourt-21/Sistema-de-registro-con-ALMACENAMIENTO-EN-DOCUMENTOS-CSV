package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import co.edu.uptc.exceptions.ErrorTranslator;
import co.edu.uptc.exceptions.TypingErrors;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.utils.CustomLabelBotton;
import co.edu.uptc.utils.CustomLabelTextField;

public class DeleteProduct extends JDialog{
    private JFrame frame;
    private PresenterInterface presenter; 
    private CustomLabelBotton searchPerson;
    private CustomLabelTextField codeInput; 
    private JPanel panelTitle = new JPanel();
    private JPanel panelSearch = new JPanel(); 
    private JPanel panelButton = new JPanel(); 


    public DeleteProduct(JFrame frame, PresenterInterface presenter){
        super(frame, true); 
        this.frame = frame; 
        this.presenter = presenter;
        initDialog();
        addComponents(); 
        setVisible(true);
    }

    public void initDialog(){
        setSize(new Dimension(400,280));
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());
    }

    public void addComponents(){
        addTitleComponents();
        addInputComponents();
        addButtonComponents();
        add(panelTitle, BorderLayout.NORTH); 
        add(panelSearch, BorderLayout.CENTER); 
        add(panelButton, BorderLayout.SOUTH); 
    }

    private void addTitleComponents(){
        title();
    }

    private void addInputComponents(){
        code(); 
    }

    private void addButtonComponents(){ 
        searchPersonButton(); 
    }

    public void title(){
        JLabel title = new JLabel("<html>" +
            "<div style='text-align: center; font-family: Serif; font-size: 12px; font-weight: bold;'>"+
            "Eliminar registro" +
            "</div>" +
            "</html>"
        ); 
        panelTitle.add(title); 
    }

    public void code(){
        codeInput = new CustomLabelTextField()
        .setText("Código: ")
        .setInputDimension(new Dimension(250,35)); 
        panelSearch.add(codeInput); 
    }

    public String returnCode(){
        return codeInput.getInput(); 
    }

    public void searchPersonButton() {
        searchPerson = new CustomLabelBotton("Buscar");
        searchPerson.onClick(e -> {
            searchProduct();
        });
        panelButton.add(searchPerson.getButton());
    }

    public void searchProduct(){
        Product product = presenter.searchProduct(returnCode()); 
        if (product != null) {
            deleteProduct(product);
        }else {
            ErrorTranslator translator = new ErrorTranslator();
            String message = translator.translate(TypingErrors.PRODUCT_NOT_FOUND);
            codeInput.setError(message);
        }
    }

    private void deleteProduct(Product product ){
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Desea eliminar el producto \"" + product.getName() + "\" (Código: " + product.getCode() + ")?\nEsta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            presenter.removeProduct(returnCode());
            JOptionPane.showMessageDialog(this,"Registro eliminado existosamente"); 
            setVisible(false);
        }
    }
}
