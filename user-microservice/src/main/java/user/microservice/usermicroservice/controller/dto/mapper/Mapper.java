package user.microservice.usermicroservice.controller.dto.mapper;

import user.microservice.usermicroservice.controller.dto.model.UserDetailsResponseDTO;
import user.microservice.usermicroservice.model.User;

import java.util.List;

public class Mapper {
    public static UserDetailsResponseDTO toUserDetailsResponseDTO(User user) {
        UserDetailsResponseDTO userDetailsResponseDTO = new UserDetailsResponseDTO();
        userDetailsResponseDTO.setId(user.getId());
        userDetailsResponseDTO.setEmail(user.getEmail());
        userDetailsResponseDTO.setRoles(List.of(user.getRole().getName()));
        userDetailsResponseDTO.setPassword(user.getPassword());

        return userDetailsResponseDTO;
    }
}
