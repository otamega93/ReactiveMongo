package com.reactive.example.ReactiveMongo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reactive.example.ReactiveMongo.daos.ReactiveProductRepository;
import com.reactive.example.ReactiveMongo.entities.Product;
import com.reactive.example.ReactiveMongo.services.ReactiveProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
	public Mono<Product> findByNameAndImageUrl(Mono<String> name, String imageUrl) {
		
		return reactiveProductRepository.findByNameAndImageUrl(name, imageUrl);
	}

	@Override
	public Mono<Product> findByNameAndImageUrl(String name, String imageUrl) {
		
		return reactiveProductRepository.findByNameAndImageUrl(name, imageUrl);
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
	public Mono<Product> save(Product product) {
		
		return reactiveProductRepository.save(product);
	}

	@Override
	public Flux<Product> save(Mono<Product> product) {

		return reactiveProductRepository.insert(product);
	}

}
