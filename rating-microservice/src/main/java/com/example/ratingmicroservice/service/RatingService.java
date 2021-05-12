package com.example.ratingmicroservice.service;

import com.example.ratingmicroservice.dto.mapper.Mapper;
import com.example.ratingmicroservice.dto.model.AverageRatingDto;
import com.example.ratingmicroservice.dto.model.ProductDto;
import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.exception.RestResponseException;
import com.example.ratingmicroservice.controller.client.ProductClient;
import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.model.Rating;
import com.example.ratingmicroservice.model.User;
import com.example.ratingmicroservice.repository.ProductRepository;
import com.example.ratingmicroservice.repository.RatingRepository;
import com.example.ratingmicroservice.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.ratingmicroservice.exception.EntityType.*;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductClient productClient;

    private Double getAverageRatingOfProduct(Long productId) throws JsonProcessingException {
        if (containsProduct(productClient.getAllProducts(), productId)) {
            List<Rating> ratings = ratingRepository.findAllByProductId(productId);
            Double ratingsSum = Double.valueOf(0);

            for (Rating r : ratings) {
                ratingsSum += r.getRate();
            }

            return ratingsSum / ratings.size();
        }
        throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
    }

    public Optional<AverageRatingDto> getRatingOfProduct(Long productId, Long userId) throws RestResponseException, JsonProcessingException {
        if (!containsProduct(productClient.getAllProducts(), productId)) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, USER);
        }
        Optional<Rating> rating = ratingRepository.findRatingByProductIdAndUserId(productId, userId);
        if (rating.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, RATING);
        }
        return Optional.of(new AverageRatingDto(rating.get().getRate(), getAverageRatingOfProduct(productId)));
    }

    public RatingDto addRating(RatingDto ratingDto) throws RestResponseException, JsonProcessingException {
        if (!containsProduct(productClient.getAllProducts(), ratingDto.getProductId())) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
        }
        Optional<User> user = userRepository.findById(ratingDto.getUserId());
        if (user.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, USER);
        }
        Optional<Rating> rating = ratingRepository.findRatingByProductIdAndUserId(ratingDto.getProductId(), ratingDto.getUserId());
        if (rating.isPresent()) {
            throw new RestResponseException(HttpStatus.CONFLICT, RATING);
        }
        Rating ratingModel = new Rating()
                .setRate(ratingDto.getRate())
                .setProduct(productRepository.save(new Product(ratingDto.getProductId())))
                .setUser(userRepository.save(new User(ratingDto.getUserId())));
        ratingRepository.save(ratingModel);
        return ratingDto;
    }

    public RatingDto updateRating(RatingDto ratingDto) throws RestResponseException, JsonProcessingException {
        Optional<User> user = userRepository.findById(ratingDto.getUserId());
        if (user.isEmpty()) {
            throw new RestResponseException(HttpStatus.CONFLICT, USER);
        }
        if (!containsProduct(productClient.getAllProducts(), ratingDto.getProductId())) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
        }
        Optional<Rating> rating = ratingRepository.findRatingByProductIdAndUserId(ratingDto.getProductId(), ratingDto.getUserId());
        if (rating.isEmpty()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, RATING);
        }
        return Mapper.toRatingDto(ratingRepository.save(rating.get().setRate(ratingDto.getRate())));
    }

    public Optional<RatingDto> deleteRating(Long productId, Long userId) throws RestResponseException, JsonProcessingException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, USER);
        }
        if (!containsProduct(productClient.getAllProducts(), productId)) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
        }
        Optional<Rating> rating = ratingRepository.findRatingByProductIdAndUserId(productId, userId);
        if (rating.isPresent()) {
            throw new RestResponseException(HttpStatus.NOT_FOUND, RATING);
        }
        Rating r = rating.get();
        ratingRepository.deleteById(rating.get().getId());
        return Optional.of(Mapper.toRatingDto(r));
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    private boolean containsProduct(final List<ProductDto> list, final Long id){
        return list.stream().filter(o -> o.getId().equals(id)).findFirst().isPresent();
    }
}
