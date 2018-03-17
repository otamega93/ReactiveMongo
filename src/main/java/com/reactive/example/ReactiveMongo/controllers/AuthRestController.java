package com.reactive.example.ReactiveMongo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.example.ReactiveMongo.daos.ReactiveUserRepository;
import com.reactive.example.ReactiveMongo.securities.config.JwtAuthenticationRequest;
import com.reactive.example.ReactiveMongo.securities.config.JwtAuthenticationResponse;
import com.reactive.example.ReactiveMongo.securities.config.JwtAuthenticationToken;
import com.reactive.example.ReactiveMongo.securities.config.JwtTokenUtil;
import com.reactive.example.ReactiveMongo.services.ReactiveUserService;

import reactor.core.publisher.Mono;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthRestController {

	private ReactiveUserService service;
	
	@Autowired
	private ReactiveAuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AuthRestController(ReactiveUserService service) {
		this.service = service;
	}

	@Autowired private JwtTokenUtil jwtTokenUtil;


	@PostMapping(value = "/token", 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<JwtAuthenticationResponse>> token(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
		
		return service.findByUsername(authenticationRequest.getUsername())
			.map(user -> {
				
				if(user.getUsername().equals(authenticationRequest.getUsername())
						&& passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
					
					return new ResponseEntity<JwtAuthenticationResponse>(
							new JwtAuthenticationResponse(jwtTokenUtil.generateToken(user), user.getUsername()), HttpStatus.OK);
				}
				
				throw new BadCredentialsException("Invalid username or password");
				
			})
			.defaultIfEmpty(notFound().build());
	}
}
