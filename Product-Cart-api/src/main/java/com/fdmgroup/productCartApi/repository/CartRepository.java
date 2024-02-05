package com.fdmgroup.productCartApi.repository;

import org.springframework.data.repository.CrudRepository;

import com.fdmgroup.productCartApi.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Long>{

}
