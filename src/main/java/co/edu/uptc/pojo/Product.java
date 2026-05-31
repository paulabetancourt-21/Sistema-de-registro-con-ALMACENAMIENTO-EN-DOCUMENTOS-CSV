package co.edu.uptc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String code; 
    private String name; 
    private String description; 
    private String category; 
    private double price; 
    private int amount; 
}
