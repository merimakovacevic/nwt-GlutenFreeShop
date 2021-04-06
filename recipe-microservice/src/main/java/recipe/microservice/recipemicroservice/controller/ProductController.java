package recipe.microservice.recipemicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import recipe.microservice.recipemicroservice.exception.ApiException;
import recipe.microservice.recipemicroservice.exception.ApiRequestException;
import recipe.microservice.recipemicroservice.model.Product;
import recipe.microservice.recipemicroservice.model.Recipe;
import recipe.microservice.recipemicroservice.repository.ProductRepository;
import recipe.microservice.recipemicroservice.service.ProductService;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path="/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping(path="/id={id}")
    public @ResponseBody Optional<Product> getProductDetails(@PathVariable Integer id){
        Optional<Product> product = productService.getProductDetails(id);
        if(product.isEmpty()) throw new ApiRequestException("Product with id: " + id.toString() + " is not found!");
        else return product;

    }

    @GetMapping(path="/name={name}")
    public @ResponseBody Iterable<Product> searchByName (@PathVariable String name) throws MethodArgumentNotValidException {
        ArrayList<Product> products = (ArrayList<Product>) productService.searchByName(name);
        if(products.size() == 0) throw new ApiRequestException("Product with name: " + name + " is not found!");
        else return products;
    }

    @GetMapping(path="/productTypeId={productTypeId}")
    public @ResponseBody Iterable<Product> searchByType(@PathVariable String productTypeId) {
        ArrayList<Product> products = (ArrayList<Product>)productService.searchByType(productTypeId);
        if(products.size() == 0) throw new ApiRequestException("Product with productTypeId: " + productTypeId + " is not found!");
        else return products;
    }

    @PostMapping(path="/add")
    public @ResponseBody ResponseEntity addProduct(@Valid @RequestBody Product product){
        return productService.addProduct(product);
    }

    @PostMapping(path="/delete")
    public @ResponseBody String deleteProduct(@RequestBody Product product){
        return productService.deleteProduct(product);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
