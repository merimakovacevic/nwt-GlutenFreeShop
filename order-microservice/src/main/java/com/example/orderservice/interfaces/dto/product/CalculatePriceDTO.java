package com.example.orderservice.interfaces.dto.product;

import com.example.orderservice.amqp.event.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CalculatePriceDTO {
    private List<OrderCreatedEvent.OrderItemInfo> itemInfoList;

}
