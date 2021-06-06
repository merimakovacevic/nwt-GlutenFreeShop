//package product.microservice.productmicroservice.amqp;
//
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//import product.microservice.productmicroservice.amqp.event.OrderCreatedEvent;
//import product.microservice.productmicroservice.amqp.event.OrderOutOfStockEvent;
//import product.microservice.productmicroservice.amqp.event.OrderProcessedEvent;
//import product.microservice.productmicroservice.service.ProductService;
//
//import java.util.Set;
//
//@RabbitListener(queues = "order_created_queue")
//@Component
//public class OrderCreatedReceiver {
//
//    private final ProductService productService;
//    private final RabbitTemplate rabbitTemplate;
//
//    public OrderCreatedReceiver(ProductService productService, RabbitTemplate rabbitTemplate) {
//        this.productService = productService;
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    @RabbitHandler
//    public void receive(String message) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(message, OrderCreatedEvent.class);
//
//            Set<Long> outOfStockProductIds = productService.updateStockForItemList(orderCreatedEvent.getItemInfoList());
//
//
//            if (outOfStockProductIds.isEmpty()) {
//                // napravi dogadjaj uspjesno skidanje sa lagare (order processed event)
//                String orderProcessedEventMessage = objectMapper.writeValueAsString(new OrderProcessedEvent(orderCreatedEvent.getOrderId()));
//                rabbitTemplate.convertAndSend("order_processed_queue", orderProcessedEventMessage);
//            }
//            else {
//                // napravi dogadjaj out of stock
//                String orderOutOfStockEventMessage = objectMapper.writeValueAsString(
//                        new OrderOutOfStockEvent(orderCreatedEvent.getOrderId(), outOfStockProductIds));
//                rabbitTemplate.convertAndSend("order_out_of_stock_queue", orderOutOfStockEventMessage);
//            }
//
//
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//}