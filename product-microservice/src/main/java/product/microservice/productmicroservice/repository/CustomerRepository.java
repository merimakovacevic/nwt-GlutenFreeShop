package product.microservice.productmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import product.microservice.productmicroservice.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
