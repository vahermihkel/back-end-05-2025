package ee.mario.sharedblogbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor


public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subTitle;

    @Column(columnDefinition = "TEXT")
    private String content;
    private Date timestamp;
    private String image;
    private boolean published;

    @ManyToMany
    private List<Category> category;

    @ManyToOne
    private Person person;
}
