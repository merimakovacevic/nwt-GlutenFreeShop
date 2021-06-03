package product.microservice.productmicroservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import product.microservice.productmicroservice.amqp.event.OrderItemInfo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatePriceDTO {
    private List<OrderItemInfo> itemInfoList;
}