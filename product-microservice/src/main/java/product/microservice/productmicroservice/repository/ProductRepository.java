package product.microservice.productmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findProductByName(String Name);
    List<Product> findAllByProductTypeName(String name);
}
