package com.reactive.example.ReactiveMongo.securities.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.util.Assert;

import com.reactive.example.ReactiveMongo.daos.ReactiveUserRepository;


@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfig {

	
    private ServerAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();

    private final CustomReactiveUserDetailsService userDetailsService;
    
    private final CustomAuthenticationConverter customAuthenticationConverter;

    public SecurityConfig(CustomReactiveUserDetailsService userDetailsService, CustomAuthenticationConverter customAuthenticationConverter) {
        Assert.notNull(userDetailsService, "userDetailsService cannot be null");
        Assert.notNull(customAuthenticationConverter, "customAuthenticationConverter cannot be null");
        this.userDetailsService = userDetailsService;
        this.customAuthenticationConverter = customAuthenticationConverter;
    }
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(
	  ServerHttpSecurity http) {
		
        // Disable default security.
        http.httpBasic().disable();
        http.formLogin().disable();
        http.csrf().disable();
        http.logout().disable();

        // Add custom security.
        http.authenticationManager(authenticationManager());

        // Disable authentication for `/resources/**` routes.
        http.authorizeExchange().pathMatchers("/resources/**").permitAll();
        http.authorizeExchange().pathMatchers("/webjars/**").permitAll();

        // Disable authentication for `/api/v1/auth/**` routes.
        http.authorizeExchange().pathMatchers("/api/v1/auth/**").permitAll();
        
        // Disable authentication for `/api/v1/users/**` routes.
        http.authorizeExchange().pathMatchers("/api/v1/users/**").permitAll();

        http.securityContextRepository(securityContextRepository());

        http.authorizeExchange().anyExchange().authenticated();
        //.and().httpBasic().disable();
        
        http.addFilterAt(apiAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
        .exceptionHandling().authenticationEntryPoint(this.entryPoint);

        return http.build();
        
	}
	
//	@Bean
//	public ReactiveUserDetailsService userDetailsService(ReactiveUserRepository users) {
//		return (username) -> {
//			return users.findByUsername(username).cast(UserDetails.class);
//		};
//	}
	
	@Bean
    public ReactiveAuthenticationManager authenticationManager() {
        CustomReactiveAuthenticationManager customReactiveAuthenticationManager =  new CustomReactiveAuthenticationManager(this.userDetailsService);
        return customReactiveAuthenticationManager;
    }

    private AuthenticationWebFilter apiAuthenticationWebFilter() {
        try {
            AuthenticationWebFilter apiAuthenticationWebFilter = new AuthenticationWebFilter(authenticationManager());
            apiAuthenticationWebFilter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(this.entryPoint));
            apiAuthenticationWebFilter.setAuthenticationConverter(this.customAuthenticationConverter);
            // Set the path the filter will work on
            apiAuthenticationWebFilter.setRequiresAuthenticationMatcher(new PathPatternParserServerWebExchangeMatcher("/api/v1/react/**"));

            // Setting the Context Repo helped, not sure if I need this
            apiAuthenticationWebFilter.setSecurityContextRepository(securityContextRepository());

            return apiAuthenticationWebFilter;
        } catch (Exception e) {
            throw new BeanInitializationException("Could not initialize AuthenticationWebFilter apiAuthenticationWebFilter.", e);
        }
    }

    @Bean
    public WebSessionServerSecurityContextRepository securityContextRepository() {
        return new WebSessionServerSecurityContextRepository();
    }
	
}
