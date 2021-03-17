package user.microservice.usermicroservice.controller;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import user.microservice.usermicroservice.model.Customer;
import user.microservice.usermicroservice.repository.CustomerRepository;

import java.util.Optional;

@RestController
@RequestMapping(path="/customer")
@Getter @Setter @NoArgsConstructor
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(path="/add")
    public @ResponseBody String addNewCustomer (@RequestBody Customer customer) {
        customerRepository.save(customer);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Customer> getAllCustomers (){
        return customerRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public Optional<Customer> getById (@PathVariable Integer id){
        return customerRepository.findById(id);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteCustomer (@PathVariable Integer id){
        customerRepository.deleteById(id);
        return "Deleted";
    }
}
