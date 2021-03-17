package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ImageRepository;
import product.microservice.productmicroservice.repository.ProductRepository;

import java.util.Optional;

@RestController
@RequestMapping(path="/image")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path="/all")
    public Iterable<Image> getAllImages(){
        return imageRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewImage(@RequestBody Image image){
        Optional<Product> optionalProduct = productRepository.findById(image.getProduct().getId());
        image.setProduct(optionalProduct.get());
        Image savedImage = imageRepository.save(image);
        return "Saved";
    }
}
