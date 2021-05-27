package product.microservice.productmicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
