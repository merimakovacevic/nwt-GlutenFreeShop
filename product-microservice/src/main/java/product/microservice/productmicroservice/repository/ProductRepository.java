package product.microservice.productmicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByName(String Name);
    List<Product> findByProductTypeId(Long id);
    List<Product> findByProductTypeName(String name);
}
