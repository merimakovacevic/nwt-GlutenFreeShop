package product.microservice.productmicroservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.ProductType;
import product.microservice.productmicroservice.repository.ImageRepository;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.service.ImageService;

import java.util.Optional;

@RestController
@RequestMapping(path="/image")
@RefreshScope
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping(path="/all")
    public Iterable<Image> getAllImages(){
        return imageService.getAll();
    }

    @GetMapping(path="/{id}")
    public Image getImageById(@PathVariable Long id){
        return imageService.getImageById(id);
    }
}
