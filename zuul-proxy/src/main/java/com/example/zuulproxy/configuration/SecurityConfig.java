package com.example.zuulproxy.configuration;

import com.example.zuulproxy.constants.GlobalConstants;
import com.example.zuulproxy.security.jwt.JwtConfigurer;
import com.example.zuulproxy.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, GlobalConstants.USER_MICROSERVICE + "/login").permitAll()
                .antMatchers(HttpMethod.OPTIONS, GlobalConstants.USER_MICROSERVICE + "/login").permitAll()
                .antMatchers(HttpMethod.GET, GlobalConstants.PRODUCT_MICROSERVICE + "/product/all").hasAuthority("USER")
                .antMatchers(HttpMethod.OPTIONS, GlobalConstants.PRODUCT_MICROSERVICE + "/product/all").hasAuthority("USER")
                .antMatchers(HttpMethod.GET, GlobalConstants.PRODUCT_MICROSERVICE + "/product/search").hasAuthority("USER")
                .antMatchers(HttpMethod.GET, GlobalConstants.RATING_MICROSERVICE + "/rating").hasAuthority("USER")
                .antMatchers(HttpMethod.PUT, GlobalConstants.RATING_MICROSERVICE + "/rating/update").hasAuthority("USER")
                .antMatchers(HttpMethod.POST, GlobalConstants.RATING_MICROSERVICE + "/rating/add").hasAuthority("USER")
                .antMatchers(HttpMethod.GET, GlobalConstants.PRODUCT_MICROSERVICE + "/image/all").hasAuthority("USER")
                .antMatchers(GlobalConstants.USER_MICROSERVICE + "/user/all").hasAuthority("ADMIN")
                .antMatchers(GlobalConstants.PRODUCT_MICROSERVICE + "/product/add").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedHeaders(List.of(HttpHeaders.ACCESS_CONTROL_MAX_AGE, HttpHeaders.AUTHORIZATION, HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}