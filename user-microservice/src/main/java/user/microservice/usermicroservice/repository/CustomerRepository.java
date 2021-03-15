package user.microservice.usermicroservice.repository;

import org.springframework.data.repository.CrudRepository;
import user.microservice.usermicroservice.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}