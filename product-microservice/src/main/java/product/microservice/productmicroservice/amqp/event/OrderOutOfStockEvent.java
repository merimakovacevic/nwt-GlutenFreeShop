package product.microservice.productmicroservice.amqp.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderOutOfStockEvent {
    private Long orderId;
    private Set<Long> productIds;
}