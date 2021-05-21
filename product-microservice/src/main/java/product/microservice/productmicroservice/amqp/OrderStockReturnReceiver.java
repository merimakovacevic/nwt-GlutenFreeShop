package product.microservice.productmicroservice.amqp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import product.microservice.productmicroservice.amqp.event.OrderStockReturnEvent;
import product.microservice.productmicroservice.service.ProductService;

import java.util.Set;

@RabbitListener(queues = "order_stock_return_queue")
@Component
public class OrderStockReturnReceiver {

    private final ProductService productService;

    public OrderStockReturnReceiver(ProductService productService) {
        this.productService = productService;
    }

    @RabbitHandler
    public void receive(String message) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            OrderStockReturnEvent orderCreatedEvent = objectMapper.readValue(message, OrderStockReturnEvent.class);

            productService.returnStock(orderCreatedEvent.getItemInfoList());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
