package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.service.ProductService;

import java.util.List;


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

    @GetMapping(path="/product/{name}")
    public @ResponseBody
    List<Product> getProductsByName(@PathVariable String name){
        return productService.findProductsByName(name);
    }

    @GetMapping(path="/producttype/{id}")
    public @ResponseBody
    Iterable<Product> getProductsByProductType(@PathVariable Integer id){
        return productService.findProductsByProductType(id);
    }

    @GetMapping(path="/producttypename/{name}")
    public @ResponseBody
    Iterable<Product> getProductsByProductType(@PathVariable String name){
        return productService.findProductsByProductTypeName(name);
    }
}
