package com.reactive.example.ReactiveMongo.securities.config;

import org.apache.http.HttpHeaders;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
    	
    	 String result = String.format("{\"code\":\"%s\",\"message\": \"%s\"}", "401", e.getMessage());
         ServerHttpResponse response = serverWebExchange.getResponse();
         response.setStatusCode(HttpStatus.OK);
         response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
         return response.writeWith(Mono.just(response.bufferFactory().allocateBuffer().write(result.getBytes())));
    	
//    	serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//    	serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//    	byte[] bytes = "Access Denied".getBytes(StandardCharsets.UTF_8);
//    	DataBuffer buffer = serverWebExchange.getResponse().bufferFactory().wrap(bytes);
//    	
//    	return serverWebExchange.getResponse().writeWith(Mono.just(buffer));

    }
}