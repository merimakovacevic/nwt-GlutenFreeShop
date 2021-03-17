package product.microservice.productmicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.ProductType;

public interface ProductTypeRepository extends CrudRepository<ProductType, Integer> {
}
