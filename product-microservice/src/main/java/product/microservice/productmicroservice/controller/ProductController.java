package product.microservice.productmicroservice.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ProductTypeRepository;
import product.microservice.productmicroservice.service.ProductService;

import java.util.Optional;

@RestController
@RequestMapping(path="/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path="/all")
    public Iterable<Product> getAllProducts () {
        return productService.getAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewProduct (@RequestBody Product product){
        return productService.addNew(product);
    }

    @GetMapping(path="/{id}")
    public Product getProductById (@PathVariable Integer id){
        return productService.getById(id);

    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteProduct (@PathVariable Integer id){
        return productService.deleteProductById(id);

    }

    @PutMapping(path="/{id}")
    public @ResponseBody String updateProduct(@RequestBody Product product, @PathVariable Integer id){
        return productService.updateProduct(product, id);

    }
}
