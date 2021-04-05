package com.example.ratingmicroservice.service;

import com.example.ratingmicroservice.dto.mapper.Mapper;
import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.exception.RestResponseException;
import com.example.ratingmicroservice.model.Product;
import com.example.ratingmicroservice.model.Rating;
import com.example.ratingmicroservice.model.User;
import com.example.ratingmicroservice.repository.ProductRepository;
import com.example.ratingmicroservice.repository.RatingRepository;
import com.example.ratingmicroservice.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RatingServiceTest {

    @Autowired
    RatingService ratingService;

    @MockBean
    RatingRepository ratingRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void givenValidRating_whenAddRating_thenException() throws Exception {
        Product product = new Product();
        product.setId(1L);
        User user = new User();
        user.setId(1L);
        Rating rating = new Rating(product, user, 1);
        rating.setId(1L);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(ratingRepository.findRatingByProductIdAndUserId(any(), any())).thenReturn(Optional.empty());
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        RatingDto savedRating = ratingService.addRating(Mapper.toRatingDto(new Rating()));

        assertTrue(savedRating.getRate() == rating.getRate());
    }

    @Test
    public void givenBadArguments_whenAddRating_thenException() throws Exception {
        RatingDto ratingDto = new RatingDto();

        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RestResponseException.class, () -> ratingService.addRating(ratingDto));
    }

    @Test
    public void givenValidArgumentsButNoProduct_whenAddRating_thenException() throws Exception {

        when(productRepository.findById(any())).thenReturn(Optional.empty());

        RatingDto ratingDto = RatingDto.builder()
                .productId(1L)
                .userId(1L)
                .rate(3)
                .build();

        RestResponseException exception = assertThrows(RestResponseException.class, () -> ratingService.addRating(ratingDto));
        assertTrue(exception.getResponseMessage().contains("PRODUCT with given productId does not exist."));
    }

    @Test
    public void givenValidArgumentsButRatingAlreadyExists_whenAddRating_thenException() throws Exception {

        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRate(1);
        when(ratingRepository.findRatingByProductIdAndUserId(1L, 1L)).thenReturn(Optional.of(rating));

        RestResponseException exception = assertThrows(RestResponseException.class, () -> ratingService.addRating(Mapper.toRatingDto(rating)));
        assertTrue(exception.getResponseMessage().contains("RATING with given productId and userId already exists."));
    }

    @Test
    public void givenValidArgumentsAndRatingDoesNotExist_whenAddRating_thenEqualRating() throws Exception {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(ratingRepository.findRatingByProductIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRate(1);
        when(ratingRepository.save(rating)).thenReturn(rating);

        RatingDto givenRatingDto = Mapper.toRatingDto(rating);

        RatingDto savedDto = ratingService.addRating(givenRatingDto);
        assertEquals(savedDto.getRate(), givenRatingDto.getRate());
    }
}
