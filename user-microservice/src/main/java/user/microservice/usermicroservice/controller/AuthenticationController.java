package user.microservice.usermicroservice.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import user.microservice.usermicroservice.authentication.JwtTokenUtil;
import user.microservice.usermicroservice.controller.dto.model.LoginResponseDTO;
import user.microservice.usermicroservice.model.User;
import user.microservice.usermicroservice.repository.UserRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
public class AuthenticationController {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationController(UserRepository userRepository, JwtTokenUtil jwtTokenUtil, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {


        final Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        final String token = jwtTokenUtil.generateToken(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO()
                .setEmail(user.getEmail())
                .setId(user.getId())
                .setRole(user.getRole().getName());

        return new ResponseEntity<LoginResponseDTO>(loginResponseDTO, httpHeaders, HttpStatus.OK);
    }


    @Data
    @NoArgsConstructor
    static class LoginDTO {
        @NotNull
        private String email;
        @NotNull
        private String password;
    }
}
