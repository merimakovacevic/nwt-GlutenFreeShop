package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.Rating;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.RatingRepository;
import product.microservice.productmicroservice.service.RatingService;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping(path="/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping(path="/all")
    public Iterable<Rating> getAllRatings(){
        return ratingService.getAll();
    }

    @GetMapping(path="/{id}")
    public Rating getRatingById(@PathVariable Integer id){
        return ratingService.getById(id);
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewRating(@RequestBody Rating rating){
        return ratingService.addNew(rating);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteRating(@PathVariable Integer id){
        return ratingService.deleteRatingById(id);
    }
}