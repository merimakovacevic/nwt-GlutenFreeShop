package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductTypeRepository;

@RestController
@RequestMapping(path="/producttype")
public class ProductTypeController {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @GetMapping(path="/all")
    public Iterable<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewProductType(@RequestBody ProductType productType){
        productTypeRepository.save(productType);
        return "Saved";
    }
}
