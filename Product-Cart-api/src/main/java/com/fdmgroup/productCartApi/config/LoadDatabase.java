package com.fdmgroup.productCartApi.config;

import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fdmgroup.productCartApi.model.Cart;
import com.fdmgroup.productCartApi.repository.CartRepository;

@Configuration
public class LoadDatabase {
	
	private static final Logger log = Logger.getLogger(LoadDatabase.class.getName());

	  @Bean
	  CommandLineRunner initDatabase(CartRepository cartRepository) {

	    Cart cart = new Cart();
	    Cart cart1 = new Cart();
	    Cart cart2 = new Cart();
	    
	 
	    return args -> {
	      log.info("Preloading Cart " + cartRepository.save(cart));
	      log.info("Preloading Cart1 " + cartRepository.save(cart1));
	      log.info("Preloading Cart2 " + cartRepository.save(cart2));
	      
	    };
	  }
	
	

}
