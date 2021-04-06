package user.microservice.usermicroservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer Id;

    private String FirstName;

    private String LastName;

    private String Email;

    private String Password;

    private String Address;

    private String PhoneNumber;
}
