package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.Review;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.ReviewRepository;
import product.microservice.productmicroservice.service.ReviewService;

import java.util.Optional;

@RestController
@RequestMapping(path="/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping(path="/all")
    public Iterable<Review> getAllReviews(){
        return reviewService.getAll();
    }

    @GetMapping(path="/{id}")
    public Review getReviewById(@PathVariable Integer id){
        return reviewService.getById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewReview(@RequestBody Review review){
        return reviewService.addNew(review);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteReview(@PathVariable Integer id){
        return reviewService.deleteReviewById(id);
    }
}
