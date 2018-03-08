package com.reactive.example.ReactiveMongo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping(value = "/products/{name}")
	public Flux<Product> findByName(@PathVariable String name) {

		return reactiveProductRepository.findByName(name);
	}
	
    @PostMapping("/products/{name}")
    public Mono<Product> createProduct(@PathVariable String name) {
    	
		Product product = new Product();
		product.setName("Chicle");
		product.setImageUrl("localhost");
        return reactiveProductRepository.save(product);
    }
	
}
