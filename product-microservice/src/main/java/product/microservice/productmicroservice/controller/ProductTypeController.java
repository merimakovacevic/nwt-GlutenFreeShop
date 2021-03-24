package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

import java.util.Optional;

@RestController
@RequestMapping(path="/producttype")
public class ProductTypeController {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping(path="/all")
    public Iterable<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public ProductType getProductTypeById(@PathVariable Integer id){
        Optional<ProductType> productType = productTypeRepository.findById(id);
        if(productType.isEmpty()) throw new ApiRequestException("Product type with id "+ id + " does not exist");
        return productType.get();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewProductType(@RequestBody ProductType productType){
        if (productType.getName().equals("") || productType.getName() == null) throw new ApiRequestException("Name is not valid");
        productTypeRepository.save(productType);
        return "Saved";
    }
}
