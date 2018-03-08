package com.reactive.example.ReactiveMongo.daos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.example.ReactiveMongo.entities.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findByName(String name, Pageable pageable);
    
    Flux<Product> findByName(Mono<String> name, Pageable pageable);
 
    Mono<Product> findByNameAndImageUrl(Mono<String> name, String imageUrl);
 
    @Query("{ 'name': ?0, 'imageUrl': ?1}")
    Mono<Product> findByNameAndImageUrl(String name, String imageUrl);
	
}
