package com.reactive.example.ReactiveMongo.securities.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.reactive.example.ReactiveMongo.daos.ReactiveUserRepository;

import reactor.core.publisher.Mono;

@Component
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    @Autowired 
    private ReactiveUserRepository userRepository;

    
    @Override
    public Mono<UserDetails> findByUsername(String username) {
      
        return userRepository.findUserDetailsByUsername(username);
    }
}