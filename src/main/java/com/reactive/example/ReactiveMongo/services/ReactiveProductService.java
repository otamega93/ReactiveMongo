package com.reactive.example.ReactiveMongo.services;

import org.springframework.data.domain.Pageable;

import com.reactive.example.ReactiveMongo.entities.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.lang.Void;

public interface ReactiveProductService {

    Flux<Product> findByName(String name, Pageable pageable);
    
    Flux<Product> findByName(Mono<String> name, Pageable pageable);
    
    Flux<Product> findByName(String name);
    
    Flux<Product> findByName(Mono<String> name);
    
    Mono<Product> findById(String id);

    Mono<Product> findById(Mono<String> id);
    
    Flux<Product> findAll();
    
    Mono<Product> deleteById(String id);

	Mono<Product> create(Mono<Product> productMono);
	
	Mono<Product> update(String id, Product product);
    
}
