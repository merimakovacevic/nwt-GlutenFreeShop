package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.model.Rating;
import product.microservice.productmicroservice.repository.RatingRepository;

@RestController
@RequestMapping(path="/rating")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping(path="/all")
    public Iterable<Rating> getAllRatings(){
        return ratingRepository.findAll();
    }

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewRating(@RequestBody Rating rating){
        ratingRepository.save(rating);
        return "Saved";
    }
}