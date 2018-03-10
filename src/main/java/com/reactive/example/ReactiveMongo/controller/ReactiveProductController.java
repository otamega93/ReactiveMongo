package com.reactive.example.ReactiveMongo.controller;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withUnauthorizedRequest;

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
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ReactiveProductController {

	@Autowired
	private ReactiveProductService reactiveProductService;
	
	
	@GetMapping(value = "/{name}", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE},	consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.TEXT_EVENT_STREAM_VALUE})
	public Flux<Product> findByName(@PathVariable String name) {

		return reactiveProductService.findByName(name);
	}
	
    @PostMapping(value = "", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Product> createProduct(@RequestBody @Valid Product product) {
    	
        return reactiveProductService.save(product);
    }
    
    @PostMapping(value = "/mono", 
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE,
					MediaType.TEXT_EVENT_STREAM_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Product> createReactiveProduct(@RequestBody @Valid Mono<Product> product) {

        return reactiveProductService.save(product);

    }
	
}
