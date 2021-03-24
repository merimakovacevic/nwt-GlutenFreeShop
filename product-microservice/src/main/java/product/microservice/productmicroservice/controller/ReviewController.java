package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.Review;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ReviewRepository;

import java.util.Optional;

@RestController
@RequestMapping(path="/review")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path="/all")
    public Iterable<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public Review getReviewById(@PathVariable Integer id){
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) throw new ApiRequestException("Review with id " + id + " does not exist!");
        return review.get();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewReview(@RequestBody Review review){
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

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteReview(@PathVariable Integer id){
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) throw new ApiRequestException("Review with id " + id + " does not exist!");
        reviewRepository.deleteById(id);
        return "Deleted";
    }
}
