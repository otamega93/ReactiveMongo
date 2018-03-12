package com.reactive.example.ReactiveMongo.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.example.ReactiveMongo.entities.Product;
import com.reactive.example.ReactiveMongo.services.ReactiveProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/products/react")
public class ReactiveProductController {

	
	@Autowired
	private ReactiveProductService reactiveProductService;
	
	
	@GetMapping(value = "/{name}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE},	consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<Product> reactiveFindByName(@PathVariable final String name) {

		return reactiveProductService
				.findByName(name);
	}
	
	@GetMapping(value = "", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE},	consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<Product> reactiveFindAll() {

		return reactiveProductService
				.findAll();
	}
	
    @PostMapping(value = "", 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<Product>> createReactiveProduct(@RequestBody @Valid final Mono<Product> product) {
    	
        return reactiveProductService
        		.create(product)
        		.map(content -> new ResponseEntity<>(content, HttpStatus.CREATED))
        		.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping(value = "/{id}", 
    		produces = {MediaType.APPLICATION_JSON_VALUE},
    		consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<Product>> updateReactiveProduct(@PathVariable final String id, 
    		@RequestBody @Valid Product productRequest) {
    	
    	return reactiveProductService
    			.update(id, productRequest)
    			.map(content -> new ResponseEntity<>(content, HttpStatus.OK))
    			.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    	
    }
    
    @DeleteMapping(value = "/{id}", 
    		produces = {MediaType.APPLICATION_JSON_VALUE},
    		consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<ResponseEntity<Product>> deleteReactiveProduct(@PathVariable final String id) {
    	
    	return reactiveProductService
    			.deleteById(id)
    			.map(content -> new ResponseEntity<Product>(HttpStatus.ACCEPTED))
    			.defaultIfEmpty(new ResponseEntity<Product>(HttpStatus.NOT_FOUND));
    }
	
}
