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
import org.springframework.stereotype.Component;

import java.util.Date;

@SpringBootApplication
public class RatingMicroserviceApplication {

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

//		User user1 = userRepository.save(new User());
//		User user2 = userRepository.save(new User());
//		User user3 = userRepository.save(new User());
//
//		Product product1 = productRepository.save(new Product());
//		Product product2 = productRepository.save(new Product());
//		Product product3 = productRepository.save(new Product());
//
//		ratingRepository.save(new Rating(product1, user1, 3));
//		ratingRepository.save(new Rating(product2, user2, 4));
//		ratingRepository.save(new Rating(product3, user3, 5));
//
//		reviewRepository.save(new Review(product1, user1, "Comment1", new Date()));
//		reviewRepository.save(new Review(product2, user2, "Comment2", new Date()));
//		reviewRepository.save(new Review(product3, user3, "Comment3", new Date()));
	}
}
