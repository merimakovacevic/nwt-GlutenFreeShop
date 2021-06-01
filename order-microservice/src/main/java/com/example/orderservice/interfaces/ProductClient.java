package com.example.orderservice.interfaces;

import com.example.orderservice.interfaces.dto.product.CalculatePriceDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Double calculateOrderPrice(CalculatePriceDTO calculatePriceDTO) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonBody = objectMapper.writeValueAsString(calculatePriceDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<Double> response = restTemplate.postForEntity("http://product-microservice/product/calculate-price", request, Double.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return response.getBody();
            }

            return 0.0;
        } catch (JsonProcessingException ignored) {
            ignored.printStackTrace();
            return 0.0;
        }
    }
}


