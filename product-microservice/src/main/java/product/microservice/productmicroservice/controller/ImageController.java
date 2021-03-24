package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.exception.ApiRequestException;
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

    @GetMapping(path="/{id}")
    public Image getImageById(@PathVariable Integer id){
        Optional<Image> image = imageRepository.findById(id);
        if(image.isEmpty()) throw new ApiRequestException("Image with id " + id + " does not exist!");
        return image.get();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewImage(@RequestBody Image image){
        if (image.getProduct() == null) throw new ApiRequestException("Product is not assigned");
        Integer productId = image.getProduct().getId();
        if (productId == null) throw new ApiRequestException("Product is not assigned");
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) throw new ApiRequestException("Product with id " + productId + " does not exist!");
        if (image.getUrl().equals("") || image.getUrl() == null) throw new ApiRequestException("Url is not valid");
        image.setProduct(product.get());
        Image savedImage = imageRepository.save(image);
        return "Saved";
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteImage(@PathVariable Integer id){
        Optional<Image> image = imageRepository.findById(id);
        if(image.isEmpty()) throw new ApiRequestException("Image with id " + id + " does not exist!");
        imageRepository.deleteById(id);
        return "Deleted";
    }
}
