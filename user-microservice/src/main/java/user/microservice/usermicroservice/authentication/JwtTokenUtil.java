package user.microservice.usermicroservice.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import user.microservice.usermicroservice.controller.dto.mapper.Mapper;
import user.microservice.usermicroservice.model.User;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {
    @Value("${jwt.security.key}")
    private String jwtKey;

    @Value("${jwt.security.validity-seconds}")
    private Integer validityInSeconds;

    public String generateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(user.getRole().getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +
                        validityInSeconds*1000))
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }
}