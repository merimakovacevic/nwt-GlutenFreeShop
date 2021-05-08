package product.microservice.productmicroservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message="Name should not be null")
    private String name;

    @NotNull(message="Description should not be null")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producttype_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

    public Product(String name, String description, ProductType productType, Set<Image> images){
        this.name = name;
        this.description = description;
        this.productType = productType;
        this.images = images;
    }
}
