package product.microservice.productmicroservice.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import product.microservice.productmicroservice.model.Image;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@Builder
@ApiModel
public class ProductDto {
    private Integer id;

    private String name;

    private String description;

    private String productTypeName;

    private Set<Image> images = new HashSet<>();
}
