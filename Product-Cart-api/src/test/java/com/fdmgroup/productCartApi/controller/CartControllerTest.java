package com.fdmgroup.productCartApi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.productCartApi.model.Cart;
import com.fdmgroup.productCartApi.service.CartService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class CartControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CartService cartService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void givenCartObject_whenCreateCart_thenReturnSavedCart() throws Exception {

		// given - precondition or setup
		//@formatter:off
		Cart cart = Cart.builder()
					.build();
		// @formatter:on

		given(cartService.saveCart(any(Cart.class))).willAnswer((invocation) -> invocation.getArgument(0));

		// when - action or behaviour that we are going test
		ResultActions response = mockMvc.perform(post("/api/v1/carts").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cart)));

		// then - verify the result or output using assert statements

		//@formatter:off
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.cartId", 
						is(cart.getCartId())));
		// @formatter:on
		
	}
	
	// JUnit test for Get All carts REST API
    @Test
    public void givenListOfCarts_whenFindAllCarts_thenReturnCartsList() throws Exception{
        // given - precondition or setup
        List<Cart> listOfCarts = new ArrayList<>();
        listOfCarts.add(Cart.builder().build());
        listOfCarts.add(Cart.builder().build());
        given(cartService.findAllCarts()).willReturn(listOfCarts);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/carts"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfCarts.size())));

    }
    
 // JUnit test for GET cart by id REST API
    @Test
    public void givenCartId_whenGetCartById_thenReturnCartObject() throws Exception{
        // given - precondition or setup
        Long cartId = 1L;
        Cart cart = Cart.builder()
                .build();
        given(cartService.getCartById(cartId)).willReturn(cart);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/carts/{cartId}", cartId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cartId", 
						is(cart.getCartId())));
    }
    
    // negative scenario - valid cart id
    // Integration test for GET cart by id REST API
    @Test
    public void givenInvalidCartId_whenGetCartById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
    	 Long cartId = 99999L;
        
    	 given(cartService.getCartById(cartId)).willReturn(null);
    	 

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/carts/{cartId}", cartId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
    
    
    
 // JUnit test for delete cart REST API
    @Test
    public void givenCartId_whenDeleteCart_thenReturn200() throws Exception{
        // given - precondition or setup
        Long cartId = 1L;
        Cart cart = Cart.builder()
                .build();
       given(cartService.getCartById(cartId)).willReturn(cart);
        
        willDoNothing().given(cartService).deleteCartById(cartId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/carts/{cartId}", cartId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    

}
