package product.microservice.productmicroservice.amqp.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class OrderItemInfo {
    private Long productId;
    private int quantity;
}