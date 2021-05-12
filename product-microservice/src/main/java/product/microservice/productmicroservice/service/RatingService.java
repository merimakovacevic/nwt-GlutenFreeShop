package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.Rating;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.RatingRepository;

import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Rating> getAll(){
        return ratingRepository.findAll();
    }

    public Rating getById(Long id){
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isEmpty()) throw new ApiRequestException("Rating with id " + id + " does not exist!");
        return rating.get();
    }

    public String addNew(Rating rating){
        if (rating.getProduct() == null) throw new ApiRequestException("Product is not assigned");
        Long productId = rating.getProduct().getId();
        if (productId == null) throw new ApiRequestException("Product is not assigned");
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) throw new ApiRequestException("Product with id " + productId + " does not exist!");
        if (rating.getRatingNumber() < 0 || rating.getRatingNumber() == null) throw new ApiRequestException("Rating number is not valid");
        rating.setProduct(product.get());
        ratingRepository.save(rating);
        return "Saved";
    }

    public String deleteRatingById(Long id){
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isEmpty()) throw new ApiRequestException("Rating with id " + id + " does not exist!");
        ratingRepository.deleteById(id);
        return "Deleted";
    }
}
