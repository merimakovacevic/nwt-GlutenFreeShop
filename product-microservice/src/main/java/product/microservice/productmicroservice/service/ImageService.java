package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import product.microservice.productmicroservice.exception.EntityType;
import product.microservice.productmicroservice.exception.RestResponseException;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.repository.ImageRepository;
import product.microservice.productmicroservice.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Image> getAll(){
        return imageRepository.findAll();
    }

    public Image getImageById(Long id){
        Optional<Image> image = imageRepository.findById(id);
        if (image.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.IMAGE);
        }
        return image.get();
    }

    public Image getImageByUrl(String url) {
        Optional<Image> image = imageRepository.findImageByUrl(url);
        if (image.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.IMAGE);
        }
        return image.get();
    }

    public List<Image> getImageByProductId(Long productId) {
        List<Image> images = imageRepository.findAllByProductId(productId);
        return images;
    }
}