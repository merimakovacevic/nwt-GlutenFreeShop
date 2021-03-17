package recipe.microservice.recipemicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String productTypeId;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "recipes")
    private List<Product> products;
}
