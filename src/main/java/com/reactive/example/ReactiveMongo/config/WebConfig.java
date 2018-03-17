package com.reactive.example.ReactiveMongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.*;

@Configuration
public class WebConfig implements WebFluxConfigurer {
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources", "classpath:/static/");
    }

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", 
					"Access-Control-Allow-Origin", "Authorization")
			.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
			.allowedOrigins("*")
			.maxAge(3600);

	}

//	@Bean
//	public FreeMarkerConfigurer freeMarkerConfigurer(ReactiveWebApplicationContext applicationContext) {
//		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//		configurer.setTemplateLoaderPath("classpath:/templates/");
//		configurer.setResourceLoader(applicationContext);
//		return configurer;
//	}
//
//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//		registry.freeMarker();
//	}
}