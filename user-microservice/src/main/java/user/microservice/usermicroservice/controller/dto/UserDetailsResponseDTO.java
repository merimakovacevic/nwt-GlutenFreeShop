package user.microservice.usermicroservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponseDTO implements Serializable {

    private Long id;

    private String email;

    private String password;

    private List<String> roles = new ArrayList<>();
}