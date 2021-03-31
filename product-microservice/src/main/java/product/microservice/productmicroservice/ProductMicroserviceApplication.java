package product.microservice.productmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ImageRepository;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ProductTypeRepository;
import product.microservice.productmicroservice.repository.ReviewRepository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@EnableEurekaClient
public class ProductMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMicroserviceApplication.class, args);
	}

}

