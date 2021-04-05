package com.example.ratingmicroservice.controller.api;

import com.example.ratingmicroservice.dto.model.AverageRatingDto;
import com.example.ratingmicroservice.dto.model.RatingDto;
import com.example.ratingmicroservice.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.bytecode.DuplicateMemberException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private RatingService ratingService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void givenValidArgumentsAndValidRating_whenGetRating_thenValidResult() throws Exception {
        when(ratingService.getRatingOfProduct(42L, 30L)).thenReturn(Optional.of(new AverageRatingDto(3, 3.)));

        mockMvc.perform(
                get("/rating/get")
                        .param("productId", "42")
                        .param("userId", "30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.averageRating").value(3.))
                .andExpect(jsonPath("$.result.rateByUser").value(3.));
    }

    @Test
    public void givenNoRatingForArguments_whenGetRating_thenNotFound() throws Exception {
        when(ratingService.getRatingOfProduct(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(
                get("/rating/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "1")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenBadArguments_whenGetRating_thenBadRequest() throws Exception {
        mockMvc.perform(
                get("/rating/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "")
                        .param("userId", ""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenBadArguments_whenAddRating_thenBadRequest() throws Exception {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setRate(3);

        mockMvc.perform(post("/rating/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ratingDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidArguments_whenAddRating_thenOk() throws Exception {
        RatingDto ratingDto = RatingDto.builder()
                .rate(1)
                .productId(1L)
                .userId(1L)
                .build();

        mockMvc.perform(post("/rating/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ratingDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenValidRatingRequest_whenAddRating_thenValidResponse() throws Exception {
        RatingDto ratingDto = RatingDto.builder()
                .rate(1)
                .productId(1L)
                .userId(1L)
                .build();

        when(ratingService.addRating(any(RatingDto.class))).thenReturn(ratingDto);

        mockMvc.perform(post("/rating/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ratingDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Rating successfully saved."));
    }

    @Test
    public void givenBadArguments_whenUpdateRating_thenBadRequestAndValidMessage() throws Exception {
        RatingDto ratingDto = RatingDto.builder()
                .rate(1)
                .productId(1L)
                .build();

        mockMvc.perform(put("/rating/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ratingDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Field 'userId' cannot be empty."));
    }

    @Test
    public void givenValidRatingRequest_whenUpdateRating_thenValidResponse() throws Exception {
        RatingDto ratingDto = RatingDto.builder()
                .rate(1)
                .productId(1L)
                .userId(1L)
                .build();

        when(ratingService.updateRating(any(RatingDto.class))).thenReturn(ratingDto);

        mockMvc.perform(put("/rating/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ratingDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Rating successfully updated."));
    }

    @Test
    public void givenBadArguments_whenDeleteRating_thenBadRequest() throws Exception {
        mockMvc.perform(
                delete("/rating/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "")
                        .param("userId", ""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidArgumentsButNoElement_whenDeleteRating_thenNotFound() throws Exception {
        when(ratingService.deleteRating(1L, 1L)).thenThrow(NoSuchElementException.class);

        mockMvc.perform(
                delete("/rating/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "1")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidArgumentsAndElementPresent_whenDeleteRating_thenValidResponse() throws Exception {
        when(ratingService.deleteRating(1L, 1L)).thenReturn(Optional.of(new RatingDto()));

        mockMvc.perform(
                delete("/rating/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("productId", "1")
                        .param("userId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Rating successfully deleted."));
    }
}
