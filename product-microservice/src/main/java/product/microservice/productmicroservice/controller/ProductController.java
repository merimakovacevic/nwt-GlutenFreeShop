package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

import java.util.Optional;

@RestController
@RequestMapping(path="/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping(path="/all")
    public Iterable<Product> getAllProducts () {
        return productRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewProduct (@RequestBody Product product){
        Optional<ProductType> optionalProductType = productTypeRepository.findById(product.getProductType().getId());
        product.setProductType(optionalProductType.get());
        Product savedProduct = productRepository.save(product);
        return "Saved";
    }

    @GetMapping(path="/{id}")
    public Optional<Product> getProductById (@PathVariable Integer id){
        return productRepository.findById(id);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteProduct (@PathVariable Integer id){
        productRepository.deleteById(id);
        return "Deleted";
    }
}
