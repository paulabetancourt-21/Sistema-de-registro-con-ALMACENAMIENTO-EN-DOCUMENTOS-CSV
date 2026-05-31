package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import co.edu.uptc.exceptions.*;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.utils.*;

public class AddPeople extends JDialog{
    private JFrame frame; 
    private PresenterInterface presenter;
    private JPanel panelInput = new JPanel(new FlowLayout()); 
    private JPanel panelButton = new JPanel(); 
    private JPanel panelTitle = new JPanel(); 
    private CustomLabelTextField nameInput; 
    private CustomLabelTextField lastNameInput; 
    private CustomLabelRadioButton genderRadio; 
    private CustomLabelJcombox documentTypeJcombox; 
    private CustomLabelCalendar birthDayInput; 
    private CustomLabelBotton savePerson; 
    private CustomLabelBotton cancelButton; 
    private CustomLabelTextField emailInput;
    private CustomLabelTextField documentNumberInput; 
    private Result<Person> result; 
    private boolean editingMode = false;
    private Person originalPerson = null;
    
    public AddPeople(JFrame frame, PresenterInterface presenter){
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
        name();
        lastname();
        gender();
        documentType();
        birthdate();
        documentNumber();
        mail();
    }

    private void addButtonComponents(){
        createPersonButton();
        cancelButton(); 
    }

    public void loadData(Person person) {
        this.editingMode = true;
        this.originalPerson = person;
        nameInput.setInput(person.getName());
        lastNameInput.setInput(person.getLastName());
        genderRadio.setSelected(person.getGender());
        documentTypeJcombox.setSelected(person.getDocumentType());
        birthDayInput.setDate(person.getBirthDate());
        documentNumberInput.setInput(person.getNumberDocument()); 
        emailInput.setInput(person.getEmail());
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        cancelButton.getButton().setEnabled(false);
    }

    public void title(){
        JLabel title = new JLabel("<html>" +
            "<div style='text-align: center; font-family: Serif; font-size: 14px; font-weight: bold;'>"+
            "Registro de personas" +
            "</div>" +
            "</html>"
        ); 
        panelTitle.add(title); 
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

    public void lastname(){
        lastNameInput = new CustomLabelTextField()
        .setText("Apellido: ")
        .setInputDimension(new Dimension(250,35)); 
        panelInput.add(lastNameInput); 
    }

    public String returnLastName(){
        return lastNameInput.getInput(); 
    }

    public void gender(){
        genderRadio = new CustomLabelRadioButton()
        .addOption("Masculino")
        .addOption("Femenino")
        .addOption("Otro")
        .setText("Genero: "); 
        panelInput.add(genderRadio); 
    }

    public String returnGender(){
        return genderRadio.getSelected(); 
    }

    public void documentType(){
        String[] documentsType = {"Tarjeta de Identidad","Cédula de Ciudadanía","Cédula de Extranjería","Pasaporte","Otro"};
        documentTypeJcombox = new CustomLabelJcombox(documentsType)
        .setText("Tipo de documento: "); 
        panelInput.add(documentTypeJcombox); 
    }

    public String returnDocumentType(){
        return documentTypeJcombox.getSelected(); 
    }

    public void birthdate(){
        birthDayInput = new CustomLabelCalendar()
        .setText("Fecha de nacimiento: "); 
        panelInput.add(birthDayInput); 
    }

    public Date returnDate(){
        return birthDayInput.getDate(); 
    }

    public void createPerson(Person person){ 
        presenter.add(person); 
    }

    public void documentNumber(){
        documentNumberInput = new CustomLabelTextField()
        .setText("<html>" +
            "<div>" +
            "Número de<br>" +
            "documento: " +
            "</div>" +
            "</html>"
        ) 
        .setInputDimension(new Dimension(250,35)); 
        panelInput.add(documentNumberInput); 
    }

    public String returnNumberDocument(){
        return documentNumberInput.getInput(); 
    }

    public void mail(){
        emailInput = new CustomLabelTextField()
        .setText("<html>" +
            "<div>" +
            "Correo<br>" +
            "Electronico: " +
            "</div>" +
            "</html>"
        )
        .setInputDimension(new Dimension(250,35)); 
        panelInput.add(emailInput); 
    }

    public String returnEmail(){
        return emailInput.getInput(); 
    }

    public void createPersonButton() {
        savePerson = new CustomLabelBotton("Guardar");
        savePerson.onClick(e -> {verfifyErrors();});
        panelButton.add(savePerson.getButton());
    }

    public void cancelButton() {
        cancelButton = new CustomLabelBotton("Cancelar");
        cancelButton.onClick(e -> {setVisible(false);});
        panelButton.add(cancelButton.getButton());
    }

    public void updateErrors(){
        nameInput.setError("");
        lastNameInput.setError("");
        genderRadio.setError("");
        birthDayInput.setError("");
        documentNumberInput.setError("");
        emailInput.setError("");
    }

    public void verfifyErrors(){
        updateErrors();
        Person person = new Person(returnName(), returnLastName(), returnGender(), 
            returnDocumentType(), returnDate(), returnNumberDocument(), returnEmail());
        if (editingMode) {
            handleEditMode(person);
        } else {
            handleCreateMode(person);
        }
        if (result != null && !result.isSuccess()) {
            showErrors();
        }
    }

    private void handleEditMode(Person person){
        String documentType = originalPerson.getDocumentType();
        String numberDocument = originalPerson.getNumberDocument();
        presenter.remove(documentType, numberDocument);
        result = presenter.add(person);
        if (result.isSuccess()) setVisible(false);
    }

    private void handleCreateMode(Person person){
        result = presenter.add(person);
        if (result.isSuccess()) setVisible(false);
    }

    private void showErrors(){
        ErrorTranslator translator = new ErrorTranslator();
        for (TypingErrors error : result.getErrors()) {
            String message = translator.translate(error);
            switch (error) {
                case NAME_MIN_LENGTH, NAME_EMPTY -> nameInput.setError(message);
                case LASTNAME_MIN_LENGTH, LAST_NAME_EMPTY -> lastNameInput.setError(message); 
                case INVALID_BIRTH_DATE, BIRTHDATE_BLANK -> birthDayInput.setError(message); 
                case INVALID_EMAIL, EMAIL_MAX_LENGHT, EMAIL_EMPTY, DUPLICATE_EMAIL -> emailInput.setError(message);
                case INVALID_ID_CARD, INVALID_IDENTITY_CARD, INVALID_OTHER_DOCUMENT,INVALID_FOREIGN_ID, INVALID_PASSPORT, DUPLICATE_DOCUMENT -> documentNumberInput.setError(message);
                case GENDER_BLANK -> genderRadio.setError(message); 
                case PERSON_NOT_FOUND, CODE_ALPHANUMERIC_REQUIRED, CODE_INVALID_LENGTH,CODE_BLANK,CODE_NAME_MIN_LENGTH,CODE_NAME_MAX_LENGTH,CODE_NAME_BLANK,DESCRIPTION_MAX_LENGTH,CATEGORY_MIN_LENGTH,CATEGORY_BLANK,PRICE_MUST_BE_GREATER_THAN_ZERO,PRICE_INVALID_DECIMALS,QUANTITY_MUST_BE_POSITIVE, DUPLICATE_CODE, PRODUCT_NOT_FOUND-> {}
            }
        }
    }

    
} 