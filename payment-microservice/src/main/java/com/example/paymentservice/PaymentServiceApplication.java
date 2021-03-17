package com.example.paymentservice;

import com.example.paymentservice.model.Item;
import com.example.paymentservice.model.Order;
import com.example.paymentservice.model.Product;
import com.example.paymentservice.repository.ItemRepository;
import com.example.paymentservice.repository.OrderRepository;
import com.example.paymentservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}

@Component
class DemoCommandLineRunner implements CommandLineRunner {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public void run(String... args) throws Exception {
		Product product1 = productRepository.save(new Product(20));
		Product product2 = productRepository.save(new Product(30));
		Product product3 = productRepository.save(new Product(40));

		Item item1 = itemRepository.save(new Item(product1, 1));
		Item item2 = itemRepository.save(new Item(product2, 2));
		Item item3 = itemRepository.save(new Item(product3, 3));

		orderRepository.save(new Order(List.of(item1, item2, item3)));
	}
}
