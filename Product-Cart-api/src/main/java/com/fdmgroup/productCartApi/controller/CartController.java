package com.fdmgroup.productCartApi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.productCartApi.exception.CartNotFoundException;
import com.fdmgroup.productCartApi.exception.ProductNotFoundException;
import com.fdmgroup.productCartApi.model.Cart;
import com.fdmgroup.productCartApi.model.Product;
import com.fdmgroup.productCartApi.service.CartService;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

	private CartService cartService;

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping
	public ResponseEntity<?> saveCart(@Valid @RequestBody Cart cart, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(this.cartService.saveCart(cart), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<?> updateCart(@RequestBody Cart cart) {
		if (cart == null) {
			return new ResponseEntity<>(new CartNotFoundException("The cart is not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(this.cartService.updateCart(cart), HttpStatus.OK);
	}

	@GetMapping("/{cartId}")
	public ResponseEntity<?> getCarttById(@PathVariable Long cartId) {
		Cart cart = this.cartService.getCartById(cartId);

		if (cart == null) {
			return new ResponseEntity<>(
					new CartNotFoundException("The cart with id: " + cartId + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> findAllCarts(){
		return new ResponseEntity<>(this.cartService.findAllCarts(), HttpStatus.OK);
	}

	@DeleteMapping("/{cartId}")
	public ResponseEntity<?> deleteCartById(@PathVariable Long cartId) {
		Cart cart = this.cartService.getCartById(cartId);

		if (cart == null) {
			return new ResponseEntity<>(
					new CartNotFoundException("The cart with id: " + cartId + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		this.cartService.deleteCartById(cartId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{cartId}/add/{productId}")
	public ResponseEntity<?> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
		Cart cart = this.cartService.getCartById(cartId);
		Product product = this.cartService.findProductById(productId).block();

		if (cart == null) {
			return new ResponseEntity<>(new CartNotFoundException("The cart is not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}

		if (product == null) {
			return new ResponseEntity<>(new ProductNotFoundException("The product is not found").getMessage(),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(this.cartService.addProductToCart(cart, product), HttpStatus.OK);

	}

	@PutMapping("/{cartId}/remove/{productId}")
	public ResponseEntity<?> removeProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {

		Cart cart = this.cartService.getCartById(cartId);

		if (cart == null) {
			return new ResponseEntity<>(new CartNotFoundException("The cart is not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}

		Product product = cart.getProducts().stream().filter(p -> p.getProductId().equals(productId)).findAny()
				.orElse(null);

		if (product == null) {
			return new ResponseEntity<>(new ProductNotFoundException("The product is not found").getMessage(),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(this.cartService.deleteProductFromCartById(cart, product), HttpStatus.OK);
	}
	
	@PutMapping("/{cartId}/clear")
	public ResponseEntity<?> clearCart(@PathVariable Long cartId) {

		Cart cart = this.cartService.getCartById(cartId);

		if (cart == null) {
			return new ResponseEntity<>(new CartNotFoundException("The cart is not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(this.cartService.clearCart(cart), HttpStatus.OK);
	}

}
