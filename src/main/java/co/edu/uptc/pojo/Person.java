package co.edu.uptc.pojo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Person {
    private String name; 
    private String lastName; 
    private String gender; 
    private String documentType; 
    private Date birthDate; 
    private String numberDocument; 
    private String email;
}
