package product.microservice.productmicroservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private String Name;

    private String Description;

    private Double TotalRating;

    private Integer NumberOfRatings;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producttype_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ProductType ProductType;

    @OneToMany(mappedBy = "Product", cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

    public void setProductType(ProductType productType){
        this.ProductType = productType;
    }

    public ProductType getProductType(){
        return this.ProductType;
    }

   /* public Long getProductTypeId(){
        return this.ProductType.getId();
    }
    */


  /*  @ManyToOne
    @JoinColumn(name="producttype_id")
    private ProductType ProductType;

    @OneToMany(mappedBy = "ImageProduct")
    private List<Image> Images;

    @OneToMany(mappedBy = "RatingProduct")
    private List<Rating> Ratings;

    @OneToMany
    @JoinColumn(name="id_zanr", referencedColumnName = "id")
    private List<Review> Reviews; */
}
