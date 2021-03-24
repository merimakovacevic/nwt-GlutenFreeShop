package recipe.microservice.recipemicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import recipe.microservice.recipemicroservice.exception.ApiRequestException;
import recipe.microservice.recipemicroservice.model.Product;
import recipe.microservice.recipemicroservice.repository.ProductRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Transactional
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public @ResponseBody
    Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public @ResponseBody
    Optional<Product> getProductDetails(@PathVariable Integer id){
        return productRepository.findById(id);
    }

    public @ResponseBody Iterable<Product> searchByName (@PathVariable String name) throws MethodArgumentNotValidException {
        ArrayList<Product> productsWithName = new ArrayList<Product>();
        for (Product product : productRepository.findAll()) {
            if(product.getName().contains(name)){
                productsWithName.add(product);
            }
        }
        return productsWithName;
    }

    public @ResponseBody Iterable<Product> searchByType(@PathVariable String productTypeId) {
        ArrayList<Product> productsWithType = new ArrayList<Product>();
        for (Product product : productRepository.findAll()) {
            if(product.getProductTypeId().equals(productTypeId)){
                productsWithType.add(product);
            }
        }
        return productsWithType;
    }

    public @ResponseBody
    ResponseEntity addProduct(@Valid @RequestBody Product product){
        productRepository.save(product);
        return ResponseEntity.ok("Product is valid and saved");
    }

    public @ResponseBody String deleteProduct(@RequestBody Product product){
        Integer id = product.getId();
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isEmpty()){
            throw new ApiRequestException("Product with id " + id + "does not exist!");
        }
        else{
            productRepository.delete(product);
            return "Deleted";
        }
    }
}
