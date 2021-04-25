package com.example.orderservice.service;

import com.example.orderservice.model.User;
import com.example.orderservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void addNewUser(User user) {
        userRepository.save(user);
    }
}