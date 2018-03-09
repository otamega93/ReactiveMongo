package com.reactive.example.ReactiveMongo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.example.ReactiveMongo.daos.ReactiveProductRepository;
import com.reactive.example.ReactiveMongo.entities.Product;
import com.reactive.example.ReactiveMongo.services.ReactiveProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1")
public class ReactiveProductController {

	@Autowired
	private ReactiveProductService reactiveProductService;
	
	@Autowired
	private ReactiveProductRepository reactiveProductRepository;
	
	@GetMapping(value = "/products/{name}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE},	consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<Product> findByName(@PathVariable String name) {

		return reactiveProductRepository.findByName(name);
	}
	
    @PostMapping(value = "/products", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Product> createProduct(@RequestBody @Valid Product product) {
    	
        return reactiveProductRepository.save(product);
    }
	
}
