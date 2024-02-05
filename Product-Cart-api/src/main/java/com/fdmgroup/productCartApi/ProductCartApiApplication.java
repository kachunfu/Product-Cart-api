package com.fdmgroup.productCartApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ProductCartApiApplication {

	
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ProductCartApiApplication.class, args);
	}

}
