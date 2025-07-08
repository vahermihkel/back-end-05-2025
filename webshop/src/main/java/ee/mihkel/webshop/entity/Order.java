package ee.mihkel.webshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // genereeritakse automaatselt
    private Date created; // back-end lisab ise kuupäeva
    private double totalSum; // back-end arvutab kokku

    @ManyToMany
    private List<Product> products;

    @ManyToOne
    private Person person; // tokeni kaudu kui teeme Authi
}
