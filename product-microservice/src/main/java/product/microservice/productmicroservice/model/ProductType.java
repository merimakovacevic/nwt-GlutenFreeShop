package product.microservice.productmicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private String Name;

    @OneToMany(mappedBy = "ProductType", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

}
