package com.example.ratingmicroservice;

import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.model.Rating;
import com.example.ratingmicroservice.model.Review;
import com.example.ratingmicroservice.model.User;
import com.example.ratingmicroservice.repository.ProductRepository;
import com.example.ratingmicroservice.repository.RatingRepository;
import com.example.ratingmicroservice.repository.ReviewRepository;
import com.example.ratingmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@EnableEurekaClient
@SpringBootApplication
@EnableDiscoveryClient
public class RatingMicroserviceApplication {
	@Bean
	@LoadBalanced
	//Radi service discovery i load balancing, ovim govorim Rest Templateu da ne ide na server
	//direktno nego treba otici na eureka server pa provjeriti lokaciju servisa, i onda otici na taj servis
	//Znaci, svaki put ce pitati eureku, a nece zvati direktno servis
	//Kad posaljemo url Rest Templatu, to ce njemu biti samo hint da zna koji servis treba da zove
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(RatingMicroserviceApplication.class, args);
	}
}

@Component
class DemoCommandLineRunner implements CommandLineRunner {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RatingRepository ratingRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.count()!=0) {
			return;
		}

		User user1 = new User(1L);
		userRepository.save(user1);

		User user2 = new User(2L);
		userRepository.save(user2);

		Product product1 = productRepository.save(new Product(1000L));
		Product product2 = productRepository.save(new Product(1001L));

		ratingRepository.save(new Rating(product1, user1, 3));
		ratingRepository.save(new Rating(product2, user2, 4));

		reviewRepository.save(new Review(product1, user1, "Comment1", new Date()));
		reviewRepository.save(new Review(product2, user2, "Comment2", new Date()));
	}
}
