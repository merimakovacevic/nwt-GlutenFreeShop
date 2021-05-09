package com.example.zuulproxy.interfaces;

import com.example.zuulproxy.interfaces.user.UserDetailsResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
public class UserClient {
    private final RestTemplate restTemplate;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<UserDetailsResponseDTO> getUserDetails(String email) {
        ObjectMapper objectMapper = new ObjectMapper();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://user-microservice/user/details")
                .queryParam("email", email);

        ResponseEntity<UserDetailsResponseDTO> responseEntity = restTemplate.getForEntity(builder.toUriString(), UserDetailsResponseDTO.class);
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return Optional.empty();
        }
        return Optional.ofNullable(responseEntity.getBody());
    }
}
