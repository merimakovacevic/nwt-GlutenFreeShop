package product.microservice.productmicroservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductInfoSyncDTO {
    private Double averageRating;
    private Long numberOfRatings;
    private List<String> comments;
}
