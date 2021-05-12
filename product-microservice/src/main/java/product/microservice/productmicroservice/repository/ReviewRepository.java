package product.microservice.productmicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
