package ee.mihkel.webshop.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Supplier3Response {
    private ArrayList<Supplier3Product> products;
    private int total;
    private int skip;
    private int limit;
}
