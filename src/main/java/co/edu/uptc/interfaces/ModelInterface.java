package co.edu.uptc.interfaces;

import java.util.List;

import co.edu.uptc.exceptions.Result;
import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;

public interface ModelInterface {
    Result<Person> add(Person person); 
    List<Person> getAll(); 
    void remove(String documentType, String numberDocument);
    Person searchPerson(String documentType, String numberDocument);
    Result<Product> addProduct(Product product); 
    List<Product> getAllProduct(); 
    void removeProduct(String code);
    Product searchProduct(String code);
}

