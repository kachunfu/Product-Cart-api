package com.fdmgroup.productCartApi.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	@ManyToMany
	@JoinTable(name ="cart_product", joinColumns=@JoinColumn(name="cart_cartId")
	, inverseJoinColumns=@JoinColumn(name = "product_productId"))
	private List<Product> products;
	
	public void addProductToCart(Product product){
		this.products.add(product);
	}
	
	public void deleteProductFromCart(Product product) {
		this.products.remove(product);
	}
	
	public void clearCart() {
		this.products.clear();
	}

}
