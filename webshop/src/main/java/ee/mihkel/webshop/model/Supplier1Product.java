package ee.mihkel.webshop.model;

import lombok.Data;

@Data
public class Supplier1Product {
    private int id;
    private String title;
    private double price;
    private double retailPrice; // KUI ta võtab, siis see on tühi
    private String description;
    private String category;
    private String image;
    private Supplier1Rating rating;
}
