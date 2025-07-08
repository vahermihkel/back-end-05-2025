package ee.mihkel.webshop.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Supplier2Product {
    private int id;
    private String title;
    private String slug;
    private double price;
    private double retailPrice;
    private String description;
    private Supplier2Category category;
    private ArrayList<String> images;
    private Date creationAt;
    private Date updatedAt;
}