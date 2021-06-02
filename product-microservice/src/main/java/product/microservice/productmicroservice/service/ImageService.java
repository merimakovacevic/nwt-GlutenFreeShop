package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import product.microservice.productmicroservice.dto.mapper.Mapper;
import product.microservice.productmicroservice.dto.model.ImageDto;
import product.microservice.productmicroservice.dto.model.ProductDto;
import product.microservice.productmicroservice.exception.EntityType;
import product.microservice.productmicroservice.exception.RestResponseException;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.repository.ImageRepository;
import product.microservice.productmicroservice.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<ImageDto> getAll(){
        return imageRepository.findAll().stream().map(Mapper::toImageDto).collect(Collectors.toList());
    }

    public ImageDto getImageById(Long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.IMAGE);
        }
        return Mapper.toImageDto(image.get());
    }

    public ImageDto getImageByUrl(String url) {
        Optional<Image> image = imageRepository.findImageByUrl(url);
        if (image.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, EntityType.IMAGE);
        }
        return Mapper.toImageDto(image.get());
    }

    public List<ImageDto> getImageByProductId(Long productId) {
        List<Image> images = imageRepository.findAllByProductId(productId);
        return images.stream().map(Mapper::toImageDto).collect(Collectors.toList());
    }
}