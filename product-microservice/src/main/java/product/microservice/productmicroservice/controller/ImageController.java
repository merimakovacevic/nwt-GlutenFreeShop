package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping(path="/all")
    public Iterable<Image> getAllImages(){
        return imageService.getAll();
    }

    @GetMapping(path="/{id}")
    public Image getImageById(@PathVariable Integer id){
        return imageService.getById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewImage(@RequestBody Image image){
        return imageService.addNew(image);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteImage(@PathVariable Integer id){
        return imageService.deleteImageById(id);
    }
}
