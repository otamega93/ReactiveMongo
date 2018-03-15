package com.reactive.example.ReactiveMongo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reactive.example.ReactiveMongo.daos.ReactiveUserRepository;
import com.reactive.example.ReactiveMongo.entities.User;
import com.reactive.example.ReactiveMongo.services.ReactiveUserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserServiceImpl implements ReactiveUserService {

	@Autowired
	private ReactiveUserRepository reactiveUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@Override
	public Mono<User> findByUsername(String username) {
		
		return reactiveUserRepository.findByUsername(username);
	}

	@Override
	public Mono<User> findByUsername(Mono<String> username) {
		
		return reactiveUserRepository.findByUsername(username);
	}

	@Override
	public Mono<User> findById(String id) {
		
		return reactiveUserRepository.findById(id);
	}

	@Override
	public Mono<User> findById(Mono<String> id) {
		
		return reactiveUserRepository.findById(id);
	}

	@Override
	public Flux<User> findAll() {
		
		return reactiveUserRepository.findAll();
	}

	@Override
	public Mono<User> deleteById(String id) {
		
		return reactiveUserRepository.findById(id)
	            .flatMap(oldUser -> 
	            reactiveUserRepository.deleteById(id)
	                               .then(Mono.just(oldUser))
	            );
	}

	@Override
	public Mono<User> create(Mono<User> userMono) {
		
		return userMono.map(newUser -> {
			
			User user = new User();
			
			if (null != newUser.getUsername()) {
			
				user.setUsername(newUser.getUsername());
			}
			
			if (null != newUser.getPassword()) {
				
				user.setPassword(passwordEncoder.encode(newUser.getPassword()));
			}
			
			if (null != newUser.getRoles()) {
				
				user.setRoles(newUser.getRoles());
			}

			// Automatically set to active (true)
			user.setActive(true);
			
			return user;
			
		}).flatMap(reactiveUserRepository::save);
	}

	@Override
	public Mono<User> update(String id, User user) {
		
		return reactiveUserRepository.findById(id).map(existingUser -> {

            if(user.getUsername() != null){
            	existingUser.setUsername(user.getUsername());
            }
            if(user.getPassword() != null){
            	existingUser.setPassword(user.getPassword());
            }
            if(user.getRoles() != null) {
            	existingUser.setRoles(user.getRoles());
            }
            if(user.isActive() != existingUser.isActive()) {
            	existingUser.setActive(user.isActive());
            }

            return existingUser;

        }).flatMap(reactiveUserRepository::save);
	}
	

}
