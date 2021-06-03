package product.microservice.productmicroservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producttype_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ProductType productType;

    public Product(String name, String description, Integer stock, Double price, ProductType productType){
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.productType = productType;
    }
}