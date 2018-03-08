package com.reactive.example.ReactiveMongo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.example.ReactiveMongo.daos.ReactiveProductRepository;
import com.reactive.example.ReactiveMongo.entities.Product;
import com.reactive.example.ReactiveMongo.services.ReactiveProductService;

import reactor.core.publisher.Flux;

@RestController
public class ReactiveProductController {

	@Autowired
	private ReactiveProductService reactiveProductService;
	
	@Autowired
	private ReactiveProductRepository reactiveProductRepository;
	
	
	public Flux<ResponseEntity<Product>> findByName(@Valid String name, Pageable pageable) {
		
		Product product = new Product();
		product.setName("Chicle");
		product.setImageUrl("localhost");
		
		reactiveProductRepository.insert(product);
		
		return reactiveProductService.findByName(name, pageable).map(foundProduct -> ResponseEntity.ok(foundProduct))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
}
