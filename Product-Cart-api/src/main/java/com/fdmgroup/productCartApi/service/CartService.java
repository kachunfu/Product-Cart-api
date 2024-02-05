package com.fdmgroup.productCartApi.service;

import com.fdmgroup.productCartApi.model.Cart;
import com.fdmgroup.productCartApi.model.Product;

import reactor.core.publisher.Mono;


public interface CartService {

	Cart getCartById(Long cartId);
	
	Iterable<Cart>findAllCarts();
	
	Cart saveCart(Cart cart);
	
	Cart updateCart(Cart cart);
	
	void deleteCartById(Long cartId);
	
	Mono<Product> findProductById(Long productId);
	
	Cart addProductToCart(Cart cart, Product product);
	
	Cart deleteProductFromCartById (Cart cart, Product product);
	
	Cart clearCart(Cart cart);
	
	
}
