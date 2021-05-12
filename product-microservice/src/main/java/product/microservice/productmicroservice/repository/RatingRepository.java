package product.microservice.productmicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Rating;

public interface RatingRepository extends CrudRepository<Rating, Long> {
}
