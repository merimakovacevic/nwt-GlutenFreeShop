package com.example.ratingmicroservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    public User(Long id) {
        this.id = id;
    }
}