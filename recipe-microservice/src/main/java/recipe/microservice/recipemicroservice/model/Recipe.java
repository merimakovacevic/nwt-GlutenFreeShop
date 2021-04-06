package recipe.microservice.recipemicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "ProductTypeId is mandatory")
    private String productTypeId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String description;



    public Recipe(String productTypeId, String name, String description, List<Product> products){
        this.productTypeId = productTypeId;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    @ManyToMany(mappedBy = "recipes")
    private List<Product> products;

}
