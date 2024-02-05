package com.fdmgroup.productCartApi.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.productCartApi.model.Cart;
import com.fdmgroup.productCartApi.model.Product;
import com.fdmgroup.productCartApi.repository.CartRepository;
import com.fdmgroup.productCartApi.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
	
	@Mock
	private CartRepository cartRepository;
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private CartServiceImp cartService;
	
	private Cart cart;
	
	private Product product;
	
	private Product product1;
	
	private List<Product> products;
	
	@BeforeEach
	public void setup() {
		
	product = Product.builder()
			.productId(1L)
			.name("laptop")
			.price(new BigDecimal(2800))
			.build();
	
	product1 = Product.builder()
			.productId(2L)
			.name("gold")
			.price(new BigDecimal(9999999))
			.build();
	
	productRepository.save(product);
	productRepository.save(product1);
	
	products = new ArrayList<>();
	
	products.add(product);
	products.add(product1);
		
	cart = Cart.builder()
			.cartId(1L)
			.products(products)
			.build();
	}
	
	// JUnit test for saveCart method
    @DisplayName("JUnit test for saveCart method")
    @Test
    public void givenCartObject_whenSaveCart_thenReturnCartObject(){
        // given - precondition or setup

        given(cartRepository.save(cart)).willReturn(cart);

        System.out.println(cartRepository);
        System.out.println(cartService);

        // when -  action or the behaviour that we are going test
        Cart savedCart = cartService.saveCart(cart);

        System.out.println(savedCart);
        // then - verify the output
        assertThat(savedCart).isNotNull();
        verify(cartRepository, times(1)).save(cart);
    }
    
 // JUnit test for findAllCarts method
    @DisplayName("JUnit test for findAllCarts method")
    @Test
    public void givenCartsList_whenfindAllCarts_thenReturnCartsList(){
        // given - precondition or setup

    	Cart cart1 = Cart.builder()
                .build();

        given(cartRepository.findAll()).willReturn(List.of(cart,cart1));

        // when -  action or the behaviour that we are going test
        List<Cart> cartList = (List<Cart>) cartService.findAllCarts();

        // then - verify the output
        assertThat(cartList).isNotNull();
        assertThat(cartList.size()).isEqualTo(2);
        verify(cartRepository, times(1)).findAll();
    }
    
    // JUnit test for findCartById method
    @DisplayName("JUnit test for getCartById method")
    @Test
    public void givenCartId_whenFindCartById_thenReturnCartObject(){
        // given
        given(cartRepository.findById(1L)).willReturn(Optional.of(cart));

        // when
        Cart savedCart = cartService.getCartById(cart.getCartId());

        // then
        assertThat(savedCart).isNotNull();
        verify(cartRepository, times(1)).findById(cart.getCartId());
    }
    
    @DisplayName("JUnit test for updateCart method")
    @Test
    public void givenCartObject_whenUpdateCart_thenReturnUpdatedCart(){
    	
        // given - precondition or setup
        given(cartRepository.save(cart)).willReturn(cart);

        Product product2 = Product.builder()
    			.productId(3L)
    			.name("car")
    			.price(new BigDecimal(34500))
    			.build();
    	
    	Product product3 = Product.builder()
    			.productId(4L)
    			.name("boat")
    			.price(new BigDecimal(12563))
    			.build();
    	
    	productRepository.save(product2);
    	productRepository.save(product3);
    	
    	List<Product>  updateProducts = new ArrayList<>();
    	updateProducts.add(product2);
    	updateProducts.add(product3);
    	
    	cart.setProducts(updateProducts);
        
        // when -  action or the behaviour that we are going test
        Cart updatedCart = cartService.updateCart(cart);

        // then - verify the output
        assertThat(updatedCart.getProducts()).isEqualTo(updateProducts);
    }
    
    // JUnit test for deleteCart method
    @DisplayName("JUnit test for deleteCart method")
    @Test
    public void givenCartId_whenDeleteCart_thenNothing(){
        // given - precondition or setup
        Long cartId = 1L;

        willDoNothing().given(cartRepository).deleteById(cartId);

        // when -  action or the behaviour that we are going test
        cartService.deleteCartById(cartId);

        // then - verify the output
        verify(cartRepository, times(1)).deleteById(cartId);
    }
    
    
	
	

}
