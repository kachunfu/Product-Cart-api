package com.fdmgroup.productCartApi.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.productCartApi.model.Cart;
import com.fdmgroup.productCartApi.repository.CartRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CartIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setup() {
		cartRepository.deleteAll();
	}
	
	@Test
	public void givenCartObject_whenCreateCart_thenReturnSavedCart() throws Exception {

		// given - precondition or setup
		//@formatter:off
		Cart cart = Cart.builder()
					.cartId(4L)
					.build();
		// @formatter:on


		// when - action or behaviour that we are going test
		ResultActions response = mockMvc.perform(post("/api/v1/carts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cart)));

		// then - verify the result or output using assert statements

		//@formatter:off
		response.andDo(print()).andExpect(status().isCreated())
								.andExpect(jsonPath("$.cartId", 
										is(cart.getCartId().intValue())));
		// @formatter:on
		
	}
	
	// Integration test for Get All carts REST API
    @Test
    public void givenListOfCarts_whenFindAllCarts_thenReturnCartsList() throws Exception{
        // given - precondition or setup
        List<Cart> listOfCarts = new ArrayList<>();
        listOfCarts.add(Cart.builder().build());
        listOfCarts.add(Cart.builder().build());
        
        cartRepository.saveAll(listOfCarts);
        
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/carts"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfCarts.size())));
    }
    
 // Integration test for GET cart by id REST API
    @Test
    public void givenCartId_whenGetCartById_thenReturnCartObject() throws Exception{
        // given - precondition or setup
        Cart cart = Cart.builder()
                .build();

        cartRepository.save(cart);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/carts/{cartId}", cart.getCartId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cartId", 
						is(cart.getCartId().intValue())));
    }
    
 // negative scenario - valid cart id
    // Integration test for GET cart by id REST API
    @Test
    public void givenInvalidCartId_whenGetCartById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
    	 Long cartId = 99999L;
    	 
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/carts/{cartId}", cartId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
    
    // Integration test for delete product REST API
    @Test
    public void givenCartId_whenDeleteCart_thenReturn200() throws Exception{
        // given - precondition or setup

        Cart cart = Cart.builder()
                .build();
        
        cartRepository.save(cart);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/carts/{cartId}", cart.getCartId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

}
