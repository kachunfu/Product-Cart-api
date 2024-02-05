package com.fdmgroup.productCartApi.service;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fdmgroup.productCartApi.model.Cart;
import com.fdmgroup.productCartApi.model.Product;
import com.fdmgroup.productCartApi.repository.CartRepository;
import com.fdmgroup.productCartApi.repository.ProductRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CartServiceImp implements CartService {

	private CartRepository cartRepository;

	private ProductRepository productRepository;

	private final WebClient.Builder webClientBuilder;
	
	@Override
	public Mono<Product> findProductById(Long productId) {

		return this.webClientBuilder.build()
				.get()
				.uri("http://localhost:8080/api/v1/products/" + productId)
				.retrieve()
				.bodyToMono(Product.class).timeout(Duration.ofMillis(10_000));
	}

	@Override
	public Cart getCartById(Long cartId) {
		return this.cartRepository.findById(cartId).orElse(null);
	}

	@Override
	public Cart saveCart(Cart cart) {
		return this.cartRepository.save(cart);
	}

	@Override
	public Cart updateCart(Cart cart) {
		return this.cartRepository.save(cart);
	}

	@Override
	public void deleteCartById(Long cartId) {
		this.cartRepository.deleteById(cartId);

	}

	@Override
	public Cart addProductToCart(Cart cart, Product product) {
		this.productRepository.save(product);
		cart.addProductToCart(product);
		return this.cartRepository.save(cart);
	}

	@Override
	public Cart deleteProductFromCartById(Cart cart, Product product) {
		cart.deleteProductFromCart(product);
		return this.cartRepository.save(cart);
	}

	@Override
	public Cart clearCart(Cart cart) {
		cart.clearCart();
		return this.cartRepository.save(cart);
	}

	@Override
	public Iterable<Cart> findAllCarts() {
		return this.cartRepository.findAll();
	}




}
