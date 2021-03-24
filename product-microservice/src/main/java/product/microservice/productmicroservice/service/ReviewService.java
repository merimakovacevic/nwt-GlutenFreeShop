package product.microservice.productmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.Review;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ReviewRepository;

import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Review> getAll(){
        return reviewRepository.findAll();
    }

    public Review getById(Integer id){
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) throw new ApiRequestException("Review with id " + id + " does not exist!");
        return review.get();
    }

    public String addNew(Review review){
        if (review.getProduct() == null) throw new ApiRequestException("Product is not assigned");
        Integer productId = review.getProduct().getId();
        if (productId == null) throw new ApiRequestException("Product is not assigned");
        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()) throw new ApiRequestException("Product with id " + productId + " does not exist!");
        if (review.getComment().equals("") || review.getComment() == null) throw new ApiRequestException("Comment is not valid");
        review.setProduct(product.get());
        reviewRepository.save(review);
        return "Saved";
    }

    public String deleteReviewById(Integer id){
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) throw new ApiRequestException("Review with id " + id + " does not exist!");
        reviewRepository.deleteById(id);
        return "Deleted";
    }
}
