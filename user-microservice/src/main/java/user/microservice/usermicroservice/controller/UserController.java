package user.microservice.usermicroservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.microservice.usermicroservice.controller.dto.UserDetailsResponseDTO;
import user.microservice.usermicroservice.model.User;
import user.microservice.usermicroservice.repository.UserRepository;
import user.microservice.usermicroservice.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping(path = "/create")
    public User addNewCustomer(@RequestBody @Valid User user) {
        return userService.save(user);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllCustomers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @GetMapping(path = "/details")
    public ResponseEntity<UserDetailsResponseDTO> getUserDetailsByEmail(@RequestParam String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();

        UserDetailsResponseDTO userDetailsResponseDTO = new UserDetailsResponseDTO();
        userDetailsResponseDTO.setId(user.getId());
        userDetailsResponseDTO.setEmail(user.getEmail());
        userDetailsResponseDTO.setPassword(user.getPassword());
        userDetailsResponseDTO.setRoles(Collections.singletonList(user.getRole().getName()));
        return ResponseEntity.ok(userDetailsResponseDTO);
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String deleteCustomer(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Deleted";
    }
}
