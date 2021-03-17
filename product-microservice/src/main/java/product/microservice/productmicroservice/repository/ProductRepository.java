package product.microservice.productmicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
