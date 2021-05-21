package com.example.orderservice.amqp.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStockReturnEvent {

    private List<OrderCreatedEvent.OrderItemInfo> itemInfoList;
}
