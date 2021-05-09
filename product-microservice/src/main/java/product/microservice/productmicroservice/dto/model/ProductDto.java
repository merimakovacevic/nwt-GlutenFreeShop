package product.microservice.productmicroservice.dto.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@Builder
@ApiModel
@AllArgsConstructor
public class ProductDto {
    private Integer id;

    private String name;

    private String description;

    private String productTypeName;

    private List<String> urls;
}
