package product.microservice.productmicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByName(String Name);
    List<Product> findByProductTypeId(Integer id);
    List<Product> findByProductTypeName(String name);
}
