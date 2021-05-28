package product.microservice.productmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.ProductType;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    Optional<ProductType> findProductTypeByName(String name);
}
