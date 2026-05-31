package co.edu.uptc.persistence;

import co.edu.uptc.pojo.Person;
import co.edu.uptc.pojo.Product;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CsvSerializers {
    private static String unquote(String s) {
        if (s == null) {
            return null;
        }
        String trimmed = s.trim();
        if (trimmed.length() >= 2 && trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
            return trimmed.substring(1, trimmed.length() - 1);
        }
        return trimmed;
    }

    public static String personToCsvRow(Person person) {
        return person.getName() + "," + person.getLastName() + "," + person.getGender() + ","
                + person.getDocumentType() + "," + dateToString(person.getBirthDate()) + ","
                + person.getNumberDocument() + "," + person.getEmail();
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static Date stringToDate(String date){
        Date dateBitdhay = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dateBitdhay = sdf.parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return dateBitdhay;
    }

    public static Person csvRowToPerson(String row) {
        String[] parts = row.split(",", -1);
        return new Person(
                unquote(parts[0]),
                unquote(parts[1]),
                unquote(parts[2]),
                unquote(parts[3]),
                stringToDate(unquote(parts[4])),
                unquote(parts[5]),
                unquote(parts[6])
        );
    }

    public static String productToCsvRow(Product product) {
        return product.getCode() + "," + product.getName() + "," + product.getDescription() + ","
                + product.getCategory() + "," + product.getPrice() + "," + product.getAmount();
    }

    public static Product csvRowToProduct(String row) {
        String[] parts = row.split(",", -1);
        Product p = new Product(
                unquote(parts[0]),
                unquote(parts[1]),
                unquote(parts[2]),
                unquote(parts[3]),
                Double.parseDouble(unquote(parts[4])),
                Integer.parseInt(unquote(parts[5]))
        );
        return p;
    }
}
