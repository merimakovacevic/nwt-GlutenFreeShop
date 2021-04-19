package com.example.orderservice;

import com.example.orderservice.model.Item;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Product;
import com.example.orderservice.model.User;
import com.example.orderservice.repository.ItemRepository;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.ProductRepository;
import com.example.orderservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);

    }
}

@Component
class DemoCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User("Test User");
        userRepository.save(user);

        Product product = new Product(789456L);
        productRepository.save(product);

        Item item = new Item(77777);
        itemRepository.save(item);

        Order order = new Order(user, "Address1");
        orderRepository.save(order);
    }
}
