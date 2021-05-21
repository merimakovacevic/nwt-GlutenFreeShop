package product.microservice.productmicroservice.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import product.microservice.productmicroservice.controller.dto.CalculatePriceDTO;
import product.microservice.productmicroservice.controller.dto.ProductDetailsDTO;
import product.microservice.productmicroservice.controller.dto.ProductInfoSyncDTO;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.grpc.GRPCClientService;
import product.microservice.productmicroservice.interfaces.RatingClient;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ProductTypeRepository;
import product.microservice.productmicroservice.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private RatingClient ratingClient;

    @Autowired
    private GRPCClientService grpcClientService;

    @GetMapping(path="/all")
    public Iterable<Product> getAllProducts () {
        return productService.getAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewProduct (@RequestBody Product product){
        return productService.addNew(product);
    }

    @GetMapping(path="/{id}")
    public Product getProductById (@PathVariable Long id){
        return productService.getById(id);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteProduct (@PathVariable Long id){
        return productService.deleteProductById(id);
    }

    @PutMapping(path="/{id}")
    public @ResponseBody String updateProduct(@RequestBody Product product, @PathVariable Long id){
        return productService.updateProduct(product, id);
    }

    @GetMapping(path="/product/{name}")
    public @ResponseBody
    List<Product> getProductsByName(@PathVariable String name){
        return productService.findProductsByName(name);
    }

    @GetMapping(path="/producttype/{id}")
    public @ResponseBody
    Iterable<Product> getProductsByProductType(@PathVariable Long id){
        return productService.findProductsByProductType(id);
    }

    @GetMapping(path="/producttypename/{name}")
    public @ResponseBody
    Iterable<Product> getProductsByProductType(@PathVariable String name){
        return productService.findProductsByProductTypeName(name);
    }

    @GetMapping(path="/{id}/details")
    public ProductDetailsDTO getProductDetails(@PathVariable(name = "id") Long productId){
        Product product = productService.getById(productId);


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
        productDetailsDTO.setComments(productSyncInfo.getComments());
        productDetailsDTO.setNumberOfComments(productSyncInfo.getComments().size());


        grpcClientService.sendSystemEvent("PRODUCT_GET_DETAILS_BY_ID", "GET");
        return productDetailsDTO;
    }


    @PostMapping(value = "/calculate-price", produces = "application/json")
    public Double calculatePrice(@RequestBody CalculatePriceDTO calculatePriceDTO) {
        return productService.calculatePrice(calculatePriceDTO.getItemInfoList());
    }
}
