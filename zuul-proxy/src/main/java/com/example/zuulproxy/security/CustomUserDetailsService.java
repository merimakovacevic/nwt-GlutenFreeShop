package com.example.zuulproxy.security;

import com.example.zuulproxy.interfaces.UserClient;
import com.example.zuulproxy.interfaces.user.UserDetailsResponseDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserClient userClient;

    public CustomUserDetailsService(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsResponseDTO userDetailsResponseDTO = userClient.getUserDetails(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username:" + username + " not found"));

        List<GrantedAuthority> grantedAuthorities = userDetailsResponseDTO
                                .getRoles()
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());


        return new User(userDetailsResponseDTO.getEmail(), userDetailsResponseDTO.getPassword(), true,
                true, true, true, grantedAuthorities);
    }
}