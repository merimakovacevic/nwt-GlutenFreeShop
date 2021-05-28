package product.microservice.productmicroservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Setter
@Getter
public class RestResponseException extends ResponseStatusException {
    String responseMessage;

    public RestResponseException(HttpStatus status) {
        super(status);
    }

    public RestResponseException(HttpStatus status, EntityType entityType) {
        super(status, "");
        this.responseMessage = getMessage(status, entityType);
    }

    private String getMessage(HttpStatus status, EntityType entityType) {
        StringBuilder message = new StringBuilder("").append(entityType.toString());
        if (entityType.equals(EntityType.RATING)) {
            message.append(" with given productId and userId");
        }
        else if(entityType.equals(EntityType.PRODUCT)) {
            message.append(" with given productId");
        }
        else if (entityType.equals(EntityType.USER)) {
            message.append(" with given userId");
        }
        else if(entityType.equals(EntityType.PRODUCT_TYPE)) {
            message.append(" with given name");
        }

        if (status.equals(HttpStatus.NOT_FOUND) || status.equals(HttpStatus.BAD_REQUEST)) {
            message.append(" does not exist.");
        }
        else {
            message.append(" already exists.");
        }

        return message.toString();
    }
}