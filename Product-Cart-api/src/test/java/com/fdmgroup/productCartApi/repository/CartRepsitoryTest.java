package com.fdmgroup.productCartApi.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.productCartApi.model.Cart;
import com.fdmgroup.productCartApi.model.Product;

@DataJpaTest
public class CartRepsitoryTest {

	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
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
			.products(products)
			.build();
	}
	
	//JUnit Test for get all products operation
	
	@DisplayName("JUnit test for save cart operation")
	@Test
	public void givenCartObject_whenSave_thenReturnSaveCart() {
		
		//given - precondition or setup
		Cart cart = Cart.builder()
					.build();
		
		//when - action or behaviour that we are going test
		Cart savedCart = cartRepository.save(cart);
		
		//then - verify the output
		assertThat(savedCart).isNotNull();
		assertThat(savedCart.getCartId()).isGreaterThan(0);
	}
	
	// JUnit test for get all carts operation
    
    @DisplayName("JUnit Test for get all carts operation")
    @Test
    public void givenCartList_whenFindAll_thenCartList() {
    	
    	Cart cart1= Cart.builder()
				.build();
    	
    	Cart cart2= Cart.builder()
				.build();
    	
    	cartRepository.save(cart1);
    	cartRepository.save(cart2);
    	
    	//when 
    	List<Cart> cartList = (List<Cart>) cartRepository.findAll();
    	
    	   // then - verify the output
        assertThat(cartList).isNotNull();
        assertThat(cartList.size()).isEqualTo(2);
    }
    
    // JUnit test for get cart by id operation
    @DisplayName("JUnit test for get cart by id operation")
    @Test
    public void givenCartObject_whenFindById_thenReturnCartObject(){
        // given - precondition or setup
    	
        cartRepository.save(cart);

        // when -  action or the behaviour that we are going test
        Cart cartDB = cartRepository.findById(cart.getCartId()).get();

        // then - verify the output
        assertThat(cartDB).isNotNull();
        assertThat(cartDB.getProducts()).contains(product,product1);
    }
    
 // JUnit test for update cart operation
    @DisplayName("JUnit test for update cart operation")
    @Test
    public void givenCartObject_whenUpdateCart_thenReturnUpdatedCart(){
    	
        cartRepository.save(cart);
        
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
        
        

        // when -  action or the behaviour that we are going test
        Cart savedCart = cartRepository.findById(cart.getCartId()).get();
        savedCart.setProducts(updateProducts);
        Cart updatedCart =  cartRepository.save(savedCart);

        // then - verify the output
        assertThat(updatedCart.getProducts()).isEqualTo(updateProducts);
    }
    
 // JUnit test for delete cart operation
    @DisplayName("JUnit test for cart operation")
    @Test
    public void givenCartObject_whenDelete_thenRemoveCart(){

    	cartRepository.save(cart);

        // when -  action or the behaviour that we are going test
    	cartRepository.deleteById(product.getProductId());
        Optional<Cart> cartOptional = cartRepository.findById(cart.getCartId());

        // then - verify the output
        assertThat(cartOptional).isEmpty();
    }
	
	
}
