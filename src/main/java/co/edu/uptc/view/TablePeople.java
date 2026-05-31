package co.edu.uptc.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.utils.CustomLabelBotton;
import co.edu.uptc.utils.CustomLabelTable;

public class TablePeople extends JDialog{
    private JFrame frame;
    private JPanel titlePanel = new JPanel(); 
    private JPanel tablePanel = new JPanel(); 
    private JPanel buttonsPanel = new JPanel(); 
    private CustomLabelTable table; 
    private CustomLabelBotton newPerson; 
    private CustomLabelBotton modifyPerson; 
    private CustomLabelBotton deletePerson;
    private PresenterInterface presenter;

    public TablePeople(JFrame frame, PresenterInterface presenter){
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
            "Directorio de personas" +
            "</div>" +
            "</html>"
        ); 
        titlePanel.add(title); 
    }

    private void tablePeople(){
        String[] columns = {"Nombre", "Apellido", "Tipo de documento", "Número de documento", "Correo electronico"}; 
        table = new CustomLabelTable(columns); 
        table.getScrollPane().setPreferredSize(new Dimension(700, 500));
        tablePanel.add(table.getScrollPane()); 
    }

    private void fillTable(){
        List<Person> people = presenter.getAll();  
        if (people.isEmpty()) {
            table.addRow(new Object[]{"No hay personas", " registradas", "", ""});
        }else{
        people.stream()
        .map(p -> new Object[]{ p.getName(), p.getLastName(), p.getDocumentType(), p.getNumberDocument(), p.getEmail()})
        .forEach(row -> table.addRow(row));
        }
    }

    public void createPersonButton(){
        newPerson = new CustomLabelBotton("Nuevo"); 
        newPerson.onClick(e -> {
            showRegisterPeople();
        });
        buttonsPanel.add(newPerson.getButton()); 
    }

    public void modifyPersonButton(){
        modifyPerson = new CustomLabelBotton("Modificar"); 
        modifyPerson.onClick(e -> {
            showModifyPeople(); 
        });
        buttonsPanel.add(modifyPerson.getButton()); 
    }

    public void deletePersonButton(){
        deletePerson = new CustomLabelBotton("Eliminar"); 
        deletePerson.onClick(e -> {
            showDeletePeople(); 
        });
        buttonsPanel.add(deletePerson.getButton()); 
    }

    private void showRegisterPeople(){
        AddPeople people = new AddPeople(frame, presenter);  
        people.setVisible(true);
        table.clearRows();
        fillTable();
    }

    private void showModifyPeople(){
        ModifyPeople people = new ModifyPeople(frame, presenter); 
        table.clearRows();
        fillTable();
    }

    private void showDeletePeople(){
        DeletePerson people = new DeletePerson(frame, presenter); 
        table.clearRows();
        fillTable();
    }
}
