package product.microservice.productmicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.model.Product;
import product.microservice.productmicroservice.model.Rating;
import product.microservice.productmicroservice.repository.ProductRepository;
import product.microservice.productmicroservice.repository.RatingRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
@RequestMapping(path="/rating")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path="/all")
    public Iterable<Rating> getAllRatings(){
        return ratingRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public Rating getRatingById(@PathVariable Integer id){
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isEmpty()) throw new ApiRequestException("Rating with id " + id + " does not exist!");
        return rating.get();
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewRating(@RequestBody Rating rating){
        if (rating.getProduct() == null) throw new ApiRequestException("Product is not assigned");
        Integer productId = rating.getProduct().getId();
        if (productId == null) throw new ApiRequestException("Product is not assigned");
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) throw new ApiRequestException("Product with id " + productId + " does not exist!");
        if (rating.getRatingNumber() < 0 || rating.getRatingNumber() == null) throw new ApiRequestException("Rating number is not valid");
        rating.setProduct(product.get());
        ratingRepository.save(rating);
        return "Saved";
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody String deleteRating(@PathVariable Integer id){
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isEmpty()) throw new ApiRequestException("Rating with id " + id + " does not exist!");
        ratingRepository.deleteById(id);
        return "Deleted";
    }
}