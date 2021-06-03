package product.microservice.productmicroservice.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import product.microservice.productmicroservice.dto.model.ProductInfoSyncDTO;
import product.microservice.productmicroservice.exception.ApiRequestException;
import product.microservice.productmicroservice.interfaces.dto.ProductInfoSyncResponse;

@Component
public class RatingClient {

    @Autowired
    private RestTemplate restTemplate;

    public ProductInfoSyncDTO getProductSyncInfo(Long productId) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                        .fromHttpUrl("http://rating-microservice/rating/average")
                        .queryParam("productId", productId);


        ResponseEntity<ProductInfoSyncResponse> response = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), ProductInfoSyncResponse.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            if (response.getBody()!=null) {
                return response.getBody().getResult();
            }
        }

        throw new ApiRequestException("Rating service returned error");
    }
}