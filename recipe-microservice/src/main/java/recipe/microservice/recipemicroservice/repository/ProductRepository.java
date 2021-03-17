package recipe.microservice.recipemicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import recipe.microservice.recipemicroservice.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
