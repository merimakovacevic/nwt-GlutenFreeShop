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
    private Integer Id;

    @NotNull(message="Name should not be null")
    private String Name;

    @NotNull(message="Description should not be null")
    private String Description;

    @NotNull(message="Total rating should not be null")
    private Double TotalRating;

    @NotNull(message="Number of ratings should not be null")
    private Integer NumberOfRatings;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producttype_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ProductType ProductType;

    @OneToMany(mappedBy = "Product", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "Product", cascade = CascadeType.ALL)
    private Set<Rating> ratings = new HashSet<>();

    @OneToMany(mappedBy = "Product", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

}
