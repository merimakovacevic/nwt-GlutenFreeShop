package product.microservice.productmicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="Name should not be null")
    @UniqueElements
    private String name;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL)
    private List<Product> products;

    public ProductType(String Name){
        name=Name;
    }
}