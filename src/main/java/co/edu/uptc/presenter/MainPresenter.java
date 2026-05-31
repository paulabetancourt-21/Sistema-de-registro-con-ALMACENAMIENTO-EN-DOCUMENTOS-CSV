package co.edu.uptc.presenter;

import java.util.List;
import co.edu.uptc.exceptions.Result;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.interfaces.PresenterInterface;
import co.edu.uptc.interfaces.ViewInterface;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;

public class MainPresenter implements PresenterInterface{
    private ViewInterface view; 
    private ModelInterface model;

    @Override
    public void setView(ViewInterface view) {
        this.view = view; 
    }

    @Override
    public void setModel(ModelInterface model) {
        this.model = model; 
    }

    @Override
    public Result<Person> add(Person person) {
        return model.add(person); 
    }

    @Override
    public List<Person> getAll() {
        return model.getAll(); 
    }

    @Override
    public void remove(String documentType, String numberDocument) {
        model.remove(documentType, numberDocument);
    }

    @Override
    public Person searchPerson(String documentType, String numberDocument) {
        return model.searchPerson(documentType, numberDocument); 
    }

    @Override
    public Result<Product> addProduct(Product product) {
        return model.addProduct(product); 
    }

    @Override
    public List<Product> getAllProduct() {
        return model.getAllProduct(); 
    }

    @Override
    public void removeProduct(String code) {
        model.removeProduct(code);
    }

    @Override
    public Product searchProduct(String code) {
        return model.searchProduct(code); 
    }



}
