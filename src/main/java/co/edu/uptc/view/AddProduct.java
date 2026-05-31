package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import co.edu.uptc.exceptions.ErrorTranslator;
import co.edu.uptc.exceptions.Result;
import co.edu.uptc.exceptions.TypingErrors;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;
import co.edu.uptc.utils.CustomLabelBotton;
import co.edu.uptc.utils.CustomLabelTextField;

public class AddProduct extends JDialog{
    private JFrame frame; 
    private PresenterInterface presenter;
    private JPanel panelInput = new JPanel(new FlowLayout()); 
    private JPanel panelButton = new JPanel(); 
    private JPanel panelTitle = new JPanel();
    private CustomLabelTextField codeInput; 
    private CustomLabelTextField nameInput;
    private CustomLabelTextField descriptionInput;
    private CustomLabelTextField categoryInput;
    private CustomLabelTextField unitPriceInput;
    private CustomLabelTextField quantityInput;
    private CustomLabelBotton saveProduct; 
    private CustomLabelBotton cancelButton;
    private boolean editingMode = false;
    private Product originalProduct = null;
    private Result<Product> result;
    
    public AddProduct(JFrame frame, PresenterInterface presenter){
        super(frame, true); 
        this.frame = frame; 
        this.presenter = presenter;
        initDialog();
        addComponents(); 
    }

    private void initDialog(){
        setSize(new Dimension(450,650));
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());
    }

    private void addComponents(){
        addTitleComponents();
        addInputComponents();
        addButtonComponents();
        add(panelTitle, BorderLayout.NORTH); 
        add(panelInput, BorderLayout.CENTER); 
        add(panelButton, BorderLayout.SOUTH); 
    }

    private void addTitleComponents(){
        title();
    }

    private void addInputComponents(){
        code();
        name();
        description(); 
        category(); 
        unitPrice(); 
        availableQuantity(); 
    }

    private void addButtonComponents(){
        createProductButton();
        cancelButton();
    }

    public void loadData(Product product) {
        this.editingMode = true;
        this.originalProduct = product;
        codeInput.setInput(product.getCode()); 
        nameInput.setInput(product.getName());
        descriptionInput.setInput(product.getDescription());
        categoryInput.setInput(product.getCategory());
        unitPriceInput.setInput(String.valueOf(product.getPrice()));
        quantityInput.setInput(String.valueOf(product.getAmount()));
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        cancelButton.getButton().setEnabled(false);
    }

    public void title(){
        JLabel title = new JLabel("<html>" +
            "<div style='text-align: center; font-family: Serif; font-size: 14px; font-weight: bold;'>"+
            "Registro de productos" +
            "</div>" +
            "</html>"
        ); 
        panelTitle.add(title); 
    }

    public void code(){
        codeInput = new CustomLabelTextField()
        .setText("Código: ")
        .setInputDimension(new Dimension(250,35)); 
        panelInput.add(codeInput); 
    }

    public String returnCode(){
        return codeInput.getInput(); 
    }

    public void name(){
        nameInput = new CustomLabelTextField()
        .setText("Nombre: ")
        .setInputDimension(new Dimension(250,35)); 
        panelInput.add(nameInput); 
    }

    public String returnName(){
        return nameInput.getInput(); 
    }

    public void description(){
        descriptionInput = new CustomLabelTextField()
        .setText("Descripción: ")
        .setInputDimension(new Dimension(250, 35)); 
        panelInput.add(descriptionInput); 
    }

    public String returnDescription(){
        return descriptionInput.getInput(); 
    }

    public void category(){
        categoryInput = new CustomLabelTextField()
        .setText("Categoria: ")
        .setInputDimension(new Dimension(250,35)); 
        panelInput.add(categoryInput); 
    }

    public String returnCategory(){
        return categoryInput.getInput(); 
    }

    public void unitPrice(){
        unitPriceInput = new CustomLabelTextField()
        .setText("Precio Unitario: ")
        .setInputDimension(new Dimension(250,35)); 
        panelInput.add(unitPriceInput); 
    }

    public Double returnUnitPrice(){
        String input = unitPriceInput.getInput().trim();
        if (input.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void availableQuantity(){
        quantityInput = new CustomLabelTextField()
        .setText("Cantidad disponible:  ")
        .setInputDimension(new Dimension(250,35)); 
        panelInput.add(quantityInput); 
    }

    public int returnQuantity(){
        String input = quantityInput.getInput().trim();
        if (input.isEmpty()) return -1;
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void createProductButton() {
        saveProduct = new CustomLabelBotton("Guardar");
        saveProduct.onClick(e -> {
            verfifyErrors();
        });
        panelButton.add(saveProduct.getButton());
    }

    public void cancelButton() {
        cancelButton = new CustomLabelBotton("Cancelar");
        cancelButton.onClick(e -> {setVisible(false);});
        panelButton.add(cancelButton.getButton());
    }

    public void verfifyErrors(){
        updateErrors();
        Product product = new Product(returnCode(), returnName(), returnDescription(), returnCategory(), returnUnitPrice(), returnQuantity());
        if (editingMode) {
            handleEditMode(product);
        } else {
            handleCreateMode(product);
        }
        if (result != null && !result.isSuccess()) {
            showErrors();
        }
    }

    private void handleEditMode(Product product){
        String code = originalProduct.getCode();
        presenter.removeProduct(code);
        result = presenter.addProduct(product); 
        if (result.isSuccess()) setVisible(false);
    }

    private void handleCreateMode(Product product){
        result = presenter.addProduct(product); 
        if (result.isSuccess()) setVisible(false);
    }

    public void updateErrors(){
        codeInput.setError("");
        nameInput.setError("");
        descriptionInput.setError("");
        categoryInput.setError(""); 
        unitPriceInput.setError("");
        quantityInput.setError("");
    } 

    private void showErrors(){
        ErrorTranslator translator = new ErrorTranslator();
        for (TypingErrors error : result.getErrors()) {
            String message = translator.translate(error);
            switch (error) {
                //ERRORES DE PERSONA
                case NAME_MIN_LENGTH, LASTNAME_MIN_LENGTH, NAME_EMPTY,LAST_NAME_EMPTY,INVALID_BIRTH_DATE,BIRTHDATE_BLANK,INVALID_EMAIL,EMAIL_MAX_LENGHT,EMAIL_EMPTY,INVALID_IDENTITY_CARD,INVALID_ID_CARD,INVALID_PASSPORT,INVALID_FOREIGN_ID,INVALID_OTHER_DOCUMENT,GENDER_BLANK,DUPLICATE_EMAIL, DUPLICATE_DOCUMENT, PERSON_NOT_FOUND, PRODUCT_NOT_FOUND-> {}
                //Errores de producto
                case CODE_ALPHANUMERIC_REQUIRED, CODE_INVALID_LENGTH,CODE_BLANK, DUPLICATE_CODE -> codeInput.setError(message); 
                case CODE_NAME_MIN_LENGTH,CODE_NAME_MAX_LENGTH,CODE_NAME_BLANK -> nameInput.setError(message); 
                case DESCRIPTION_MAX_LENGTH -> descriptionInput.setError(message); 
                case CATEGORY_MIN_LENGTH,CATEGORY_BLANK -> categoryInput.setError(message);  
                case PRICE_MUST_BE_GREATER_THAN_ZERO, PRICE_INVALID_DECIMALS -> unitPriceInput.setError(message); 
                case QUANTITY_MUST_BE_POSITIVE -> quantityInput.setError(message); 
            }
        }
    }


}
