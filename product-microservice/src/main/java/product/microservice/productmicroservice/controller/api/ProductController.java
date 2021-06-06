package product.microservice.productmicroservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.controller.response.RestResponse;
//import product.microservice.productmicroservice.controller.dto.CalculatePriceDTO;
import product.microservice.productmicroservice.dto.model.ProductDetailsDTO;
import product.microservice.productmicroservice.dto.model.ProductDto;
import product.microservice.productmicroservice.dto.model.ProductInfoSyncDTO;
import product.microservice.productmicroservice.interfaces.RatingClient;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = "/product")
@Validated
@RefreshScope
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RatingClient ratingClient;

    @GetMapping(path = "/all")
    @ResponseBody
    public ResponseEntity<?> getAllProducts() {
        List<ProductDto> productDtos = productService.getAll();
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDtos)
                .entity();
    }

    @PostMapping(path = "/add")
    @ResponseBody
    public ResponseEntity<?> addNewProduct(@Valid @RequestBody ProductDto productDto) {
        productService.addNewProduct(productDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDto)
                .message("Product successfully saved.")
                .entity();
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductById(@NotNull @PathVariable Long id) {
        ProductDto productDto = productService.getProductById(id);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDto)
                .entity();
    }

    @DeleteMapping(path = "/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .message("Product successfully deleted.")
                .entity();
    }

    @PutMapping(path = "/update")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product newProduct, @PathVariable Long productId) {
        productService.updateProduct(newProduct, productId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(newProduct)
                .message("Product successfully updated.")
                .entity();
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getProductsByProductTypeName(@NotNull @RequestParam(name = "productTypeName") String productTypeName) {
        List<ProductDto> productDtos = productService.findProductsByProductTypeName(productTypeName);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDtos)
                .entity();
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<?> getProductsByProductNameAndProductTypeName(
            @NotNull @RequestParam(name = "productName") String productName, @NotNull @RequestParam(name = "productTypeName") String productTypeName) {
        List<ProductDto> productDtos = productService.findProductsByProductNameAndProductTypeName(productName, productTypeName);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(productDtos)
                .entity();
    }

    @GetMapping(path = "/{id}/details")
    public ProductDetailsDTO getProductDetails(@PathVariable(name = "id") Long productId) {
        Product product = productRepository.findById(productId).get();

        // napraviti objekat koji u sebi ima lokalno dostupne informacije (u productDB-u)

        ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
        productDetailsDTO.setId(product.getId());
        productDetailsDTO.setName(product.getName());
        productDetailsDTO.setDescription(product.getDescription());
        productDetailsDTO.setProductType(product.getProductType().getName());

        // poslati zahtjev ka RatingServisu

        ProductInfoSyncDTO productSyncInfo = ratingClient.getProductSyncInfo(productId);

        // upisati informacije dobijene od rating servisa u productDetailsDTO objekat

        productDetailsDTO.setAverageRating(productSyncInfo.getAverageRating());
        productDetailsDTO.setNumberOfRatings(productSyncInfo.getNumberOfRatings());
//        productDetailsDTO.setComments(productSyncInfo.getComments());
//        productDetailsDTO.setNumberOfComments(productSyncInfo.getComments().size());

        return productDetailsDTO;
    }

//
//    @PostMapping(value = "/calculate-price", produces = "application/json")
//    public Double calculatePrice(@RequestBody CalculatePriceDTO calculatePriceDTO) {
//        return productService.calculatePrice(calculatePriceDTO.getItemInfoList());
//    }
}