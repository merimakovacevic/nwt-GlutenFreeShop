package com.example.ratingmicroservice.controller.client;

import com.example.ratingmicroservice.controller.response.RestResponse;
import com.example.ratingmicroservice.dto.model.ProductDto;
import com.example.ratingmicroservice.exception.RestResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductDto> getAllProducts() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = this.restTemplate
                .getForObject("http://product-microservice/product/all",  JsonNode.class);

        RestResponse<List<ProductDto>> products = objectMapper.convertValue(
                response,
                new TypeReference<RestResponse<List<ProductDto>>>(){}
        );

        return products.getResult();
    }
}
