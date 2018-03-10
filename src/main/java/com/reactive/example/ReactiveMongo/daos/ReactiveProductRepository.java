package com.reactive.example.ReactiveMongo.daos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.example.ReactiveMongo.entities.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findByName(String name, Pageable pageable);
    
    Flux<Product> findByName(String name);
    
    Flux<Product> findByName(Mono<String> name);
    
    Flux<Product> findByName(Mono<String> name, Pageable pageable);
    
    Mono<Product> findById(String id);

    Mono<Product> findById(Mono<String> id);
    
    Mono<Void> deleteById(String id);
	
}
