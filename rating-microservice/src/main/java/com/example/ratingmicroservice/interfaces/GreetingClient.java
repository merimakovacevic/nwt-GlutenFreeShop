package com.example.ratingmicroservice.interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "RatingService")
public interface GreetingClient {
    @RequestMapping("/get-greeting")
    String greeting();
}
