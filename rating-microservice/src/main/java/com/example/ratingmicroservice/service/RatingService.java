package com.example.ratingmicroservice.service;

import com.example.ratingmicroservice.dto.mapper.Mapper;
import com.example.ratingmicroservice.dto.model.AverageRatingDto;
import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.exception.RestResponseException;
import com.example.ratingmicroservice.interfaces.ProductClient;
import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.model.Rating;
import com.example.ratingmicroservice.model.User;
import com.example.ratingmicroservice.repository.ProductRepository;
import com.example.ratingmicroservice.repository.RatingRepository;
import com.example.ratingmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private Double getAverageRatingOfProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            List<Rating> ratings = ratingRepository.findAllByProductId(productId);
            Double ratingsSum = Double.valueOf(0);

            for (Rating r : ratings) {
                ratingsSum += r.getRate();
            }

            return ratingsSum / ratings.size();
        }
        throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
    }

    public Optional<AverageRatingDto> getRatingOfProduct(Long productId, Long userId) throws Exception {
        Optional<Product> product = productRepository.findById(productId);
        Boolean test = productClient.isProductPresent(productId);

        if (product.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                Optional<Rating> rating = ratingRepository.findRatingByProductIdAndUserId(productId, userId);
                if (rating.isPresent()) {
                    return Optional.of(new AverageRatingDto(rating.get().getRate(), getAverageRatingOfProduct(productId)));
                }
                throw new RestResponseException(HttpStatus.NOT_FOUND, RATING);
            }
            throw new RestResponseException(HttpStatus.NOT_FOUND, USER);
        }
        throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
    }

    public RatingDto addRating(RatingDto ratingDto) throws RestResponseException {
        Optional<Product> product = productRepository.findById(ratingDto.getProductId());
        if (product.isPresent()) {
            Optional<User> user = userRepository.findById(ratingDto.getUserId());
            if (user.isPresent()) {
                Optional<Rating> rating = ratingRepository.findRatingByProductIdAndUserId(ratingDto.getProductId(), ratingDto.getUserId());
                if (!rating.isPresent()) {
                    Rating ratingModel = new Rating()
                            .setRate(ratingDto.getRate())
                            .setProduct(product.get())
                            .setUser(user.get());
                    ratingRepository.save(ratingModel);
                    return ratingDto;
                }
                throw new RestResponseException(HttpStatus.CONFLICT, RATING);
            }
            throw new RestResponseException(HttpStatus.NOT_FOUND, USER);
        }
        throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
    }

    public RatingDto updateRating(RatingDto ratingDto) throws RestResponseException {
        Optional<User> user = userRepository.findById(ratingDto.getUserId());
        if (user.isPresent()) {
            Optional<Product> product = productRepository.findById(ratingDto.getProductId());
            if (product.isPresent()) {
                Optional<Rating> rating = ratingRepository.findRatingByProductIdAndUserId(ratingDto.getProductId(), ratingDto.getUserId());
                if (rating.isPresent()) {
                    return Mapper.toRatingDto(ratingRepository.save(rating.get().setRate(ratingDto.getRate())));
                } else
                    throw new RestResponseException(HttpStatus.NOT_FOUND, RATING);
            } else throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
        } else throw new RestResponseException(HttpStatus.CONFLICT, USER);
    }

    public Optional<RatingDto> deleteRating(Long productId, Long userId) throws RestResponseException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                Optional<Rating> rating = ratingRepository.findRatingByProductIdAndUserId(productId, userId);
                if (rating.isPresent()) {
                    Rating r = rating.get();
                    ratingRepository.deleteById(rating.get().getId());
                    return Optional.of(Mapper.toRatingDto(r));
                } else
                    throw new RestResponseException(HttpStatus.NOT_FOUND, RATING);
            } else throw new RestResponseException(HttpStatus.NOT_FOUND, PRODUCT);
        } else throw new RestResponseException(HttpStatus.NOT_FOUND, USER);
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }
}
