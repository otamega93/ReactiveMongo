package com.reactive.example.ReactiveMongo.daos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.reactive.example.ReactiveMongo.entities.User;

import reactor.core.publisher.Mono;

@Repository
public interface ReactiveUserRepository extends ReactiveMongoRepository<User, String> {

	Mono<User> findByUsername(String username);
	
	Mono<User> findByUsername(Mono<String> username);
	
	Mono<UserDetails> findUserDetailsByUsername(String username);
}
