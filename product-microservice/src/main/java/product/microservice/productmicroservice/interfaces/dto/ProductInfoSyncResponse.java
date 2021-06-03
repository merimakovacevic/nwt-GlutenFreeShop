package product.microservice.productmicroservice.interfaces.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import product.microservice.productmicroservice.dto.model.ProductInfoSyncDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductInfoSyncResponse {
    private String status;
    private ProductInfoSyncDTO result;
}
