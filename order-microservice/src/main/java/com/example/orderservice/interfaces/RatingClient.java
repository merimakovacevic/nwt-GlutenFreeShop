package com.example.orderservice.interfaces;

import com.example.orderservice.controller.dto.OrderRatingItem;
import com.example.orderservice.interfaces.dto.rating.RatingDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RatingClient {
    private final RestTemplate restTemplate;

    public RatingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void addRating(RatingDto ratingDto) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonBody = objectMapper.writeValueAsString(ratingDto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(jsonBody, headers);

            ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://rating-microservice/rating/add", request, Void.class);

        } catch (JsonProcessingException ignored) {
            ignored.printStackTrace();
        }
    }

}
