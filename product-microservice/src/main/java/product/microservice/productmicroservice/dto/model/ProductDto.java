package product.microservice.productmicroservice.dto.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@Builder
@ApiModel
@AllArgsConstructor
public class ProductDto implements Serializable {

    private String name;

    private String description;

    private Integer stock;

    private Double price;

    private String productTypeName;

}