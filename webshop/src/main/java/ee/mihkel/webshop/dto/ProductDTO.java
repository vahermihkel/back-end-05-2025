package ee.mihkel.webshop.dto;

// DTO - Data Transfer Object

import lombok.Data;

@Data   // @Getter @Setter
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
}
