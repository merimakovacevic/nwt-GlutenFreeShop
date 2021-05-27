package product.microservice.productmicroservice.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import product.microservice.productmicroservice.controller.dto.ProductInfoSyncDTO;
import product.microservice.productmicroservice.exception.ApiException;
import product.microservice.productmicroservice.exception.ApiRequestException;

import java.time.ZonedDateTime;

@Component
public class RatingClient {

    @Autowired
    private RestTemplate restTemplate;

    public ProductInfoSyncDTO getProductSyncInfo(Long productId) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                        .fromHttpUrl("http://rating-microservice/rating/average")
                        .queryParam("productId", productId);


        ResponseEntity<ProductInfoSyncDTO> response = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), ProductInfoSyncDTO.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return response.getBody();
        }

        throw new ApiRequestException("Rating service returned error");
    }
}