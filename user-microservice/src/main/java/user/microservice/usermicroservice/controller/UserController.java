package user.microservice.usermicroservice.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.microservice.usermicroservice.controller.dto.mapper.Mapper;
import user.microservice.usermicroservice.controller.dto.model.UserDetailsResponseDTO;
import user.microservice.usermicroservice.model.User;
import user.microservice.usermicroservice.repository.UserRepository;
import user.microservice.usermicroservice.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@RefreshScope
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

        return ResponseEntity.ok(Mapper.toUserDetailsResponseDTO(userOptional.get()));
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody
    String deleteCustomer(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Deleted";
    }
}
