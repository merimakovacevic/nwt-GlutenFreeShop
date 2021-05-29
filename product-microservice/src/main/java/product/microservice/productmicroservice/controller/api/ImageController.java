package product.microservice.productmicroservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.service.ImageService;

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