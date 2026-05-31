package co.edu.uptc.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.config.GlobalConfig;
import co.edu.uptc.exceptions.*;
import co.edu.uptc.interfaces.ModelInterface;
import co.edu.uptc.persistence.CsvSerializers;
import co.edu.uptc.persistence.FileManager;
import co.edu.uptc.pojo.*;

public class ManagerApp implements ModelInterface{
    private List<Person> listPeople = new ArrayList<>(); 
    private List<Product> listProducts = new ArrayList<>(); 
    private static ManagerApp instance;

    private FileManager<Person> filePeople;
    private FileManager<Product> fileProducts;
    Path pathPeople;
    Path pathProducts;

    private ManagerApp() {
        pathPeople = Paths.get(GlobalConfig.getInstance().nameFolder(), GlobalConfig.getInstance().nameFilePeople());
        pathProducts = Paths.get(GlobalConfig.getInstance().nameFolder(), GlobalConfig.getInstance().nameFileProduct());

        filePeople = new FileManager<Person>(pathPeople,
                CsvSerializers::personToCsvRow,
                CsvSerializers::csvRowToPerson,
                person -> ValidatePeople.validate(person).isSuccess());

        fileProducts = new FileManager<Product>(pathProducts,
                CsvSerializers::productToCsvRow,
                CsvSerializers::csvRowToProduct,
                product -> ValidateProduct.validate(product).isSuccess());
    }


    public static ManagerApp getInstance(){
        if (instance == null) {
            instance = new ManagerApp();
        }
        return instance;
    } 

    //TODO: REVISAR POR SI APARECE UN ERROR, Y ANADIR TAMBIEN A LA LISTA SI ES NECESARIO.
    @Override
    public Result<Person> add(Person person) {
        Result<Person> result = ValidatePeople.validate(person);
        if (result.isSuccess()) {
            try {
                filePeople.append(person);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    @Override
    public List<Person> getAll(){
        try {
            listPeople = filePeople.readAll();
            return listPeople;
        } catch (IOException e) {
            e.printStackTrace();
            return listPeople;
        }
    }

    @Override
    public void remove(String documentType, String numberDocument){
        try {
            filePeople.deleteIf(p -> p.getDocumentType().equals(documentType)
                    && p.getNumberDocument().equals(numberDocument));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person searchPerson(String documentType, String numberDocument){
        for (Person p : getAll()){
            if (p.getDocumentType().equals(documentType) && p.getNumberDocument().equals(numberDocument)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Result<Product> addProduct(Product product){
        Result<Product> result = ValidateProduct.validate(product);
        if (result.isSuccess()) {
            try{
                fileProducts.append(product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public List<Product> getAllProduct(){
        try{
            listProducts = fileProducts.readAll();
            return listProducts;
        } catch (IOException e) {
            e.printStackTrace();
            return listProducts;
        }
    }

    @Override
    public void removeProduct(String code){
        try {
            fileProducts.deleteIf(p -> p.getCode().equals(code));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product searchProduct(String code){
        for (Product p : getAllProduct()){
            if (p.getCode().equals(code)){
                return p;
            }
        }
        return null;
    }

}
