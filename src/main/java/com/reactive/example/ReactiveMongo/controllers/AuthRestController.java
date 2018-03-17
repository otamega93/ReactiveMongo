package com.reactive.example.ReactiveMongo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.example.ReactiveMongo.daos.ReactiveUserRepository;
import com.reactive.example.ReactiveMongo.securities.config.JwtAuthenticationRequest;
import com.reactive.example.ReactiveMongo.securities.config.JwtAuthenticationResponse;
import com.reactive.example.ReactiveMongo.securities.config.JwtTokenUtil;
import com.reactive.example.ReactiveMongo.services.ReactiveUserService;

import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthRestController {

	private ReactiveUserService service;
	
	public AuthRestController(ReactiveUserService service) {
		this.service = service;
	}

	@Autowired private JwtTokenUtil jwtTokenUtil;


	@PostMapping(value = "/token", 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	//@CrossOrigin("*")
	public Mono<ResponseEntity<JwtAuthenticationResponse>> token(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

		return service.findByUsername(authenticationRequest.getUsername())
			.map(user -> ok().contentType(MediaType.APPLICATION_JSON).body(
					new JwtAuthenticationResponse(jwtTokenUtil.generateToken(user), user.getUsername()))
			)
			.defaultIfEmpty(notFound().build());
	}
}
