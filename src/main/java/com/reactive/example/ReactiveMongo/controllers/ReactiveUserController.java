package com.reactive.example.ReactiveMongo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.example.ReactiveMongo.entities.User;
import com.reactive.example.ReactiveMongo.services.ReactiveUserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/users")
public class ReactiveUserController {

	
	@Autowired
	private ReactiveUserService reactiveUserService;
	
	
	@GetMapping(value = "/security", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE},	consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.TEXT_EVENT_STREAM_VALUE})
	public Mono<String> reactiveSecurityContext(Mono<Authentication> auth) {

		// This method returns de security context data but it's plain text, not json
		return auth.flatMap(a -> 
			Mono.just(a.getName() + " / " + a.getAuthorities().toString()));
	}
	
	@GetMapping(value = "/{username}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE},	consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.TEXT_EVENT_STREAM_VALUE})
	public Mono<User> reactiveFindByUsername(@PathVariable final String username) {

		return reactiveUserService
				.findByUsername(username);
	}
	
	@GetMapping(value = "", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE},	consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<User> reactiveFindAll() {

		return reactiveUserService
				.findAll();
	}
	
    @PostMapping(value = "", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<User>> createReactiveUser(@RequestBody @Valid final Mono<User> user) {
    	
        return reactiveUserService
        		.create(user)
        		.map(content -> new ResponseEntity<>(content, HttpStatus.CREATED))
        		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(value = "/{id}", 
    		produces = {MediaType.APPLICATION_JSON_VALUE},
    		consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<User>> updateReactiveUser(@PathVariable final String id, 
    		@RequestBody @Valid User userRequest) {
    	
    	return reactiveUserService
    			.update(id, userRequest)
    			.map(content -> new ResponseEntity<>(content, HttpStatus.OK))
    			.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    	
    }
    
    @DeleteMapping(value = "/{id}", 
    		produces = {MediaType.APPLICATION_JSON_VALUE},
    		consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<User>> deleteReactiveUser(@PathVariable final String id) {
    	
    	return reactiveUserService
    			.deleteById(id)
    			.map(content -> new ResponseEntity<User>(HttpStatus.ACCEPTED))
    			.defaultIfEmpty(new ResponseEntity<User>(HttpStatus.NOT_FOUND));
    }
	
}
