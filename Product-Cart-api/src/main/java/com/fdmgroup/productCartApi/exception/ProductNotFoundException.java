package com.fdmgroup.productCartApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1898234578540731716L;
	
	public ProductNotFoundException(String msg) {
		super(msg);
	}

}
