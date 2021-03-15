package user.microservice.usermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import user.microservice.usermicroservice.model.Customer;
import user.microservice.usermicroservice.repository.CustomerRepository;

@RestController
@RequestMapping(path="/customer")
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
}
