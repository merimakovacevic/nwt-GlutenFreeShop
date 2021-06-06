package product.microservice.productmicroservice.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.controller.response.RestResponse;
import product.microservice.productmicroservice.dto.model.ImageDto;
import product.microservice.productmicroservice.dto.model.ProductDto;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.service.ImageService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path="/image")
@Validated
@RefreshScope
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping(path="/all")
    public ResponseEntity<?> getAllImages () {
        List<ImageDto> imageDtos = imageService.getAll();
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(imageDtos)
                .entity();
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id){
        ImageDto imageDto = imageService.getImageById(id);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(imageDto)
                .entity();
    }

    @PostMapping(path="/add")
    public ResponseEntity<?> addNewImage(@RequestBody ImageDto imageDto){
        imageService.addNew(imageDto);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(imageDto)
                .entity();
    }

    @GetMapping(path="/product/{productId}")
    //@ResponseBody
    public ResponseEntity<?> getProductImagesByProductId(@PathVariable Long productId){
        List<ImageDto> imageDtos = imageService.getImageByProductId(productId);
        return RestResponse.builder()
                .status(HttpStatus.OK)
                .result(imageDtos)
                .entity();
    }
}