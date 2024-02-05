package com.fdmgroup.productCartApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends RuntimeException{


	private static final long serialVersionUID = -1834692716076752572L;

	public CartNotFoundException(String msg) {
		super(msg);
	}
}
