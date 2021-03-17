package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.model.Review;
import product.microservice.productmicroservice.repository.ReviewRepository;

@RestController
@RequestMapping(path="/review")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping(path="/all")
    public Iterable<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewReview(@RequestBody Review review){
        reviewRepository.save(review);
        return "Saved";
    }
}
