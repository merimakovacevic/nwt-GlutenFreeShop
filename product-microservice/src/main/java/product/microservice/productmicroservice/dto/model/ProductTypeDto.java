package product.microservice.productmicroservice.dto.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@Builder
@ApiModel
@AllArgsConstructor
public class ProductTypeDto {

    private String name;

}
