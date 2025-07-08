package ee.mihkel.webshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    //@ColumnDefault("0") // ühe korra vaja teha.
    //@JsonIgnore // üldse ei näita, aga andmebaasis on
    private double purchasePrice;
    // String --> null
    // Double --> null
    // Integer --> null
    // int
    // double

    // @OneToOne
    // @ManyToOne
    // @OneToMany
    // @ManyToMany
    // parem pool tähistab, kas mul on siin üks Entity või List<Entity>

    // Panen kategooria id: 1, name: Elektroonika
    // @OneToOne ---> seda kategooriat ei saa panna mitmele tootele. Isik -> Kontaktandmed. Aadress.
    // @ManyToOne ---> seda kategooriat saan panna mitmele tootele
    @ManyToOne
    private Category category;
}
