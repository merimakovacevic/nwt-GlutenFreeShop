package product.microservice.productmicroservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.controller.response.RestResponse;
import product.microservice.productmicroservice.dto.model.ProductDto;
import product.microservice.productmicroservice.service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path="/product")
@Validated
@RefreshScope
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path="/all")
    @ResponseBody
    public ResponseEntity<?> getAllProducts () {
        List<ProductDto> productDtos = productService.getAll();
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDtos)
                .entity();
    }

    @PostMapping(path="/add")
    @ResponseBody
    public ResponseEntity<?> addNewProduct (@Valid @RequestBody ProductDto productDto){
        productService.addNewProduct(productDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDto)
                .message("Product successfully saved.")
                .entity();
    }

    @GetMapping(path="/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductById (@NotNull @PathVariable Long id){
        ProductDto productDto = productService.getProductById(id);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDto)
                .entity();
    }

    @DeleteMapping(path="/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProduct (@PathVariable Long id){
        productService.deleteProductById(id);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Product successfully deleted.")
                .entity();
    }

    @PutMapping(path="/update")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDto productDto){
        productService.updateProduct(productDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDto)
                .message("Product successfully updated.")
                .entity();
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getProductsByProductTypeName(@NotNull @RequestParam(name = "productTypeName") String productTypeName){
        List<ProductDto> productDtos = productService.findProductsByProductTypeName(productTypeName);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDtos)
                .entity();
    }
}