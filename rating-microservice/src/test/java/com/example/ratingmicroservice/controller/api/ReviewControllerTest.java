package com.example.ratingmicroservice.controller.api;

import com.example.ratingmicroservice.dto.model.AverageRatingDto;
import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.dto.model.ReviewDto;
import com.example.ratingmicroservice.service.RatingService;
import com.example.ratingmicroservice.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenValidArguments_whenGetReview_thenValidResult() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setProductId(1L);
        reviewDto.setUserId(1L);
        reviewDto.setComment("Comment");

        when(reviewService.findAllByProductId(1L)).thenReturn(List.of(reviewDto));

        mockMvc.perform(
                get("/review/get")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].productId").value(1))
                .andExpect(jsonPath("$.result[0].userId").value(1))
                .andExpect(jsonPath("$.result[0].comment").value("Comment"));
    }

    @Test
    public void givenBadArguments_whenGetReview_thenBadRequest() throws Exception {
        mockMvc.perform(
                get("/review/get")
                        .param("productId", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidArgumentsButNoReview_whenGetReview_thenNotFound() throws Exception {
        when(reviewService.findAllByProductId(any())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(
                get("/review/get")
                        .param("productId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidArguments_whenAddReview_thenValidResult() throws Exception {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setProductId(1L);
        reviewDto.setUserId(1L);
        reviewDto.setComment("Comment");

        when(reviewService.addReview(any(ReviewDto.class))).thenReturn(reviewDto);

        mockMvc.perform(post("/review/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reviewDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Review successfully added."));
    }
}
