package com.reactive.example.ReactiveMongo.services;

import org.springframework.data.domain.Pageable;

import com.reactive.example.ReactiveMongo.entities.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveProductService {

    Flux<Product> findByName(String name, Pageable pageable);
    
    Flux<Product> findByName(Mono<String> name, Pageable pageable);
 
    Mono<Product> findByNameAndImageUrl(Mono<String> name, String imageUrl);

    Mono<Product> findByNameAndImageUrl(String name, String imageUrl);
	
}
