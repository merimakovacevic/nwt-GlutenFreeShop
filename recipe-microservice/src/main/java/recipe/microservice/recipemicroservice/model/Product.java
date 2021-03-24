package recipe.microservice.recipemicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "ProductTypeId is mandatory")
    private String productTypeId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String totalRating;
    private String reviewsNumber;

    public Product(String productTypeId, String name){
        this.productTypeId = productTypeId;
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_recipe",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id",
                    referencedColumnName = "id"))
    private List<Recipe> recipes;
}
