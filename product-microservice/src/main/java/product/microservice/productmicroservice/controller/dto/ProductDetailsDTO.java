package product.microservice.productmicroservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDetailsDTO {
    private Long id;
    private String name;
    private String description;
    private String productType;

    private Double averageRating;
    private Long numberOfRatings;
    private List<String> comments;
    private Integer numberOfComments;
}
