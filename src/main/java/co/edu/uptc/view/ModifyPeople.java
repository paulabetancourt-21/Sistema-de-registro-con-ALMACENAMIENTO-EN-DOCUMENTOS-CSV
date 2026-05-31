package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import co.edu.uptc.exceptions.ErrorTranslator;
import co.edu.uptc.exceptions.TypingErrors;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.presenter.MainPresenter;
import co.edu.uptc.utils.CustomLabelBotton;
import co.edu.uptc.utils.CustomLabelJcombox;
import co.edu.uptc.utils.CustomLabelTextField;

public class ModifyPeople extends JDialog{
    private JFrame frame;
    private PresenterInterface presenter; 
    private CustomLabelJcombox documentTypeJcombox; 
    private CustomLabelTextField documentNumberInput; 
    private CustomLabelBotton searchPerson;
    private JPanel panelTitle = new JPanel();
    private JPanel panelSearch = new JPanel(); 
    private JPanel panelButton = new JPanel(); 

    public ModifyPeople(JFrame frame, PresenterInterface presenter){
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
        documentType();
        documentNumber();
    }

    private void addButtonComponents(){ 
        searchPersonButton(); 
    }

    public void title(){
        JLabel title = new JLabel("<html>" +
            "<div style='text-align: center; font-family: Serif; font-size: 12px; font-weight: bold;'>"+
            "Modificar registro" +
            "</div>" +
            "</html>"
        ); 
        panelTitle.add(title); 
    }

    public void documentType(){
        String[] documentsType = {"Tarjeta de Identidad","Cédula de Ciudadanía","Cédula de Extranjería","Pasaporte","Otro"};
        documentTypeJcombox = new CustomLabelJcombox(documentsType)
        .setText("Tipo de documento: "); 
        panelSearch.add(documentTypeJcombox); 
    }

    public String returnDocumentType(){
        return documentTypeJcombox.getSelected(); 
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
        panelSearch.add(documentNumberInput); 
    }

    public String returnNumberDocument(){
        return documentNumberInput.getInput(); 
    }

    public void searchPersonButton() {
        searchPerson = new CustomLabelBotton("Buscar");
        searchPerson.onClick(e -> {
            searchPerson();
        });
        panelButton.add(searchPerson.getButton());
    }

    public void searchPerson(){
        Person person = presenter.searchPerson(returnDocumentType(), returnNumberDocument()); 
        if (person != null) {
            setVisible(false);                 
            AddPeople window = new AddPeople(frame, presenter); 
            window.loadData(person);       
            window.setVisible(true);
        }else {
            ErrorTranslator translator = new ErrorTranslator();
            String message = translator.translate(TypingErrors.PERSON_NOT_FOUND);
            documentNumberInput.setError(message);
        }
    }
}
