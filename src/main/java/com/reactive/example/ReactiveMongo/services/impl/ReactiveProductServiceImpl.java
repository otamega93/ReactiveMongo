package com.reactive.example.ReactiveMongo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.reactive.example.ReactiveMongo.daos.ReactiveProductRepository;
import com.reactive.example.ReactiveMongo.entities.Product;
import com.reactive.example.ReactiveMongo.services.ReactiveProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveProductServiceImpl implements ReactiveProductService {

	@Autowired
	private ReactiveProductRepository reactiveProductRepository;
	
	@Override
	public Flux<Product> findByName(String name, Pageable pageable) {

		return reactiveProductRepository.findByName(name, pageable);
	}

	@Override
	public Flux<Product> findByName(Mono<String> name, Pageable pageable) {
		
		return reactiveProductRepository.findByName(name, pageable);
	}

	@Override
	public Flux<Product> findByName(String name) {
		
		return reactiveProductRepository.findByName(name);
	}

	@Override
	public Flux<Product> findByName(Mono<String> name) {

		return reactiveProductRepository.findByName(name);
	}

	@Override
	public Mono<Product> findById(String id) {

		return reactiveProductRepository.findById(id);
	}

	@Override
	public Mono<Product> findById(Mono<String> id) {
		
		return reactiveProductRepository.findById(id);
	}

	@Override
	public Flux<Product> findAll() {

		return reactiveProductRepository.findAll();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public Mono<Product> deleteById(String id) {
		
	    return reactiveProductRepository.findById(id)
	            .flatMap(oldProduct -> 
	            reactiveProductRepository.deleteById(id)
	                               .then(Mono.just(oldProduct))
	            );
	}

	@Override
	public Mono<Product> update(String id, Product productRequest) {

        return reactiveProductRepository.findById(id).map(existingProduct -> {

             if(productRequest.getDescription() != null){
            	 existingProduct.setDescription(productRequest.getDescription());
             }
             if(productRequest.getName() != null){
            	 existingProduct.setName(productRequest.getName());
             }
             if(productRequest.getPrice() != null) {
            	 existingProduct.setPrice(productRequest.getPrice());
             }
             if(productRequest.getImageUrl() != null) {
            	 existingProduct.setImageUrl(productRequest.getImageUrl());
             }
             return existingProduct;

         }).flatMap(reactiveProductRepository::save);
		
	}

	@Override
	public Mono<Product> create(Mono<Product> productMono) {

        return productMono.map(newProduct -> {

            Product product = new Product();

             if (newProduct.getDescription() != null) {
            	 
             	product.setDescription(newProduct.getDescription());
             }
             
             if (newProduct.getName() != null) {
            	 
             	product.setName(newProduct.getName());
             }
             
             if (newProduct.getPrice() != null) {
            	 
             	product.setPrice(newProduct.getPrice());
             }
             
             if (newProduct.getImageUrl() != null) {
            	 
             	product.setImageUrl(newProduct.getImageUrl());
             }
             
             return product;

         }).flatMap(reactiveProductRepository::save);
	}

}
