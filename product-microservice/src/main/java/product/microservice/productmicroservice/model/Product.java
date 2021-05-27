package product.microservice.productmicroservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="Name should not be null")
    private String name;

    @NotNull(message="Description should not be null")
    private String description;

    @Min(value = 0, message = "Product stock cannot be lower then 0")
    @NotNull(message="Product stock should not be null")
    private Integer stock;

    @Min(value = 0, message = "Product price cannot be lower then 0")
    @NotNull(message="Product price should not be null")
    private Double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producttype_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ProductType productType;

    public Product(String Name, String Description, ProductType ProductType){
        name=Name;
        description=Description;
        productType=ProductType;
    }

}
