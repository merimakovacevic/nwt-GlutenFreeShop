package product.microservice.productmicroservice.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderProcessConfiguration {

    @Bean(name = "order_created_queue")
    public Queue orderCreatedQueue() {
        return new Queue("order_created_queue");
    }

    @Bean(name = "order_stock_return_queue")
    public Queue orderStockReturnQueue() {
        return new Queue("order_stock_return_queue");
    }
}