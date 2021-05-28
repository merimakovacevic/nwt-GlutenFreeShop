package com.example.ratingmicroservice.interfaces;

import com.example.ratingmicroservice.controller.response.RestResponse;
import com.example.ratingmicroservice.dto.model.ProductDto;
import com.example.ratingmicroservice.exception.RestResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Boolean isProductPresent(Long productId) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonBody = objectMapper.writeValueAsString(productId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request =
                    new HttpEntity<>(jsonBody, headers);

            ResponseEntity<List<ProductDto>> exchange =
                    this.restTemplate.exchange(
                            "http://product-microservice/product/all",
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<ProductDto>>() {
                            },
                            (Object) "mstine");
            List<ProductDto> productList = exchange.getBody();
            return containsProduct(productList, productId);

        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public boolean containsProduct(final List<ProductDto> list, final Long id){
        return list.stream().filter(o -> o.getId().equals(id)).findFirst().isPresent();
    }
}
