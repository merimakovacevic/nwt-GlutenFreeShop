package com.example.ratingmicroservice.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @LoadBalanced
    //Radi service discovery i load balancing, ovim govorim Rest Templateu da ne ide na server
    //direktno nego treba otici na eureka server pa provjeriti lokaciju servisa, i onda otici na taj servis
    //Znaci, svaki put ce pitati eureku, a nece zvati direktno servis
    //Kad posaljemo url Rest Templatu, to ce njemu biti samo hint da zna koji servis treba da zove
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
