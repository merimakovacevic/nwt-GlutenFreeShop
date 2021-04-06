package recipe.microservice.recipemicroservice.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class ApiException {
    private String message;
    private HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
