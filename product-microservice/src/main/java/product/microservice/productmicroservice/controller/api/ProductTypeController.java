package product.microservice.productmicroservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.controller.response.RestResponse;
import product.microservice.productmicroservice.dto.model.ProductDto;
import product.microservice.productmicroservice.dto.model.ProductTypeDto;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.service.ProductTypeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path="/producttype")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping(path="/all")
    @ResponseBody
    public ResponseEntity<?> getAllProducts () {
        List<ProductTypeDto> productTypeDtos = productTypeService.getAll();
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productTypeDtos)
                .entity();
    }

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseEntity<?> addNewProduct (@Valid @RequestBody ProductTypeDto productTypeDto){
        productTypeService.addNewProductType(productTypeDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productTypeDto)
                .message("Product Type successfully saved.")
                .entity();
    }

    @GetMapping(path="/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductTypeById (@NotNull @PathVariable Long id){
        ProductTypeDto productTypeDto = productTypeService.getProductTypeById(id);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productTypeDto)
                .entity();
    }

    @DeleteMapping(path="/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProductType (@PathVariable Long id){
        productTypeService.deleteProductTypeById(id);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Product Type successfully deleted.")
                .entity();
    }

    @PutMapping(path="/update")
    @ResponseBody
    public ResponseEntity<?> updateProductType(@Valid @RequestBody ProductTypeDto productTypeDto, @PathVariable Long productTypeId){
        productTypeService.updateProductType(productTypeDto, productTypeId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productTypeDto)
                .message("Product Type successfully updated.")
                .entity();
    }

}