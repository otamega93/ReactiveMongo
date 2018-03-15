package com.reactive.example.ReactiveMongo.securities.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.reactive.example.ReactiveMongo.daos.ReactiveUserRepository;


@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(
	  ServerHttpSecurity http) {
		
	    return http.authorizeExchange()
	      .anyExchange().permitAll()
	      .and().formLogin().and().csrf().disable().build();
	}
	
	@Bean
	public ReactiveUserDetailsService userDetailsService(ReactiveUserRepository users) {
		return (username) -> {
			return users.findByUsername(username).cast(UserDetails.class);
		};
	}
	
}
