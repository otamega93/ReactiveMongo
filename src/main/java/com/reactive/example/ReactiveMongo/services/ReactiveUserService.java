package com.reactive.example.ReactiveMongo.services;

import com.reactive.example.ReactiveMongo.entities.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveUserService {
    
    Mono<User> findByUsername(String username);
    
    Mono<User> findByUsername(Mono<String> username);
    
    Mono<User> findById(String id);

    Mono<User> findById(Mono<String> id);
    
    Flux<User> findAll();
    
    Mono<User> deleteById(String id);

	Mono<User> create(Mono<User> userMono);
	
	Mono<User> update(String id, User user);
    
}
