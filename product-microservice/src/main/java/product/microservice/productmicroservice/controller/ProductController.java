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
        if (product.getProductType() == null) throw new ApiRequestException("Product type not assigned");
        Integer typeId = product.getProductType().getId();
        if (typeId == null) throw new ApiRequestException("Product type not assigned");
        Optional<ProductType> productType = productTypeRepository.findById(typeId);
        if (productType.isEmpty()) throw new ApiRequestException("Product type with id " + typeId + " does not exist!");
        if (product.getName().equals("") || product.getName() == null) throw new ApiRequestException("Name is not valid");
        if (product.getDescription().equals("") || product.getDescription() == null) throw new ApiRequestException("Description is not valid");
        if (product.getTotalRating() < 0 || product.getName() == null) throw new ApiRequestException("Total rating is not valid");
        if (product.getNumberOfRatings() < 0 || product.getNumberOfRatings() == null) throw new ApiRequestException("Number of ratings is not valid");
        product.setProductType(productType.get());
        Product savedProduct = productRepository.save(product);
        return "Saved";
    }

    @GetMapping(path="/{id}")
    public Product getProductById (@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ApiRequestException("Product with id "+id+" does not exist");
        return product.get();
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteProduct (@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ApiRequestException("Product with id "+id+" does not exist");
        productRepository.deleteById(id);
        return "Deleted";
    }
}
