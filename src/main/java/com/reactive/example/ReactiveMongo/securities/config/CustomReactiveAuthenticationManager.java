package com.reactive.example.ReactiveMongo.securities.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import com.reactive.example.ReactiveMongo.entities.User;

import reactor.core.publisher.Mono;

public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {
   
	private final ReactiveUserDetailsService userDetailsService;

	@Autowired
    private PasswordEncoder passwordEncoder;

    public CustomReactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService) {
        Assert.notNull(userDetailsService, "userDetailsService cannot be null");
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        return Mono.just(authentication);
    }
    
    public Boolean isValidUsernamePassword(User user, JwtAuthenticationRequest request) {

      	return user.getUsername().equals(request.getUsername()) 
    				&& passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
    
    public Mono<Boolean> isValidUsernamePasswordMono(User user, JwtAuthenticationRequest request) {

    	return Mono.just(user.getUsername().equals(request.getUsername()) 
    				&& passwordEncoder.matches(request.getPassword(), user.getPassword()));
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;
    }
}