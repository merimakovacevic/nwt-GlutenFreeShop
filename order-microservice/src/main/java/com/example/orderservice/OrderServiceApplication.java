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
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
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

    @Autowired
    RestTemplate restTemplate;



    @Override
    public void run(String... args) throws Exception {



        if (userRepository.count()!=0) {
            return;
        }
        User user1 = new User(1L, "User one");
        userRepository.save(user1);

        User user2 = new User(2L, "User two");
        userRepository.save(user2);

        Product product1 = new Product(789456L);
        productRepository.save(product1);





        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("productId", "" + product1.getId());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, new HttpHeaders());

        String response = restTemplate.postForObject("http://localhost:8084/products", request, String.class);

        System.out.println(response);

        Product product2 = new Product(5000L);
        productRepository.save(product2);




        MultiValueMap<String, String> map2 = new LinkedMultiValueMap<String, String>();
        map2.add("productId", "" + product2.getId());
        HttpEntity<MultiValueMap<String, String>> request2 = new HttpEntity<MultiValueMap<String, String>>(map2, new HttpHeaders());

        String response2 = restTemplate.postForObject("http://localhost:8084/products", request2, String.class);

        System.out.println(response2);

    }
}
