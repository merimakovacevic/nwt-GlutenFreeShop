package recipe.microservice.recipemicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String productTypeId;

    @Column(nullable = false)
    private String name;

    private String totalRating;
    private String reviewsNumber;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_recipe",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id",
                    referencedColumnName = "id"))
    private List<Recipe> recipes;
}
