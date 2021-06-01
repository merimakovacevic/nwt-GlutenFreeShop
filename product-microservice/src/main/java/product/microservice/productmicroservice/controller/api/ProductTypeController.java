package product.microservice.productmicroservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.service.ProductTypeService;

@RestController
@RequestMapping(path="/producttype")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping(path="/all")
    public Iterable<ProductType> getAllProductTypes() {
        return productTypeService.getAll();
    }

    @GetMapping(path="/{id}")
    public ProductType getProductTypeById(@PathVariable Long id){
        return productTypeService.getById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewProductType(@RequestBody ProductType productType){
        return productTypeService.addNew(productType);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteProductType(@PathVariable Long id){
        return productTypeService.deleteProductTypeById(id);
    }

    @PutMapping(path="{id}")
    public @ResponseBody String updateProductType(@RequestBody ProductType productType, @PathVariable Long id){
        return productTypeService.updateProductType(productType, id);
    }
}