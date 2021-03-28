package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Customer;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.repository.CustomerRepository;
import product.microservice.productmicroservice.repository.ImageRepository;
import product.microservice.productmicroservice.repository.ProductRepository;

import java.util.Optional;

import java.util.Optional;

@RestController
@RequestMapping(path="/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path="/all")
    public Iterable<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public Customer getCustomerById(@PathVariable Integer id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) throw new ApiRequestException("Customer with id " + id + " does not exist");
        return customer.get();
        
    }

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewCustomer(@RequestBody Customer customer){
        Customer c = customerRepository.save(customer);
        return "Saved";

    }
}