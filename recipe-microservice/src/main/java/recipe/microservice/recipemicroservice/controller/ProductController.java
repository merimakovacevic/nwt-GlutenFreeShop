package recipe.microservice.recipemicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recipe.microservice.recipemicroservice.model.Product;
import recipe.microservice.recipemicroservice.model.Recipe;
import recipe.microservice.recipemicroservice.repository.ProductRepository;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(path="/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @GetMapping(path="/id={id}")
    public @ResponseBody Optional<Product> getProductDetails(@PathVariable Integer id){
        return productRepository.findById(id);
    }

    @GetMapping(path="/name={name}")
    public @ResponseBody Iterable<Product> searchByName (@PathVariable String name){
        ArrayList<Product> productsWithName = new ArrayList<Product>();
        for (Product product : productRepository.findAll()) {
            if(product.getName().equals(name)){
                productsWithName.add(product);
            }
        }
        return productsWithName;
    }

    @GetMapping(path="/productTypeId={productTypeId}")
    public @ResponseBody Iterable<Product> searchByType(@PathVariable String productTypeId) {
        ArrayList<Product> productsWithType = new ArrayList<Product>();
        for (Product product : productRepository.findAll()) {
            if(product.getProductTypeId().equals(productTypeId)){
                productsWithType.add(product);
            }
        }
        return productsWithType;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addProduct(@RequestBody Product product){
        productRepository.save(product);
        return "Saved";
    }

    @PostMapping(path="/delete")
    public @ResponseBody String deleteProduct(@RequestBody Product product){
        productRepository.delete(product);
        return "Deleted";
    }
}
