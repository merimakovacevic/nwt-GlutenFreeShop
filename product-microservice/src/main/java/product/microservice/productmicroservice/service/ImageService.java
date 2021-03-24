package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Image;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.repository.ImageRepository;
import product.microservice.productmicroservice.repository.ProductRepository;

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

    public Image getById(Integer id){
        Optional<Image> image = imageRepository.findById(id);
        if(image.isEmpty()) throw new ApiRequestException("Image with id " + id + " does not exist!");
        return image.get();
    }

    public String addNew(Image image){
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

    public String deleteImageById(Integer id){
        Optional<Image> image = imageRepository.findById(id);
        if(image.isEmpty()) throw new ApiRequestException("Image with id " + id + " does not exist!");
        imageRepository.deleteById(id);
        return "Deleted";
    }
}
