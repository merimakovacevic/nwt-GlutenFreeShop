package com.example.orderservice.amqp.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;
    private List<OrderItemInfo> itemInfoList;

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class OrderItemInfo {
        private Long productId;
        private int quantity;
    }
}
